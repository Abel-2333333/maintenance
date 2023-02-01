package com.ruoyi.maintenance.service.impl;

import com.ruoyi.maintenance.domain.SonyChannelMsg;
import com.ruoyi.maintenance.mapper.SonyChannelMsgMapper;
import com.ruoyi.maintenance.service.ISonyChannelMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自动回复消息Service业务层处理
 * 
 * @author Abel
 * @date 2023-02-01
 */
@Service
public class SonyChannelMsgServiceImpl implements ISonyChannelMsgService 
{
    @Autowired
    private SonyChannelMsgMapper sonyChannelMsgMapper;

    /**
     * 查询自动回复消息
     * 
     * @param id 自动回复消息主键
     * @return 自动回复消息
     */
    @Override
    public SonyChannelMsg selectSonyChannelMsgById(Long id)
    {
        return sonyChannelMsgMapper.selectSonyChannelMsgById(id);
    }

    /**
     * 查询自动回复消息列表
     * 
     * @param sonyChannelMsg 自动回复消息
     * @return 自动回复消息
     */
    @Override
    public List<SonyChannelMsg> selectSonyChannelMsgList(SonyChannelMsg sonyChannelMsg)
    {
        return sonyChannelMsgMapper.selectSonyChannelMsgList(sonyChannelMsg);
    }

    /**
     * 新增自动回复消息
     * 
     * @param sonyChannelMsg 自动回复消息
     * @return 结果
     */
    @Override
    public int insertSonyChannelMsg(SonyChannelMsg sonyChannelMsg)
    {
        return sonyChannelMsgMapper.insertSonyChannelMsg(sonyChannelMsg);
    }

    /**
     * 修改自动回复消息
     * 
     * @param sonyChannelMsg 自动回复消息
     * @return 结果
     */
    @Override
    public int updateSonyChannelMsg(SonyChannelMsg sonyChannelMsg)
    {
        return sonyChannelMsgMapper.updateSonyChannelMsg(sonyChannelMsg);
    }

    /**
     * 批量删除自动回复消息
     * 
     * @param ids 需要删除的自动回复消息主键
     * @return 结果
     */
    @Override
    public int deleteSonyChannelMsgByIds(Long[] ids)
    {
        return sonyChannelMsgMapper.deleteSonyChannelMsgByIds(ids);
    }

    /**
     * 删除自动回复消息信息
     * 
     * @param id 自动回复消息主键
     * @return 结果
     */
    @Override
    public int deleteSonyChannelMsgById(Long id)
    {
        return sonyChannelMsgMapper.deleteSonyChannelMsgById(id);
    }
}
