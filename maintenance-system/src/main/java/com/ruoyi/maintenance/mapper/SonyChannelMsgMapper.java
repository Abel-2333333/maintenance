package com.ruoyi.maintenance.mapper;

import com.ruoyi.maintenance.domain.SonyChannelMsg;

import java.util.List;

/**
 * 自动回复消息Mapper接口
 * 
 * @author Abel
 * @date 2023-02-01
 */
public interface SonyChannelMsgMapper 
{
    /**
     * 查询自动回复消息
     * 
     * @param id 自动回复消息主键
     * @return 自动回复消息
     */
    public SonyChannelMsg selectSonyChannelMsgById(Long id);

    /**
     * 查询自动回复消息列表
     * 
     * @param sonyChannelMsg 自动回复消息
     * @return 自动回复消息集合
     */
    public List<SonyChannelMsg> selectSonyChannelMsgList(SonyChannelMsg sonyChannelMsg);

    /**
     * 新增自动回复消息
     * 
     * @param sonyChannelMsg 自动回复消息
     * @return 结果
     */
    public int insertSonyChannelMsg(SonyChannelMsg sonyChannelMsg);

    /**
     * 修改自动回复消息
     * 
     * @param sonyChannelMsg 自动回复消息
     * @return 结果
     */
    public int updateSonyChannelMsg(SonyChannelMsg sonyChannelMsg);

    /**
     * 删除自动回复消息
     * 
     * @param id 自动回复消息主键
     * @return 结果
     */
    public int deleteSonyChannelMsgById(Long id);

    /**
     * 批量删除自动回复消息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSonyChannelMsgByIds(Long[] ids);
}
