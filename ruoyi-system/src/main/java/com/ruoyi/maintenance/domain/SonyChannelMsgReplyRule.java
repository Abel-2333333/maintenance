package com.ruoyi.maintenance.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 公众号回复规则对象 sony_channel_msg_reply_rule
 * 
 * @author Abel
 * @date 2023-02-01
 */
public class SonyChannelMsgReplyRule extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 回复规则id. 关键词回复用于关联多条消息 */
    private Long ruleId;

    /** 规则名称 */
    @Excel(name = "规则名称")
    private String ruleName;

    /** 自动回复类型 1.关键词回复 2.被关注回复 3.收到消息回复 */
    @Excel(name = "自动回复类型 1.关键词回复 2.被关注回复 3.收到消息回复")
    private Long replyType;

    /** 回复数量 1. 随即回复一条 2. 回复全部 */
    @Excel(name = "回复数量 1. 随即回复一条 2. 回复全部")
    private Long replyNum;

    /** 是否关联渠道码 0. 否 1. 是 默认否 */
    @Excel(name = "是否关联渠道码 0. 否 1. 是 默认否")
    private Integer matchQrcode;

    /** 渠道代码 */
    @Excel(name = "渠道代码")
    private String channelCode;

    /**  */
    @Excel(name = "")
    private String createdBy;

    /**  */
    @Excel(name = "")
    private String updatedBy;

    public void setRuleId(Long ruleId) 
    {
        this.ruleId = ruleId;
    }

    public Long getRuleId() 
    {
        return ruleId;
    }
    public void setRuleName(String ruleName) 
    {
        this.ruleName = ruleName;
    }

    public String getRuleName() 
    {
        return ruleName;
    }
    public void setReplyType(Long replyType) 
    {
        this.replyType = replyType;
    }

    public Long getReplyType() 
    {
        return replyType;
    }
    public void setReplyNum(Long replyNum) 
    {
        this.replyNum = replyNum;
    }

    public Long getReplyNum() 
    {
        return replyNum;
    }
    public void setMatchQrcode(Integer matchQrcode) 
    {
        this.matchQrcode = matchQrcode;
    }

    public Integer getMatchQrcode() 
    {
        return matchQrcode;
    }
    public void setChannelCode(String channelCode) 
    {
        this.channelCode = channelCode;
    }

    public String getChannelCode() 
    {
        return channelCode;
    }
    public void setCreatedBy(String createdBy) 
    {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() 
    {
        return createdBy;
    }
    public void setUpdatedBy(String updatedBy) 
    {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy() 
    {
        return updatedBy;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("ruleId", getRuleId())
            .append("ruleName", getRuleName())
            .append("replyType", getReplyType())
            .append("replyNum", getReplyNum())
            .append("matchQrcode", getMatchQrcode())
            .append("channelCode", getChannelCode())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createdBy", getCreatedBy())
            .append("updatedBy", getUpdatedBy())
            .toString();
    }
}
