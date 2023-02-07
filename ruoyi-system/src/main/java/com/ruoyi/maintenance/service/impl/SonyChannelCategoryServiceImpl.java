package com.ruoyi.maintenance.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.maintenance.domain.SonyChannelCategory;
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
public class SonyChannelCategoryServiceImpl implements ISonyChannelCategoryService 
{
    @Autowired
    private SonyChannelCategoryMapper sonyChannelCategoryMapper;

    /**
     * 查询渠道关系
     * 
     * @param id 渠道关系主键
     * @return 渠道关系
     */
    @Override
    public SonyChannelCategory selectSonyChannelCategoryById(Long id)
    {
        return sonyChannelCategoryMapper.selectSonyChannelCategoryById(id);
    }

    /**
     * 查询渠道关系列表
     * 
     * @param sonyChannelCategory 渠道关系
     * @return 渠道关系
     */
    @Override
    public List<SonyChannelCategory> selectSonyChannelCategoryList(SonyChannelCategory sonyChannelCategory)
    {
        return sonyChannelCategoryMapper.selectSonyChannelCategoryList(sonyChannelCategory);
    }

    /**
     * 新增渠道关系
     * 
     * @param sonyChannelCategory 渠道关系
     * @return 结果
     */
    @Override
    public int insertSonyChannelCategory(SonyChannelCategory sonyChannelCategory)
    {
        Integer parentId = sonyChannelCategory.getParentId();
        // 如果parentId为空, 创建一级渠道
        if (parentId == null) {
            sonyChannelCategory.setParentId(-1);
        }
        sonyChannelCategory.setCreateBy(SecurityUtils.getUsername());
        sonyChannelCategory.setCreateTime(new Date());
        return sonyChannelCategoryMapper.insertSonyChannelCategory(sonyChannelCategory);
    }

    /**
     * 修改渠道关系
     * 
     * @param sonyChannelCategory 渠道关系
     * @return 结果
     */
    @Override
    public int updateSonyChannelCategory(SonyChannelCategory sonyChannelCategory)
    {
        return sonyChannelCategoryMapper.updateSonyChannelCategory(sonyChannelCategory);
    }

    /**
     * 批量删除渠道关系
     * 
     * @param ids 需要删除的渠道关系主键
     * @return 结果
     */
    @Override
    public int deleteSonyChannelCategoryByIds(Long[] ids)
    {
        return sonyChannelCategoryMapper.deleteSonyChannelCategoryByIds(ids);
    }

    /**
     * 删除渠道关系信息
     * 
     * @param id 渠道关系主键
     * @return 结果
     */
    @Override
    public int deleteSonyChannelCategoryById(Long id)
    {
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
}
