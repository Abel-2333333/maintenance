package com.ruoyi.maintenance.service.impl;

import com.google.zxing.WriterException;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.maintenance.wechat.entity.QrCodeResponseBody;
import com.ruoyi.maintenance.wechat.exception.ExceptionAssert;
import com.ruoyi.maintenance.wechat.util.FileUtils;
import com.ruoyi.maintenance.wechat.util.RedisUtils;
import com.ruoyi.maintenance.wechat.util.StringUtils;
import com.ruoyi.maintenance.domain.SonyChannel;
import com.ruoyi.maintenance.domain.SonyChannelCategory;
import com.ruoyi.maintenance.domain.base.SonyChannelBaseEntity;
import com.ruoyi.maintenance.domain.dto.SonyChannelCategoryDTO;
import com.ruoyi.maintenance.domain.dto.SonyChannelDTO;
import com.ruoyi.maintenance.domain.excel.SonyChannelExcelVO;
import com.ruoyi.maintenance.domain.excel.SonyChannelImportVO;
import com.ruoyi.maintenance.domain.vo.SonyChannelVO;
import com.ruoyi.maintenance.mapper.SonyChannelCategoryMapper;
import com.ruoyi.maintenance.mapper.SonyChannelMapper;
import com.ruoyi.maintenance.service.IQrCodeService;
import com.ruoyi.maintenance.service.ISonyChannelCategoryService;
import com.ruoyi.maintenance.service.ISonyChannelService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 渠道信息Service业务层处理
 *
 * @author Abel
 * @date 2023-02-01
 */
@Service
public class SonyChannelServiceImpl implements ISonyChannelService {
    private final Logger logger = LoggerFactory.getLogger ( getClass () );
    @Resource
    private SonyChannelMapper sonyChannelMapper;
    @Resource
    private RedisCache redisCache;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private StringUtils stringUtils;
    @Resource
    private ISonyChannelService sonyChannelService;
    @Resource
    private IQrCodeService qrCodeService;

    @Resource
    private ISonyChannelCategoryService sonyChannelCategoryService;

    @Resource
    private SonyChannelCategoryMapper sonyChannelCategoryMapper;

    /**
     * 查询渠道信息
     *
     * @param id 渠道信息主键
     * @return 渠道信息
     */
    @Override
    public SonyChannel selectSonyChannelById ( Long id ) {
        return sonyChannelMapper.selectSonyChannelById ( id );
    }

    @Override
    public List <SonyChannel> selectSonyChannelList ( SonyChannel sonyChannel ) {
        return sonyChannelMapper.selectSonyChannelList ( sonyChannel );
    }

    /**
     * 查询渠道信息列表
     *
     * @param sonyChannel 渠道信息
     * @return 渠道信息
     */
    @Override
    public List <SonyChannelVO> selectSonyChannelListByDTO ( SonyChannelDTO sonyChannel ) {
        List <SonyChannelVO> list = sonyChannelMapper.selectSonyChannelListByDTO ( sonyChannel );
        if ( list == null || list.isEmpty () ) {
            return Collections.emptyList ();
        }
        Map <String, String> channelIdToNameMap = getChannelIdToNameMap ( list );

        list.forEach ( e -> {
            String channelCode = e.getChannelCode ();
            String qrCodePath = FileUtils.getQrCodeUrl ( channelCode );
            String qrCodeWithLogoPath = FileUtils.getQrCodeWithLogoUrl ( channelCode );
            e.setQrCode ( qrCodePath );
            e.setQrCodeWithLogo ( qrCodeWithLogoPath );

            // 从map中获取渠道名赋值给vo
            e.setPrimaryChannelId(e.getPrimaryChannel());
            e.setSecondaryChannelId(e.getSecondaryChannel());
            e.setPrimaryChannel ( channelIdToNameMap.get (  e.getPrimaryChannel () ) );
            if ( e.getSecondaryChannel () != null && !e.getSecondaryChannel ().equals ( "" ) ) {
                e.setSecondaryChannel ( channelIdToNameMap.get ( e.getSecondaryChannel ()  ) );
            }
        } );
        return list;
    }

    /**
     * 新增渠道信息
     *
     * @param sonyChannel 渠道信息
     * @return 结果
     */
    @Override
    public int insertSonyChannel ( SonyChannel sonyChannel ) {
        sonyChannel.setCreateTime ( DateUtils.getNowDate () );
        return sonyChannelMapper.insertSonyChannel ( sonyChannel );
    }

    /**
     * 修改渠道信息
     *
     * @param sonyChannel 渠道信息
     * @return 结果
     */
    @Override
    public int updateSonyChannel ( SonyChannel sonyChannel ) {
        String primaryChannel = sonyChannel.getPrimaryChannel ();
        ExceptionAssert.throwException ( primaryChannel == null || primaryChannel.equals ( "" ), "一级渠道不能为空" );
        // 渠道名存在校验
        SonyChannelCategoryDTO sonyChannelCategoryDTO = sonyChannelCategoryService.getSonyChannelCategoryDTO ( sonyChannel.getPrimaryChannel (), sonyChannel.getSecondaryChannel () );
        sonyChannelCategoryService.checkChannelName ( sonyChannelCategoryDTO );

        sonyChannel.setUpdateTime ( DateUtils.getNowDate () );
        sonyChannel.setUpdatedBy ( SecurityUtils.getUsername () );
        return sonyChannelMapper.updateSonyChannel ( sonyChannel );
    }

    /**
     * 批量删除渠道信息
     *
     * @param ids 需要删除的渠道信息主键
     * @return 结果
     */
    @Override
    public int deleteSonyChannelByIds ( Long[] ids ) {
        return sonyChannelMapper.deleteSonyChannelByIds ( ids );
    }

    /**
     * 删除渠道信息信息
     *
     * @param id 渠道信息主键
     * @return 结果
     */
    @Override
    public int deleteSonyChannelById ( Long id ) {
        return sonyChannelMapper.deleteSonyChannelById ( id );
    }

    @Override
    public List <SonyChannelExcelVO> selectSonyChannelListByIds ( List <Integer> ids ) {
        List <SonyChannelExcelVO> list = sonyChannelMapper.selectSonyChannelListByIds ( ids );
        if ( list == null || list.isEmpty () ) {
            return Collections.emptyList ();
        }
        Map <String, String> idToNameMap = getChannelIdToNameMap ( list );

        // 将渠道id替换为渠道名
        for ( SonyChannelExcelVO sonyChannelExcelVO : list ) {
            String primaryChannel = idToNameMap.get ( sonyChannelExcelVO.getPrimaryChannel () );
            String secondaryChannel = idToNameMap.get ( sonyChannelExcelVO.getSecondaryChannel () );
            sonyChannelExcelVO.setPrimaryChannel ( primaryChannel );
            sonyChannelExcelVO.setSecondaryChannel ( secondaryChannel );
        }
        return list;
    }

    public Map <String, String> getChannelIdToNameMap ( List <? extends SonyChannelBaseEntity> list ) {
        // 获取list中所有的渠道id
        List <Integer> channelIds = list.stream ()
                .map ( e -> Integer.valueOf ( e.getPrimaryChannel () ) ).distinct ().collect ( Collectors.toList () );
        channelIds.addAll (
                list.stream ()
                        .filter ( e -> e.getSecondaryChannel () != null && !e.getSecondaryChannel ().equals ( "" ) )
                        .map ( e -> Integer.valueOf ( e.getSecondaryChannel () ) )
                        .distinct ()
                        .collect ( Collectors.toList () ) );

        // 渠道id:name的map
        List <SonyChannelCategory> sonyChannelCategoryList =
                sonyChannelCategoryService.selectSonyChannelCategoryByIds ( channelIds );
        return sonyChannelCategoryList.stream ()
                .collect ( Collectors.toMap ( e -> e.getId ().toString (), SonyChannelCategory::getChannelName ) );
    }

    @Override
    public List <SonyChannel> selectSonyChannelByIds ( List <Long> ids ) {
        return sonyChannelMapper.selectSonyChannelByIds ( ids );
    }

    @Override
    public void batchDelete ( List <Long> ids ) {
        sonyChannelMapper.batchDelete ( ids );
    }

    @Override
    public void addChannel ( SonyChannel sonyChannel ) {
        // 渠道存在校验
        SonyChannelCategoryDTO sonyChannelCategoryDTO = sonyChannelCategoryService.getSonyChannelCategoryDTO ( sonyChannel.getPrimaryChannel (), sonyChannel.getSecondaryChannel () );
        sonyChannelCategoryService.checkChannelName ( sonyChannelCategoryDTO );

        // 生成channelCode
        String channelCode = stringUtils.getChannelCode ();

        // 保存渠道信息
        sonyChannel.setChannelCode ( channelCode );
        sonyChannel.setCreatedBy ( SecurityUtils.getUsername () );
        sonyChannelService.insertSonyChannel ( sonyChannel );

        // 生成并保存渠道码及微信二维码跳转url
        QrCodeResponseBody qrCodeResponseBody = null;
        try {
            qrCodeResponseBody = sonyChannelService.generateQrCodeUrlAndSave ( sonyChannel.getId (), channelCode );
        } catch ( WxErrorException e ) {
            // 抛异常需要恢复当天新增渠道数
            Integer channelCodeVal = (Integer) redisCache.getCacheObject ( redisUtils.getChannelCodeKey () );
            redisCache.setCacheObject ( redisUtils.getChannelCodeKey (), --channelCodeVal );
            logger.error ( "添加编号为{}的渠道获取微信access_token出错", sonyChannel.getId (), e );
            throw new ServiceException ( "添加渠道失败", HttpStatus.ERROR );
        } catch ( IOException | WriterException e ) {
            // 抛异常需要恢复当天新增渠道数
            Integer channelCodeVal = (Integer) redisCache.getCacheObject ( redisUtils.getChannelCodeKey () );
            redisCache.setCacheObject ( redisUtils.getChannelCodeKey (), --channelCodeVal );
            logger.error ( "添加id为 {}的渠道 生成渠道码出错", sonyChannel.getId (), e );
            throw new ServiceException ( "添加渠道失败", HttpStatus.ERROR );
        }
        sonyChannel.setQrcodeUrl ( qrCodeResponseBody.getUrl () );
        sonyChannel.setTicket ( qrCodeResponseBody.getTicket () );

        // 更新该渠道qrCodeUrl和ticket
        sonyChannelService.updateSonyChannel ( sonyChannel );
        logger.info ( "添加渠道 {} 成功. 渠道信息: {}", sonyChannel.getPrimaryChannel (), sonyChannel );
    }

    public QrCodeResponseBody generateQrCodeUrlAndSave ( long id, String channelCode ) throws WxErrorException, IOException, WriterException {
        QrCodeResponseBody qrCodeResponseBody = null;
        // 获取微信二维码响应
        qrCodeResponseBody = qrCodeService.getQrCodeResponseBody ( (int) id );
        // 生成渠道码到指定位置
        qrCodeService.saveQrCode ( qrCodeResponseBody.getUrl (), channelCode );
        return qrCodeResponseBody;
    }

    @Override
    public void batchInsert ( List <SonyChannelImportVO> list ) {
        //  批量保存
        for ( SonyChannelImportVO sonyChannelImportVO : list ) {
            sonyChannelImportVO.setCreateTime ( new Date () );
            sonyChannelImportVO.setCreatedBy ( SecurityUtils.getUsername () );
            sonyChannelImportVO.setChannelCode ( stringUtils.getChannelCode () );
        }
        sonyChannelMapper.batchInsert ( list );
    }

    @Override
    public void batchUpdateSonyChannel ( List <SonyChannelImportVO> list ) {
        for ( SonyChannelImportVO sonyChannelImportVO : list ) {
            sonyChannelImportVO.setUpdateTime ( new Date () );
            sonyChannelImportVO.setUpdatedBy ( SecurityUtils.getUsername () );
        }
        sonyChannelMapper.batchUpdateSonyChannel ( list );
    }

    @Override
    @Transactional
    public void batchAddChannel ( List <SonyChannelImportVO> list ) {
        for ( SonyChannelImportVO sonyChannelImportVO : list ) {
            SonyChannelCategoryDTO sonyChannelCategoryDTO = sonyChannelCategoryService.getSonyChannelCategoryDTO ( sonyChannelImportVO.getPrimaryChannel (), sonyChannelImportVO.getSecondaryChannel () );
            logger.error ( "插入渠道: {} 出错", sonyChannelImportVO );
            SonyChannelCategoryDTO channelCategoryDTO = sonyChannelCategoryService.checkChannelNameByName ( sonyChannelCategoryDTO );

            // 将渠道名字转换为渠道id
            sonyChannelImportVO.setPrimaryChannel ( channelCategoryDTO.getPrimaryChannel () );
            sonyChannelImportVO.setSecondaryChannel ( channelCategoryDTO.getSecondaryChannel () );
        }
        sonyChannelService.batchInsert ( list );
        // TODO 考虑多线程优化
        // 生成并保存二维码, 返回微信二维码跳转url
        for ( SonyChannelImportVO sonyChannelImportVO : list ) {
            Long id = sonyChannelImportVO.getId ();
            // 获取微信二维码跳转url
            QrCodeResponseBody qrCodeResponseBody = null;
            try {
                qrCodeResponseBody = sonyChannelService.generateQrCodeUrlAndSave ( id, sonyChannelImportVO.getChannelCode () );
            } catch ( WxErrorException e ) {
                // 抛异常需要恢复当天新增渠道数
                Integer channelCodeVal = (Integer) redisCache.getCacheObject ( redisUtils.getChannelCodeKey () );
                redisCache.setCacheObject ( redisUtils.getChannelCodeKey (), --channelCodeVal );
                logger.error ( "添加编号为{}的渠道获取微信access_token出错", id, e );
                throw new ServiceException ( "导入渠道失败", HttpStatus.ERROR );
            } catch ( IOException | WriterException e ) {
                // 抛异常需要恢复当天新增渠道数
                Integer channelCodeVal = (Integer) redisCache.getCacheObject ( redisUtils.getChannelCodeKey () );
                redisCache.setCacheObject ( redisUtils.getChannelCodeKey (), --channelCodeVal );
                logger.error ( "添加id为 {}的渠道 生成渠道码出错", id, e );
                throw new ServiceException ( "添加渠道失败", HttpStatus.ERROR );
            }
            sonyChannelImportVO.setQrcodeUrl ( qrCodeResponseBody.getUrl () );
            sonyChannelImportVO.setTicket ( qrCodeResponseBody.getTicket () );
        }
        // 更新该渠道qrCodeUrl和ticket
        sonyChannelService.batchUpdateSonyChannel ( list );
    }

    /**
     * 重新生成渠道码
     */
    @Override
    public void regenerateQrCode ( List <Long> ids ) {
        List <SonyChannel> sonyChannelList = sonyChannelService.selectSonyChannelByIds ( ids );
        ExceptionAssert.throwException ( sonyChannelList.isEmpty (), "不存在该渠道, 无法重新生成渠道码" );
        // 获取id:SonyChannel的map
        Map <Long, SonyChannel> idToChannelCodeMap = sonyChannelList.stream ()
                .collect ( Collectors.toMap ( SonyChannel::getId, Function.identity () ) );

        for ( Map.Entry <Long, SonyChannel> entry : idToChannelCodeMap.entrySet () ) {
            Long id = entry.getKey ();
            SonyChannel sonyChannel = entry.getValue ();
            String channelCode = sonyChannel.getChannelCode ();

            try {
                // 删除已存在的二维码
                String qrCodePath = FileUtils.getQrCodePath ( channelCode );
                String qrCodeWithLogoPath = FileUtils.getQrCodeWithLogoPath ( channelCode );
                FileUtils.removeFile ( qrCodePath );
                FileUtils.removeFile ( qrCodeWithLogoPath );

                // 调微信接口获取url和ticket, 生成二维码并保存
                QrCodeResponseBody qrCodeResponseBody = sonyChannelService.generateQrCodeUrlAndSave ( id, channelCode );
                String url = qrCodeResponseBody.getUrl ();
                String ticket = qrCodeResponseBody.getTicket ();

                // 更新渠道信息
                sonyChannel.setQrcodeUrl ( url );
                sonyChannel.setTicket ( ticket );
                sonyChannelService.updateSonyChannel ( sonyChannel );
            } catch ( WxErrorException | IOException | WriterException e ) {
                logger.error ( "id为: {}的渠道重新生成二维码出错", sonyChannel.getId () );
                throw new ServiceException ( sonyChannel.getMaintenanceStationName () + "重新生成二维码出错" );
            }
        }
    }
}
