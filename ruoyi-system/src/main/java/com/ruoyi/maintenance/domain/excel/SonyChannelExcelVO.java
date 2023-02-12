package com.ruoyi.maintenance.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.ContentStyle;
import com.alibaba.excel.converters.string.StringImageConverter;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.maintenance.domain.base.SonyChannelBaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

import static com.alibaba.excel.enums.poi.VerticalAlignmentEnum.CENTER;

@ContentRowHeight(100)
@ContentStyle(horizontalAlignment = HorizontalAlignmentEnum.CENTER, verticalAlignment = CENTER)
public class SonyChannelExcelVO extends SonyChannelBaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Excel(name = "渠道编号", cellType = Excel.ColumnType.NUMERIC, prompt = "渠道编号")
    @ExcelProperty(value = "渠道编号", order = 1)
    private Long id;

    /**
     * 一级渠道
     */
    @Excel(name = "一级渠道")
//    @NotNull(message = "一级渠道不能为空")
    @ExcelProperty(value = "一级渠道", order = 2)
    private String primaryChannel;

    /**
     * 二级渠道
     */
    @Excel(name = "二级渠道")
    @ExcelProperty(value = "二级渠道", order = 3)
    private String secondaryChannel;

    /**
     * 渠道代码
     */
    @Excel(name = "渠道代码")
    @ExcelProperty(value = "渠道代码", order = 4)
    private String channelCode;

    /**
     * 带Logo渠道码
     */
    @ExcelProperty(value = "渠道二维码(带Logo)", converter = StringImageConverter.class, order = 5)
    @ColumnWidth(75/4)
    private String qrCodeWithLogo;

    /**
     * 不带Logo渠道码
     */
    @ExcelProperty(value = "渠道二维码(不带Logo)", converter = StringImageConverter.class, order = 6)
    @ColumnWidth(75/4)
    private String qrCode;

    /**
     * 省
     */
    @Excel(name = "省")
    @ExcelProperty(value = "省", order = 7)
    private String province;

    /**
     * 市
     */
    @Excel(name = "市")
    @ExcelProperty(value = "市", order = 8)
    private String city;

    /**
     * 维修站名称
     */
    @Excel(name = "维修站名称")
    @ExcelProperty(value = "维修站名称", order = 9)
    private String maintenanceStationName;

    /**
     * 维修站代码
     */
    @Excel(name = "维修站代码")
    @ExcelProperty(value = "维修站代码", order = 10)
    private String maintenanceStationCode;

    /**
     * 门店名称
     */
    @Excel(name = "门店名称")
    @ExcelProperty(value = "门店名称",order = 11)
    private String storeName;

    /**
     * 门店代码
     */
    @Excel(name = "门店代码")
    @ExcelProperty(value = "门店代码", order = 12)
    private String storeCode;

    @Excel(name = "备注")
    @ExcelProperty(value = "备注", order = 13)
    private String remark;

    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间", order = 14)
    @ColumnWidth(25)
    private Date createTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
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

    public String getQrCodeWithLogo() {
        return qrCodeWithLogo;
    }

    public void setQrCodeWithLogo(String qrCodeWithLogo) {
        this.qrCodeWithLogo = qrCodeWithLogo;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
                .append("remark", getRemark())
                .append("createTime", getCreateTime())
                .toString();
    }
}
