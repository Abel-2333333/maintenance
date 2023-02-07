package com.ruoyi.maintenance.service.impl;

import com.ruoyi.maintenance.domain.SonyRegion;
import com.ruoyi.maintenance.mapper.SonyRegionMapper;
import com.ruoyi.maintenance.service.ISonyRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 省市关系Service业务层处理
 * 
 * @author Abel
 * @date 2023-02-01
 */
@Service
public class SonyRegionServiceImpl implements ISonyRegionService 
{
    @Autowired
    private SonyRegionMapper sonyRegionMapper;

    /**
     * 查询省市关系
     * 
     * @param id 省市关系主键
     * @return 省市关系
     */
    @Override
    public SonyRegion selectSonyRegionById(Long id)
    {
        return sonyRegionMapper.selectSonyRegionById(id);
    }

    /**
     * 查询省市关系列表
     * 
     * @param sonyRegion 省市关系
     * @return 省市关系
     */
    @Override
    public List<SonyRegion> selectSonyRegionList(SonyRegion sonyRegion)
    {
        return sonyRegionMapper.selectSonyRegionList(sonyRegion);
    }

    /**
     * 新增省市关系
     * 
     * @param sonyRegion 省市关系
     * @return 结果
     */
    @Override
    public int insertSonyRegion(SonyRegion sonyRegion)
    {
        return sonyRegionMapper.insertSonyRegion(sonyRegion);
    }

    /**
     * 修改省市关系
     * 
     * @param sonyRegion 省市关系
     * @return 结果
     */
    @Override
    public int updateSonyRegion(SonyRegion sonyRegion)
    {
        return sonyRegionMapper.updateSonyRegion(sonyRegion);
    }

    /**
     * 批量删除省市关系
     * 
     * @param ids 需要删除的省市关系主键
     * @return 结果
     */
    @Override
    public int deleteSonyRegionByIds(Long[] ids)
    {
        return sonyRegionMapper.deleteSonyRegionByIds(ids);
    }

    /**
     * 删除省市关系信息
     * 
     * @param id 省市关系主键
     * @return 结果
     */
    @Override
    public int deleteSonyRegionById(Long id)
    {
        return sonyRegionMapper.deleteSonyRegionById(id);
    }

    @Override
    public List<SonyRegion> selectSonyRegionListByParentRegionId(String regionId) {
        SonyRegion sonyRegion = new SonyRegion();
        sonyRegion.setRegionParentId(regionId);
        return sonyRegionMapper.selectSonyRegionList(sonyRegion);
    }
}
