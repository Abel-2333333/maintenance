package com.ruoyi.maintenance.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 查询返回列表VO
 * @author Abel
 * @since 2/8/2023 10:19 AM
 */
public class SonyChannelCategoryIndexVO implements Serializable {
	private static final long serialVersionUID = -2953154140020551697L;
	
	/** 主键. 关联渠道信息表channel_id */
	private Integer id;
	
	/** 子级渠道关联的父级渠道id */
	@Excel(name = "子级渠道关联的父级渠道id")
	private Integer parentId;
	
	/** 一级渠道名 */
	@Excel(name = "一级渠道名")
	private String primaryName;
	
	/** 二级渠道名 */
	@Excel(name = "二级渠道名")
	private String secondaryName;
	
	@Excel(name = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Map<String, Object> params;
	
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
	
	public String getPrimaryName() {
		return primaryName;
	}
	
	public void setPrimaryName(String primaryName) {
		this.primaryName = primaryName;
	}
	
	public String getSecondaryName() {
		return secondaryName;
	}
	
	public void setSecondaryName(String secondaryName) {
		this.secondaryName = secondaryName;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Map<String, Object> getParams()
	{
		if (params == null)
		{
			params = new HashMap<>();
		}
		return params;
	}
	
	public void setParams(Map<String, Object> params)
	{
		this.params = params;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", getId())
				.append("parentId", getParentId())
				.append("primaryName", getPrimaryName())
				.append("secondaryName", getSecondaryName())
				.toString();
	}
}
