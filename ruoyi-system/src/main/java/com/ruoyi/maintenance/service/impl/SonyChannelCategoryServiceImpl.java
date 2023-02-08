package com.ruoyi.maintenance.service.impl;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.wechat.exception.ExceptionAssert;
import com.ruoyi.maintenance.domain.SonyChannelCategory;
import com.ruoyi.maintenance.domain.dto.SonyChannelCategoryDTO;
import com.ruoyi.maintenance.domain.vo.SonyChannelCategoryIndexVO;
import com.ruoyi.maintenance.domain.vo.SonyChannelCategoryVO;
import com.ruoyi.maintenance.mapper.SonyChannelCategoryMapper;
import com.ruoyi.maintenance.service.ISonyChannelCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
	public SonyChannelCategory selectSonyChannelCategoryById(Integer id) {
		return sonyChannelCategoryMapper.selectSonyChannelCategoryById(id);
	}
	
	/**
	 * 查询渠道关系列表
	 *
	 * @param sonyChannelCategory 渠道关系
	 * @return 渠道关系
	 */
	@Override
	public List<SonyChannelCategory> selectSonyChannelCategoryList(SonyChannelCategory sonyChannelCategory) {
		return sonyChannelCategoryMapper.selectSonyChannelCategoryList(sonyChannelCategory);
	}
	
	/**
	 * 新增渠道关系
	 *
	 * @param sonyChannelCategory 渠道关系
	 * @return 结果
	 */
	@Override
	public void insertSonyChannelCategory(SonyChannelCategory sonyChannelCategory) {
		Integer parentId = sonyChannelCategory.getParentId();
		// 如果parentId为空, 创建一级渠道
		if (parentId == null) {
			sonyChannelCategory.setParentId(-1);
		}
		sonyChannelCategory.setCreatedBy(SecurityUtils.getUsername());
		sonyChannelCategory.setCreateTime(new Date());
		try {
			sonyChannelCategoryMapper.insertSonyChannelCategory(sonyChannelCategory);
		} catch (Exception e) {
			throw new ServiceException("新增渠道 "+sonyChannelCategory.getChannelName() + " 失败", e );
		}
	}
	
	/**
	 * 修改渠道关系
	 *
	 * @param sonyChannelCategory 渠道关系
	 * @return 结果
	 */
	@Override
	public int updateSonyChannelCategory(SonyChannelCategory sonyChannelCategory) {
		return sonyChannelCategoryMapper.updateSonyChannelCategory(sonyChannelCategory);
	}
	
	/**
	 * 批量删除渠道关系
	 *
	 * @param ids 需要删除的渠道关系主键
	 * @return 结果
	 */
	@Override
	public int deleteSonyChannelCategoryByIds(Long[] ids) {
		List<SonyChannelCategory> sonyChannelCategoryList = sonyChannelCategoryMapper.selectChannelListByIds(ids);
		boolean notPrimaryChannel = sonyChannelCategoryList.stream().anyMatch(e -> e.getParentId() != -1);
		if (notPrimaryChannel) {
			throw new ServiceException("渠道中包含二级渠道", HttpStatus.ERROR);
		}
		return sonyChannelCategoryMapper.deleteSonyChannelCategoryByIds(ids);
	}
	
	/**
	 * 删除渠道关系信息
	 *
	 * @param id 渠道关系主键
	 * @return 结果
	 */
	@Override
	public int deleteSonyChannelCategoryById(Long id) {
		return sonyChannelCategoryMapper.deleteSonyChannelCategoryById(id);
	}
	
	@Override
	public List<SonyChannelCategoryVO> selectChannelList(SonyChannelCategory sonyChannelCategory) {
		return sonyChannelCategoryMapper.selectChannelList(sonyChannelCategory);
	}
	
	@Override
	public List<SonyChannelCategoryVO> selectSecondaryChannelList(SonyChannelCategory sonyChannelCategory) {
		return sonyChannelCategoryMapper.selectSecondaryChannelList(sonyChannelCategory);
	}
	
	@Override
	public void deleteSonyChannelCategoryByChannelName(String channelName) {
		SonyChannelCategoryVO sonyChannelCategoryVO = sonyChannelCategoryMapper.selectChannelCategoryByChannelName(channelName);
		ExceptionAssert.throwException(sonyChannelCategoryVO == null, "");

		// 根据名字查到的渠道是一级渠道
		if (sonyChannelCategoryVO.getParentId() == -1) {
            // 如果有二级渠道, 不允许直接删除一级渠道
            SonyChannelCategory entity = new SonyChannelCategory();
			entity.setParentId(sonyChannelCategoryVO.getId());
            List<SonyChannelCategory> sonyChannelCategoryList = sonyChannelCategoryMapper.selectSonyChannelCategoryList(entity);
            if (!sonyChannelCategoryList.isEmpty()) {
                throw new ServiceException("该渠道有次级渠道， 不允许直接删除", HttpStatus.ERROR);
            }
        }
		sonyChannelCategoryMapper.deleteSonyChannelCategoryByChannelName(channelName);
	}
	
	@Override
	public List<SonyChannelCategoryVO> selectChannelListByChannelName(String channelName) {
		SonyChannelCategory sonyChannelCategory = new SonyChannelCategory();
		sonyChannelCategory.setChannelName(channelName);
		sonyChannelCategory.setParentId(-1);
		return sonyChannelCategoryMapper.selectChannelList(sonyChannelCategory);
	}
	
	@Override
	public List<SonyChannelCategoryIndexVO> selectChannelCategoryListByChannelCategoryDTO(SonyChannelCategoryDTO dto) {
		return  sonyChannelCategoryMapper.selectChannelCategoryListByChannelCategoryDTO(dto);
	}
}
