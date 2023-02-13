package com.ruoyi.maintenance.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.maintenance.domain.SonyChannelMsgReplyRule;
import com.ruoyi.maintenance.mapper.SonyChannelMsgReplyRuleMapper;
import com.ruoyi.maintenance.service.ISonyChannelMsgReplyRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公众号回复规则Service业务层处理
 * 
 * @author Abel
 * @date 2023-02-01
 */
@Service
public class SonyChannelMsgReplyRuleServiceImpl implements ISonyChannelMsgReplyRuleService 
{
    @Autowired
    private SonyChannelMsgReplyRuleMapper sonyChannelMsgReplyRuleMapper;

    /**
     * 查询公众号回复规则
     * 
     * @param ruleId 公众号回复规则主键
     * @return 公众号回复规则
     */
    @Override
    public SonyChannelMsgReplyRule selectSonyChannelMsgReplyRuleByRuleId(Long ruleId)
    {
        return sonyChannelMsgReplyRuleMapper.selectSonyChannelMsgReplyRuleByRuleId(ruleId);
    }

    /**
     * 查询公众号回复规则列表
     * 
     * @param sonyChannelMsgReplyRule 公众号回复规则
     * @return 公众号回复规则
     */
    @Override
    public List<SonyChannelMsgReplyRule> selectSonyChannelMsgReplyRuleList(SonyChannelMsgReplyRule sonyChannelMsgReplyRule)
    {
        return sonyChannelMsgReplyRuleMapper.selectSonyChannelMsgReplyRuleList(sonyChannelMsgReplyRule);
    }

    /**
     * 新增公众号回复规则
     * 
     * @param sonyChannelMsgReplyRule 公众号回复规则
     * @return 结果
     */
    @Override
    public int insertSonyChannelMsgReplyRule(SonyChannelMsgReplyRule sonyChannelMsgReplyRule)
    {
        sonyChannelMsgReplyRule.setCreateTime(DateUtils.getNowDate());
        return sonyChannelMsgReplyRuleMapper.insertSonyChannelMsgReplyRule(sonyChannelMsgReplyRule);
    }

    /**
     * 修改公众号回复规则
     * 
     * @param sonyChannelMsgReplyRule 公众号回复规则
     * @return 结果
     */
    @Override
    public int updateSonyChannelMsgReplyRule(SonyChannelMsgReplyRule sonyChannelMsgReplyRule)
    {
        sonyChannelMsgReplyRule.setUpdateTime(DateUtils.getNowDate());
        return sonyChannelMsgReplyRuleMapper.updateSonyChannelMsgReplyRule(sonyChannelMsgReplyRule);
    }

    /**
     * 批量删除公众号回复规则
     * 
     * @param ruleIds 需要删除的公众号回复规则主键
     * @return 结果
     */
    @Override
    public int deleteSonyChannelMsgReplyRuleByRuleIds(Long[] ruleIds)
    {
        return sonyChannelMsgReplyRuleMapper.deleteSonyChannelMsgReplyRuleByRuleIds(ruleIds);
    }

    /**
     * 删除公众号回复规则信息
     * 
     * @param ruleId 公众号回复规则主键
     * @return 结果
     */
    @Override
    public int deleteSonyChannelMsgReplyRuleByRuleId(Long ruleId)
    {
        return sonyChannelMsgReplyRuleMapper.deleteSonyChannelMsgReplyRuleByRuleId(ruleId);
    }
}
