package com.ruoyi.maintenance.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 渠道关系对象 sony_channel_category
 * 
 * @author Abel
 * @date 2023-02-07
 */
public class SonyChannelCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键. 关联渠道信息表channel_id */
    private Long id;

    /** 子级渠道关联的父级渠道id */
    @Excel(name = "子级渠道关联的父级渠道id")
    private Integer parentId;

    /** 渠道名 */
    @Excel(name = "渠道名")
    private String channelName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setParentId(Integer parentId)
    {
        this.parentId = parentId;
    }

    public Integer getParentId()
    {
        return parentId;
    }
    public void setChannelName(String channelName) 
    {
        this.channelName = channelName;
    }

    public String getChannelName() 
    {
        return channelName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("parentId", getParentId())
            .append("channelName", getChannelName())
            .toString();
    }
}
