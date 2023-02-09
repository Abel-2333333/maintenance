package com.ruoyi.maintenance.service;

import com.ruoyi.maintenance.domain.SonyRegion;

import java.util.List;

/**
 * 省市关系Service接口
 * 
 * @author Abel
 * @date 2023-02-01
 */
public interface ISonyRegionService 
{
    void loadingRegionCache();
    /**
     * 查询省市关系
     * 
     * @param id 省市关系主键
     * @return 省市关系
     */
    public SonyRegion selectSonyRegionById(Long id);

    /**
     * 查询省市关系列表
     * 
     * @param sonyRegion 省市关系
     * @return 省市关系集合
     */
    public List<SonyRegion> selectSonyRegionList(SonyRegion sonyRegion);

    /**
     * 新增省市关系
     * 
     * @param sonyRegion 省市关系
     * @return 结果
     */
    public int insertSonyRegion(SonyRegion sonyRegion);

    /**
     * 修改省市关系
     * 
     * @param sonyRegion 省市关系
     * @return 结果
     */
    public int updateSonyRegion(SonyRegion sonyRegion);

    /**
     * 批量删除省市关系
     * 
     * @param ids 需要删除的省市关系主键集合
     * @return 结果
     */
    public int deleteSonyRegionByIds(Long[] ids);

    /**
     * 删除省市关系信息
     * 
     * @param id 省市关系主键
     * @return 结果
     */
    public int deleteSonyRegionById(Long id);

    List<SonyRegion> selectSonyRegionListByParentRegionId(String regionId);
}
