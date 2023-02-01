package com.ruoyi.maintenance.mapper;

import com.ruoyi.maintenance.domain.SonyChannelMsgReplyRule;

import java.util.List;

/**
 * 公众号回复规则Mapper接口
 * 
 * @author Abel
 * @date 2023-02-01
 */
public interface SonyChannelMsgReplyRuleMapper 
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
     * 删除公众号回复规则
     * 
     * @param ruleId 公众号回复规则主键
     * @return 结果
     */
    public int deleteSonyChannelMsgReplyRuleByRuleId(Long ruleId);

    /**
     * 批量删除公众号回复规则
     * 
     * @param ruleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSonyChannelMsgReplyRuleByRuleIds(Long[] ruleIds);
}
