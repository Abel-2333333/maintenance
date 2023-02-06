package com.ruoyi.maintenance.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 渠道信息对象 sony_channel
 *
 * @author Abel
 * @date 2023-02-01
 */
public class SonyChannel extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Excel(name = "渠道编号", cellType = Excel.ColumnType.NUMERIC, prompt = "渠道编号")
    @ExcelProperty(value = "渠道编号")
    private long id;

    /**
     * 一级渠道
     */
    @Excel(name = "一级渠道")
//    @NotNull(message = "一级渠道不能为空")
    @ExcelProperty(value = "一级渠道")
    private String primaryChannel;

    /**
     * 二级渠道
     */
    @Excel(name = "二级渠道")
    @ExcelProperty(value = "二级渠道")
    private String secondaryChannel;

    /**
     * 渠道代码
     */
    @Excel(name = "渠道代码")
    @ExcelProperty(value = "渠道代码")
    private String channelCode;

    /**
     * 省
     */
    @Excel(name = "省")
    @ExcelProperty(value = "省")
    private String province;

    /**
     * 市
     */
    @Excel(name = "市")
    @ExcelProperty(value = "市")
    private String city;

    /**
     * 维修站名称
     */
    @Excel(name = "维修站名称")
    @ExcelProperty(value = "维修站名称")
    private String maintenanceStationName;

    /**
     * 维修站代码
     */
    @Excel(name = "维修站代码")
    @ExcelProperty(value = "维修站代码")
    private String maintenanceStationCode;

    /**
     * 门店名称
     */
    @Excel(name = "门店名称")
    @ExcelProperty(value = "门店名称")
    private String storeName;

    /**
     * 门店代码
     */
    @Excel(name = "门店代码")
    @ExcelProperty(value = "门店代码")
    private String storeCode;

    /**
     * 微信生成的二维码跳转的url
     */

    private String qrcodeUrl;


    /**
     * 逻辑删除状态  0.未删除 1.已删除
     */

    private Integer delFlag;


    @Excel(name = "备注")
    @ExcelProperty(value = "备注")
    private String remark;

    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    private String createdBy;


    private String updatedBy;

    public void setId(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setPrimaryChannel(String primaryChannel) {
        this.primaryChannel = primaryChannel;
    }

    public String getPrimaryChannel() {
        return primaryChannel;
    }

    public void setSecondaryChannel(String secondaryChannel) {
        this.secondaryChannel = secondaryChannel;
    }

    public String getSecondaryChannel() {
        return secondaryChannel;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setMaintenanceStationName(String maintenanceStationName) {
        this.maintenanceStationName = maintenanceStationName;
    }

    public String getMaintenanceStationName() {
        return maintenanceStationName;
    }

    public void setMaintenanceStationCode(String maintenanceStationCode) {
        this.maintenanceStationCode = maintenanceStationCode;
    }

    public String getMaintenanceStationCode() {
        return maintenanceStationCode;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("primaryChannel", getPrimaryChannel())
                .append("secondaryChannel", getSecondaryChannel())
                .append("channelCode", getChannelCode())
                .append("province", getProvince())
                .append("city", getCity())
                .append("maintenanceStationName", getMaintenanceStationName())
                .append("maintenanceStationCode", getMaintenanceStationCode())
                .append("storeName", getStoreName())
                .append("storeCode", getStoreCode())
                .append("delFlag", getDelFlag())
                .append("remark", getRemark())
                .append("qrcodeUrl", getQrcodeUrl())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("createdBy", getCreatedBy())
                .append("updatedBy", getUpdatedBy())
                .toString();
    }
}
