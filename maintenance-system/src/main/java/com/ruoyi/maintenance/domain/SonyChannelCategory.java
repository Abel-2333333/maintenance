package com.ruoyi.maintenance.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

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
    private Integer id;

    /** 子级渠道关联的父级渠道id */
    @Excel(name = "子级渠道关联的父级渠道id")
    private Integer parentId;

    /** 渠道名 */
    @Excel(name = "渠道名")
    private String channelName;
    
    @Excel(name = "创建人")
    private String createdBy;
    
    @Excel(name = "创建时间")
    private Date createTime;

    @Excel(name = "创建时间")
    private Date deletedAt;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
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
    
    public String getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    
    @Override
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("parentId", getParentId())
            .append("channelName", getChannelName())
            .append("createdBy", getCreatedBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
