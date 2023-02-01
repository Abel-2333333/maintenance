package com.ruoyi.maintenance.service;

import com.ruoyi.maintenance.domain.SonyChannel;

import java.util.List;

/**
 * 渠道信息Service接口
 * 
 * @author Abel
 * @date 2023-02-01
 */
public interface ISonyChannelService 
{
    /**
     * 查询渠道信息
     * 
     * @param id 渠道信息主键
     * @return 渠道信息
     */
    public SonyChannel selectSonyChannelById(Long id);

    /**
     * 查询渠道信息列表
     * 
     * @param sonyChannel 渠道信息
     * @return 渠道信息集合
     */
    public List<SonyChannel> selectSonyChannelList(SonyChannel sonyChannel);

    /**
     * 新增渠道信息
     * 
     * @param sonyChannel 渠道信息
     * @return 结果
     */
    public int insertSonyChannel(SonyChannel sonyChannel);

    /**
     * 修改渠道信息
     * 
     * @param sonyChannel 渠道信息
     * @return 结果
     */
    public int updateSonyChannel(SonyChannel sonyChannel);

    /**
     * 批量删除渠道信息
     * 
     * @param ids 需要删除的渠道信息主键集合
     * @return 结果
     */
    public int deleteSonyChannelByIds(Long[] ids);

    /**
     * 删除渠道信息信息
     * 
     * @param id 渠道信息主键
     * @return 结果
     */
    public int deleteSonyChannelById(Long id);
}
