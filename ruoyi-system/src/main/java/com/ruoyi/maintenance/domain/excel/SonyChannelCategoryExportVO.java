package com.ruoyi.maintenance.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

import static com.alibaba.excel.enums.poi.VerticalAlignmentEnum.CENTER;

/**
 * 查询返回列表VO
 * @author Abel
 * @since 2/8/2023 10:19 AM
 */
@ContentRowHeight(30)
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER, verticalAlignment = CENTER)
public class SonyChannelCategoryExportVO implements Serializable {
	private static final long serialVersionUID = -2953154140020551697L;
	
	/** 主键. 关联渠道信息表channel_id */
	@ExcelProperty("二级渠道ID")
	private Integer id;
	
	/** 一级渠道名 */
	@Excel(name = "一级渠道名")
	@ExcelProperty(value = "一级渠道名")
	private String primaryName;
	
	/** 二级渠道名 */
	@Excel(name = "二级渠道名")
	@ExcelProperty(value = "二级渠道名")
	private String secondaryName;
	
	@Excel(name = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelProperty(value = "创建时间")
	private Date createTime;
	
	public void setId(Integer id)
	{
		this.id = id;
	}
	
	public Integer getId()
	{
		return id;
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
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", getId())
				.append("primaryName", getPrimaryName())
				.append("secondaryName", getSecondaryName())
				.toString();
	}
}
