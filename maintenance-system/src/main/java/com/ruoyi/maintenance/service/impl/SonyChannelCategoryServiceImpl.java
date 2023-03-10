package com.ruoyi.maintenance.service.impl;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.maintenance.wechat.exception.ExceptionAssert;
import com.ruoyi.maintenance.domain.SonyChannelCategory;
import com.ruoyi.maintenance.domain.dto.SonyChannelCategoryDTO;
import com.ruoyi.maintenance.domain.excel.SonyChannelCategoryExportVO;
import com.ruoyi.maintenance.domain.vo.SonyChannelCategoryIndexVO;
import com.ruoyi.maintenance.domain.vo.SonyChannelCategoryVO;
import com.ruoyi.maintenance.mapper.SonyChannelCategoryMapper;
import com.ruoyi.maintenance.service.ISonyChannelCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 渠道关系Service业务层处理
 *
 * @author Abel
 * @date 2023-02-07
 */
@Service
public class SonyChannelCategoryServiceImpl implements ISonyChannelCategoryService {
    @Autowired
    private SonyChannelCategoryMapper sonyChannelCategoryMapper;

    /**
     * 查询渠道关系
     *
     * @param id 渠道关系主键
     * @return 渠道关系
     */
    @Override
    public SonyChannelCategory selectSonyChannelCategoryById ( Integer id ) {
        return sonyChannelCategoryMapper.selectSonyChannelCategoryById ( id );
    }

    /**
     * 查询渠道关系列表
     *
     * @param sonyChannelCategory 渠道关系
     * @return 渠道关系
     */
    @Override
    public List <SonyChannelCategory> selectSonyChannelCategoryList ( SonyChannelCategory sonyChannelCategory ) {
        return sonyChannelCategoryMapper.selectSonyChannelCategoryList ( sonyChannelCategory );
    }

    /**
     * 新增渠道关系
     *
     * @param sonyChannelCategory 渠道关系
     * @return 结果
     */
    @Override
    public void insertSonyChannelCategory ( SonyChannelCategory sonyChannelCategory ) {
        Integer parentId = sonyChannelCategory.getParentId ();
        // 如果parentId为空, 创建一级渠道
        if ( parentId == null ) {
            sonyChannelCategory.setParentId ( -1 );
        }
        sonyChannelCategory.setCreatedBy ( SecurityUtils.getUsername () );
        sonyChannelCategory.setCreateTime ( new Date () );
        try {
            sonyChannelCategoryMapper.insertSonyChannelCategory ( sonyChannelCategory );
        } catch ( Exception e ) {
            throw new ServiceException ( "新增渠道 " + sonyChannelCategory.getChannelName () + " 失败", e );
        }
    }

    /**
     * 修改渠道关系
     *
     * @param sonyChannelCategory 渠道关系
     * @return 结果
     */
    @Override
    public int updateSonyChannelCategory ( SonyChannelCategory sonyChannelCategory ) {
        return sonyChannelCategoryMapper.updateSonyChannelCategory ( sonyChannelCategory );
    }

    /**
     * 批量删除渠道关系
     *
     * @param ids 需要删除的渠道关系主键
     * @return 结果
     */
    @Override
    public int deleteSonyChannelCategoryByIds ( List <Integer> ids ) {
        List <Integer> idList = sonyChannelCategoryMapper.selectChannelListByParentId ( ids );
        idList.addAll ( ids );
        List <Integer> list = idList.stream ().distinct ().collect ( Collectors.toList () );
        return sonyChannelCategoryMapper.deleteSonyChannelCategoryByIds ( list );
    }

    /**
     * 删除渠道关系信息
     *
     * @param id 渠道关系主键
     * @return 结果
     */
    @Override
    public int deleteSonyChannelCategoryById ( Integer id ) {
        return sonyChannelCategoryMapper.deleteSonyChannelCategoryById ( id );
    }

    @Override
    public List <SonyChannelCategoryVO> selectChannelList ( SonyChannelCategory sonyChannelCategory ) {
        return sonyChannelCategoryMapper.selectChannelList ( sonyChannelCategory );
    }

    @Override
    public List <SonyChannelCategoryVO> selectSecondaryChannelList ( SonyChannelCategory sonyChannelCategory ) {
        return sonyChannelCategoryMapper.selectSecondaryChannelList ( sonyChannelCategory );
    }

    @Override
    public void deleteSonyChannelCategoryByChannelName ( String channelName ) {
        SonyChannelCategoryVO sonyChannelCategoryVO = sonyChannelCategoryMapper.selectChannelCategoryByChannelName ( channelName );
        ExceptionAssert.throwException ( sonyChannelCategoryVO == null, "" );

        // 根据名字查到的渠道是一级渠道
        if ( sonyChannelCategoryVO.getParentId () == -1 ) {
            // 如果有二级渠道, 不允许直接删除一级渠道
            SonyChannelCategory entity = new SonyChannelCategory ();
            entity.setParentId ( sonyChannelCategoryVO.getId () );
            List <SonyChannelCategory> sonyChannelCategoryList = sonyChannelCategoryMapper.selectSonyChannelCategoryList ( entity );
            if ( !sonyChannelCategoryList.isEmpty () ) {
                throw new ServiceException ( "该渠道有次级渠道， 不允许直接删除", HttpStatus.ERROR );
            }
        }
        sonyChannelCategoryMapper.deleteSonyChannelCategoryByChannelName ( channelName );
    }

    @Override
    public List <SonyChannelCategoryVO> selectChannelListByChannelId ( Integer id ) {
        SonyChannelCategory sonyChannelCategory = new SonyChannelCategory ();
        sonyChannelCategory.setId ( id );
        sonyChannelCategory.setParentId ( -1 );
        return sonyChannelCategoryMapper.selectChannelList ( sonyChannelCategory );
    }

    @Override
    public List <SonyChannelCategoryIndexVO> selectChannelCategoryListByChannelCategoryDTO ( SonyChannelCategoryDTO dto ) {
        return sonyChannelCategoryMapper.selectChannelCategoryListByChannelCategoryDTO ( dto );
    }

    @Override
    public void checkChannelName ( SonyChannelCategoryDTO sonyChannelCategoryDTO ) {
        // 渠道存在校验
        SonyChannelCategory primaryChannelCategory = sonyChannelCategoryMapper
                .selectSonyChannelCategoryById ( Integer.valueOf ( sonyChannelCategoryDTO.getPrimaryChannel () ) );
        ExceptionAssert.throwException ( primaryChannelCategory == null, "一级渠道不存在" );
        String secondaryChannel = sonyChannelCategoryDTO.getSecondaryChannel ();
        if ( secondaryChannel != null && !secondaryChannel.equals ( "" ) ) {
            SonyChannelCategory secondaryChannelCategory = sonyChannelCategoryMapper
                    .selectSonyChannelCategoryById ( Integer.valueOf ( secondaryChannel ) );
            ExceptionAssert.throwException ( secondaryChannelCategory == null, "二级渠道不存在" );
        }
    }

    @Override
    public SonyChannelCategoryDTO checkChannelNameByName ( SonyChannelCategoryDTO sonyChannelCategoryDTO ) {
        SonyChannelCategoryDTO channelCategoryDTO = new SonyChannelCategoryDTO ();
        // 渠道存在校验
        SonyChannelCategory primaryChannelCategory = sonyChannelCategoryMapper.selectSonyChannelCategoryByChannelName ( sonyChannelCategoryDTO.getPrimaryChannel () );
        ExceptionAssert.throwException ( primaryChannelCategory == null || primaryChannelCategory.getParentId () != -1, "一级渠道不存在" );

        channelCategoryDTO.setPrimaryChannel ( primaryChannelCategory.getId ().toString () );
        String secondaryChannel = sonyChannelCategoryDTO.getSecondaryChannel ();
        SonyChannelCategory secondaryChannelCategory = null;
        if ( secondaryChannel != null && !secondaryChannel.equals ( "" ) ) {
            secondaryChannelCategory = sonyChannelCategoryMapper.selectSonyChannelCategoryByChannelName ( secondaryChannel );
            ExceptionAssert.throwException ( secondaryChannelCategory == null || secondaryChannelCategory.getParentId () == -1, "二级渠道不存在" );
        }
        channelCategoryDTO.setSecondaryChannel ( secondaryChannelCategory.getId ().toString () );
        return channelCategoryDTO;
    }

    @Override
    public List <SonyChannelCategoryExportVO> selectSonyChannelCategoryIndexVOByIds ( List <Integer> ids ) {
        return sonyChannelCategoryMapper.selectSonyChannelCategoryIndexVOByIds ( ids );
    }

    @Override
    public List <SonyChannelCategory> selectSonyChannelCategoryByIds ( List <Integer> idList ) {
        return sonyChannelCategoryMapper.selectChannelListByIds ( idList );
    }

    @Override
    public void batchInsert ( List <SonyChannelCategory> sonyChannelCategoryList ) {
        sonyChannelCategoryMapper.batchInsertSonyChannelCategory ( sonyChannelCategoryList );
    }

    @Override
    public void batchUpdate ( List <SonyChannelCategory> sonyChannelCategoryList ) {
        sonyChannelCategoryMapper.batchUpdateSonyChannelCategory ( sonyChannelCategoryList );
    }

    public SonyChannelCategoryDTO getSonyChannelCategoryDTO ( String primaryChannel, String secondaryChannel ) {
        SonyChannelCategoryDTO sonyChannelCategoryDTO = new SonyChannelCategoryDTO ();
        sonyChannelCategoryDTO.setPrimaryChannel ( primaryChannel );
        sonyChannelCategoryDTO.setSecondaryChannel ( secondaryChannel );
        return sonyChannelCategoryDTO;
    }
}
