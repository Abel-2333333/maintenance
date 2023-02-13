package com.ruoyi.maintenance.service;

import com.ruoyi.maintenance.domain.SonyChannelMsgReplyRule;

import java.util.List;

/**
 * 公众号回复规则Service接口
 * 
 * @author Abel
 * @date 2023-02-01
 */
public interface ISonyChannelMsgReplyRuleService 
{
    /**
     * 查询公众号回复规则
     * 
     * @param ruleId 公众号回复规则主键
     * @return 公众号回复规则
     */
    public SonyChannelMsgReplyRule selectSonyChannelMsgReplyRuleByRuleId(Long ruleId);

    /**
     * 查询公众号回复规则列表
     * 
     * @param sonyChannelMsgReplyRule 公众号回复规则
     * @return 公众号回复规则集合
     */
    public List<SonyChannelMsgReplyRule> selectSonyChannelMsgReplyRuleList(SonyChannelMsgReplyRule sonyChannelMsgReplyRule);

    /**
     * 新增公众号回复规则
     * 
     * @param sonyChannelMsgReplyRule 公众号回复规则
     * @return 结果
     */
    public int insertSonyChannelMsgReplyRule(SonyChannelMsgReplyRule sonyChannelMsgReplyRule);

    /**
     * 修改公众号回复规则
     * 
     * @param sonyChannelMsgReplyRule 公众号回复规则
     * @return 结果
     */
    public int updateSonyChannelMsgReplyRule(SonyChannelMsgReplyRule sonyChannelMsgReplyRule);

    /**
     * 批量删除公众号回复规则
     * 
     * @param ruleIds 需要删除的公众号回复规则主键集合
     * @return 结果
     */
    public int deleteSonyChannelMsgReplyRuleByRuleIds(Long[] ruleIds);

    /**
     * 删除公众号回复规则信息
     * 
     * @param ruleId 公众号回复规则主键
     * @return 结果
     */
    public int deleteSonyChannelMsgReplyRuleByRuleId(Long ruleId);
}
