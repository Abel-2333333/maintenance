package com.ruoyi.maintenance.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 用户数据统计对象 sony_channel_wechat_user_stats
 * 
 * @author Abel
 * @date 2023-02-01
 */
public class SonyChannelWechatUserStats extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 来源 */
    @Excel(name = "来源")
    private String userSource;

    /** 新增关注 */
    @Excel(name = "新增关注")
    private Long newUser;

    /** 取消关注用户数 */
    @Excel(name = "取消关注用户数")
    private Long unsubscribedUser;

    /** 净增关注 */
    @Excel(name = "净增关注")
    private Long netNewUser;

    /** 截止到这条记录的时间累计关注数 */
    @Excel(name = "截止到这条记录的时间累计关注数")
    private Long totalUser;

    /** 数据统计日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "数据统计日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date statsDate;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserSource(String userSource) 
    {
        this.userSource = userSource;
    }

    public String getUserSource() 
    {
        return userSource;
    }
    public void setNewUser(Long newUser) 
    {
        this.newUser = newUser;
    }

    public Long getNewUser() 
    {
        return newUser;
    }
    public void setUnsubscribedUser(Long unsubscribedUser) 
    {
        this.unsubscribedUser = unsubscribedUser;
    }

    public Long getUnsubscribedUser() 
    {
        return unsubscribedUser;
    }
    public void setNetNewUser(Long netNewUser) 
    {
        this.netNewUser = netNewUser;
    }

    public Long getNetNewUser() 
    {
        return netNewUser;
    }
    public void setTotalUser(Long totalUser) 
    {
        this.totalUser = totalUser;
    }

    public Long getTotalUser() 
    {
        return totalUser;
    }
    public void setStatsDate(Date statsDate) 
    {
        this.statsDate = statsDate;
    }

    public Date getStatsDate() 
    {
        return statsDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userSource", getUserSource())
            .append("newUser", getNewUser())
            .append("unsubscribedUser", getUnsubscribedUser())
            .append("netNewUser", getNetNewUser())
            .append("totalUser", getTotalUser())
            .append("statsDate", getStatsDate())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
