package com.ruoyi.maintenance.mapper;

import com.ruoyi.maintenance.domain.SonyChannelWechatUserStats;

import java.util.List;

/**
 * 用户数据统计Mapper接口
 * 
 * @author Abel
 * @date 2023-02-01
 */
public interface SonyChannelWechatUserStatsMapper 
{
    /**
     * 查询用户数据统计
     * 
     * @param id 用户数据统计主键
     * @return 用户数据统计
     */
    public SonyChannelWechatUserStats selectSonyChannelWechatUserStatsById(Long id);

    /**
     * 查询用户数据统计列表
     * 
     * @param sonyChannelWechatUserStats 用户数据统计
     * @return 用户数据统计集合
     */
    public List<SonyChannelWechatUserStats> selectSonyChannelWechatUserStatsList(SonyChannelWechatUserStats sonyChannelWechatUserStats);

    /**
     * 新增用户数据统计
     * 
     * @param sonyChannelWechatUserStats 用户数据统计
     * @return 结果
     */
    public int insertSonyChannelWechatUserStats(SonyChannelWechatUserStats sonyChannelWechatUserStats);

    /**
     * 修改用户数据统计
     * 
     * @param sonyChannelWechatUserStats 用户数据统计
     * @return 结果
     */
    public int updateSonyChannelWechatUserStats(SonyChannelWechatUserStats sonyChannelWechatUserStats);

    /**
     * 删除用户数据统计
     * 
     * @param id 用户数据统计主键
     * @return 结果
     */
    public int deleteSonyChannelWechatUserStatsById(Long id);

    /**
     * 批量删除用户数据统计
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSonyChannelWechatUserStatsByIds(Long[] ids);
}
