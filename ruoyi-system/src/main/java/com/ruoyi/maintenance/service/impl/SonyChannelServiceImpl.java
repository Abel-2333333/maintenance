package com.ruoyi.maintenance.service.impl;

import com.google.zxing.WriterException;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.wechat.entity.QrCodeResponseBody;
import com.ruoyi.common.wechat.exception.ExceptionAssert;
import com.ruoyi.common.wechat.util.RedisUtils;
import com.ruoyi.common.wechat.util.StringUtils;
import com.ruoyi.maintenance.domain.SonyChannel;
import com.ruoyi.maintenance.domain.SonyChannelCategory;
import com.ruoyi.maintenance.domain.dto.SonyChannelCategoryDTO;
import com.ruoyi.maintenance.domain.dto.SonyChannelDTO;
import com.ruoyi.maintenance.domain.excel.SonyChannelExcelVO;
import com.ruoyi.maintenance.domain.excel.SonyChannelImportVO;
import com.ruoyi.maintenance.domain.vo.SonyChannelVO;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 渠道信息Service业务层处理
 *
 * @author Abel
 * @date 2023-02-01
 */
@Service
public class SonyChannelServiceImpl implements ISonyChannelService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
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
	
	/**
	 * 查询渠道信息
	 *
	 * @param id 渠道信息主键
	 * @return 渠道信息
	 */
	@Override
	public SonyChannel selectSonyChannelById(Long id) {
		return sonyChannelMapper.selectSonyChannelById(id);
	}
	
	@Override
	public List<SonyChannel> selectSonyChannelList(SonyChannel sonyChannel) {
		return sonyChannelMapper.selectSonyChannelList(sonyChannel);
	}
	
	/**
	 * 查询渠道信息列表
	 *
	 * @param sonyChannel 渠道信息
	 * @return 渠道信息
	 */
	@Override
	public List<SonyChannelVO> selectSonyChannelListByDTO(SonyChannelDTO sonyChannel) {
		return sonyChannelMapper.selectSonyChannelListByDTO(sonyChannel);
	}
	
	/**
	 * 新增渠道信息
	 *
	 * @param sonyChannel 渠道信息
	 * @return 结果
	 */
	@Override
	public int insertSonyChannel(SonyChannel sonyChannel) {
		sonyChannel.setCreateTime(DateUtils.getNowDate());
		return sonyChannelMapper.insertSonyChannel(sonyChannel);
	}
	
	/**
	 * 修改渠道信息
	 *
	 * @param sonyChannel 渠道信息
	 * @return 结果
	 */
	@Override
	public int updateSonyChannel(SonyChannel sonyChannel) {
		String primaryChannel = sonyChannel.getPrimaryChannel();
		ExceptionAssert.throwException(primaryChannel == null, "一级渠道不能为空");
		// 渠道名存在校验
		SonyChannelCategoryDTO sonyChannelCategoryDTO = sonyChannelCategoryService.getSonyChannelCategoryDTO(sonyChannel.getPrimaryChannel(), sonyChannel.getSecondaryChannel());
		sonyChannelCategoryService.checkChannelName(sonyChannelCategoryDTO);
		
		sonyChannel.setUpdateTime(DateUtils.getNowDate());
		sonyChannel.setUpdatedBy(SecurityUtils.getUsername());
		return sonyChannelMapper.updateSonyChannel(sonyChannel);
	}
	
	/**
	 * 批量删除渠道信息
	 *
	 * @param ids 需要删除的渠道信息主键
	 * @return 结果
	 */
	@Override
	public int deleteSonyChannelByIds(Long[] ids) {
		return sonyChannelMapper.deleteSonyChannelByIds(ids);
	}
	
	/**
	 * 删除渠道信息信息
	 *
	 * @param id 渠道信息主键
	 * @return 结果
	 */
	@Override
	public int deleteSonyChannelById(Long id) {
		return sonyChannelMapper.deleteSonyChannelById(id);
	}
	
	@Override
	public List<SonyChannelExcelVO> selectSonyChannelListByIds(List<Integer> ids) {
		List<SonyChannelExcelVO> list = sonyChannelMapper.selectSonyChannelListByIds(ids);
		
		// 获取list中所有的渠道id
		List<Integer> idList = list.stream().map(e->Integer.valueOf(e.getPrimaryChannel())).distinct().collect(Collectors.toList());
		idList.addAll( list.stream().map(e->Integer.valueOf(e.getSecondaryChannel())).distinct().collect(Collectors.toList()));
		
		// 渠道id:name的map
		List<SonyChannelCategory> sonyChannelCategoryList = sonyChannelCategoryService.selectSonyChannelCategoryByIds(idList);
		Map<String, String> idToNameMap = sonyChannelCategoryList.stream().collect(Collectors.toMap(e->e.getId().toString(), SonyChannelCategory::getChannelName));
		
		// 将渠道id替换为渠道名
		for (SonyChannelExcelVO sonyChannelExcelVO : list) {
			String primaryChannel = idToNameMap.get(sonyChannelExcelVO.getPrimaryChannel());
			String secondaryChannel = idToNameMap.get(sonyChannelExcelVO.getSecondaryChannel());
			sonyChannelExcelVO.setPrimaryChannel(primaryChannel);
			sonyChannelExcelVO.setSecondaryChannel(secondaryChannel);
		}
		return list;
	}
	
	@Override
	public List<SonyChannel> selectSonyChannelByIds(Long[] ids) {
		return sonyChannelMapper.selectSonyChannelByIds(ids);
	}
	
	@Override
	public int batchUpdateSonyChannel(Long[] ids) {
		return sonyChannelMapper.batchUpdate(ids);
	}
	
	@Override
	public void addChannel(SonyChannel sonyChannel) {
		// 渠道存在校验
		SonyChannelCategoryDTO sonyChannelCategoryDTO = sonyChannelCategoryService.getSonyChannelCategoryDTO(sonyChannel.getPrimaryChannel(), sonyChannel.getSecondaryChannel());
		sonyChannelCategoryService.checkChannelName(sonyChannelCategoryDTO);
		
		// 生成channelCode
		String channelCode = stringUtils.getChannelCode();
		
		// 保存渠道信息
		sonyChannel.setChannelCode(channelCode);
		sonyChannel.setCreatedBy(SecurityUtils.getUsername());
		sonyChannelService.insertSonyChannel(sonyChannel);
		
		// 生成并保存渠道码及微信二维码跳转url
		QrCodeResponseBody qrCodeResponseBody = generateQrCodeUrlAndSave(sonyChannel.getId(), channelCode);
		sonyChannel.setQrcodeUrl(qrCodeResponseBody.getUrl());
		sonyChannel.setTicket(qrCodeResponseBody.getTicket());
		
		// 更新该渠道qrCodeUrl和ticket
		sonyChannelService.updateSonyChannel(sonyChannel);
		logger.info("添加渠道 {} 成功. 渠道信息: {}", sonyChannel.getPrimaryChannel(), sonyChannel);
	}
	
	private QrCodeResponseBody generateQrCodeUrlAndSave(long id, String channelCode) {
		QrCodeResponseBody qrCodeResponseBody = null;
		try {
			// 获取微信二维码响应
			qrCodeResponseBody = qrCodeService.getQrCodeResponseBody((int) id);
			// 生成渠道码到指定位置
			qrCodeService.saveQrCode(qrCodeResponseBody.getUrl(), channelCode);
		} catch (WxErrorException e) {
			// 抛异常需要恢复当天新增渠道数
			Integer channelCodeVal = (Integer) redisCache.getCacheObject(redisUtils.getChannelCodeKey());
			redisCache.setCacheObject(redisUtils.getChannelCodeKey(), --channelCodeVal);
			logger.error("添加编号为{}的渠道获取微信access_token出错", id, e);
			throw new ServiceException("添加渠道失败", HttpStatus.ERROR);
		} catch (IOException | WriterException e) {
			// 抛异常需要恢复当天新增渠道数
			Integer channelCodeVal = (Integer) redisCache.getCacheObject(redisUtils.getChannelCodeKey());
			redisCache.setCacheObject(redisUtils.getChannelCodeKey(), --channelCodeVal);
			logger.error("添加id为 {}的渠道 生成渠道码出错", id, e);
			throw new ServiceException("添加渠道失败", HttpStatus.ERROR);
		}
		return qrCodeResponseBody;
	}
	
	@Override
	public void batchInsert(List<SonyChannelImportVO> list) {
		//  批量保存
		for (SonyChannelImportVO sonyChannelImportVO : list) {
			sonyChannelImportVO.setCreateTime(new Date());
			sonyChannelImportVO.setCreatedBy(SecurityUtils.getUsername());
			sonyChannelImportVO.setChannelCode(stringUtils.getChannelCode());
		}
		sonyChannelMapper.batchInsert(list);
	}
	
	@Override
	public void batchUpdateSonyChannel(List<SonyChannelImportVO> list) {
		for (SonyChannelImportVO sonyChannelImportVO : list) {
			sonyChannelImportVO.setUpdateTime(new Date());
			sonyChannelImportVO.setUpdatedBy(SecurityUtils.getUsername());
		}
		sonyChannelMapper.batchUpdateSonyChannel(list);
	}
	
	@Override
	@Transactional
	public void batchAddChannel(List<SonyChannelImportVO> list) {
		for (SonyChannelImportVO sonyChannelImportVO : list) {
			SonyChannelCategoryDTO sonyChannelCategoryDTO = sonyChannelCategoryService.getSonyChannelCategoryDTO(sonyChannelImportVO.getPrimaryChannel(), sonyChannelImportVO.getSecondaryChannel());
			logger.error("插入渠道: {} 出错", sonyChannelImportVO);
			sonyChannelCategoryService.checkChannelName(sonyChannelCategoryDTO);
		}
		sonyChannelService.batchInsert(list);
		// TODO 考虑多线程优化
		// 生成并保存二维码, 返回微信二维码跳转url
		for (SonyChannelImportVO sonyChannelImportVO : list) {
			Long id = sonyChannelImportVO.getId();
			// 获取微信二维码跳转url
			QrCodeResponseBody qrCodeResponseBody = generateQrCodeUrlAndSave(id, sonyChannelImportVO.getChannelCode());
			sonyChannelImportVO.setQrcodeUrl(qrCodeResponseBody.getUrl());
			sonyChannelImportVO.setTicket(qrCodeResponseBody.getTicket());
		}
		// 更新该渠道qrCodeUrl和ticket
		sonyChannelService.batchUpdateSonyChannel(list);
	}
}
