package com.ruoyi.maintenance.service;

import com.ruoyi.maintenance.domain.SonyChannelCategory;
import com.ruoyi.maintenance.domain.vo.SonyChannelCategoryVO;

import java.util.List;

/**
 * 渠道关系Service接口
 * 
 * @author Abel
 * @date 2023-02-07
 */
public interface ISonyChannelCategoryService 
{
    /**
     * 查询渠道关系
     * 
     * @param id 渠道关系主键
     * @return 渠道关系
     */
    public SonyChannelCategory selectSonyChannelCategoryById(Long id);

    /**
     * 查询渠道关系列表
     * 
     * @param sonyChannelCategory 渠道关系
     * @return 渠道关系集合
     */
    public List<SonyChannelCategory> selectSonyChannelCategoryList(SonyChannelCategory sonyChannelCategory);

    /**
     * 新增渠道关系
     * 
     * @param sonyChannelCategory 渠道关系
     * @return 结果
     */
    public int insertSonyChannelCategory(SonyChannelCategory sonyChannelCategory);

    /**
     * 修改渠道关系
     * 
     * @param sonyChannelCategory 渠道关系
     * @return 结果
     */
    public int updateSonyChannelCategory(SonyChannelCategory sonyChannelCategory);

    /**
     * 批量删除渠道关系
     * 
     * @param ids 需要删除的渠道关系主键集合
     * @return 结果
     */
    public int deleteSonyChannelCategoryByIds(Long[] ids);

    /**
     * 删除渠道关系信息
     * 
     * @param id 渠道关系主键
     * @return 结果
     */
    public int deleteSonyChannelCategoryById(Long id);

    List<SonyChannelCategoryVO> selectChannelList(SonyChannelCategory sonyChannelCategory);

    List<SonyChannelCategoryVO> selectSecondaryChannelList(SonyChannelCategory sonyChannelCategory);
}