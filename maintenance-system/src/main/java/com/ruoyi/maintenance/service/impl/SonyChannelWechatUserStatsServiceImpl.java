package com.ruoyi.maintenance.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.maintenance.domain.SonyChannelWechatUserStats;
import com.ruoyi.maintenance.mapper.SonyChannelWechatUserStatsMapper;
import com.ruoyi.maintenance.service.ISonyChannelWechatUserStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户数据统计Service业务层处理
 * 
 * @author Abel
 * @date 2023-02-01
 */
@Service
public class SonyChannelWechatUserStatsServiceImpl implements ISonyChannelWechatUserStatsService 
{
    @Autowired
    private SonyChannelWechatUserStatsMapper sonyChannelWechatUserStatsMapper;

    /**
     * 查询用户数据统计
     * 
     * @param id 用户数据统计主键
     * @return 用户数据统计
     */
    @Override
    public SonyChannelWechatUserStats selectSonyChannelWechatUserStatsById(Long id)
    {
        return sonyChannelWechatUserStatsMapper.selectSonyChannelWechatUserStatsById(id);
    }

    /**
     * 查询用户数据统计列表
     * 
     * @param sonyChannelWechatUserStats 用户数据统计
     * @return 用户数据统计
     */
    @Override
    public List<SonyChannelWechatUserStats> selectSonyChannelWechatUserStatsList(SonyChannelWechatUserStats sonyChannelWechatUserStats)
    {
        return sonyChannelWechatUserStatsMapper.selectSonyChannelWechatUserStatsList(sonyChannelWechatUserStats);
    }

    /**
     * 新增用户数据统计
     * 
     * @param sonyChannelWechatUserStats 用户数据统计
     * @return 结果
     */
    @Override
    public int insertSonyChannelWechatUserStats(SonyChannelWechatUserStats sonyChannelWechatUserStats)
    {
        return sonyChannelWechatUserStatsMapper.insertSonyChannelWechatUserStats(sonyChannelWechatUserStats);
    }

    /**
     * 修改用户数据统计
     * 
     * @param sonyChannelWechatUserStats 用户数据统计
     * @return 结果
     */
    @Override
    public int updateSonyChannelWechatUserStats(SonyChannelWechatUserStats sonyChannelWechatUserStats)
    {
        sonyChannelWechatUserStats.setUpdateTime(DateUtils.getNowDate());
        return sonyChannelWechatUserStatsMapper.updateSonyChannelWechatUserStats(sonyChannelWechatUserStats);
    }

    /**
     * 批量删除用户数据统计
     * 
     * @param ids 需要删除的用户数据统计主键
     * @return 结果
     */
    @Override
    public int deleteSonyChannelWechatUserStatsByIds(Long[] ids)
    {
        return sonyChannelWechatUserStatsMapper.deleteSonyChannelWechatUserStatsByIds(ids);
    }

    /**
     * 删除用户数据统计信息
     * 
     * @param id 用户数据统计主键
     * @return 结果
     */
    @Override
    public int deleteSonyChannelWechatUserStatsById(Long id)
    {
        return sonyChannelWechatUserStatsMapper.deleteSonyChannelWechatUserStatsById(id);
    }
}
