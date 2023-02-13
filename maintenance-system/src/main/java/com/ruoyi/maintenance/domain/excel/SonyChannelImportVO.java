package com.ruoyi.maintenance.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
public class SonyChannelImportVO {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Excel(name = "渠道编号", cellType = Excel.ColumnType.NUMERIC, prompt = "渠道编号")
    @ExcelProperty(value = "渠道编号")
    private Long id;

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

    @Excel(name = "微信生成的二维码跳转的url")
    @ExcelProperty(value = "微信生成的二维码跳转的url")
    private String qrcodeUrl;
    
    @Excel(name = "微信生成的二维码跳转的ticket")
    @ExcelProperty(value = "微信生成的二维码跳转的ticket")
    private String ticket;

    @Excel(name = "备注")
    @ExcelProperty(value = "备注")
    private String remark;

    @Excel(name = "创建时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    @Excel(name = "更新时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "更新时间")
    private Date updateTime;

    private String createdBy;
    private String updatedBy;
}
