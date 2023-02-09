package com.ruoyi.maintenance.domain.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户渠道信息对象 sony_channel_consumer_info
 * 
 * @author Abel
 * @date 2023-02-01
 */
@Data
public class SonyChannelConsumerInfoExportVO implements Serializable
{
    private static final long serialVersionUID = 1L;
    

    /** 微信用户unionId */
    @Excel(name = "微信用户unionId")
    @ExcelProperty("微信用户unionId")
    private String unionId;

    /** 昵称 */
    @Excel(name = "昵称")
    @ExcelProperty("昵称")
    private String nickname;

    /** 姓名 */
    @Excel(name = "姓名")
    @ExcelProperty( "姓名")
    private String realName;

    /** 性别 */
    @Excel(name = "性别")
    @ExcelProperty("性别")
    private Long gender;

    /** 省 */
    @Excel(name = "省")
    @ExcelProperty( "省")
    private String province;

    /** 城市 */
    @Excel(name = "城市")
    @ExcelProperty( "城市")
    private String city;
    
    /** 一级渠道 */
    @Excel(name = "一级渠道")
    @ExcelProperty("一级渠道")
    private String primaryName;
    
    /** 二级渠道 */
    @Excel(name = "二级渠道")
    @ExcelProperty("二级渠道")
    private String secondaryName;
    
    /** 维修站名称 */
    @Excel(name = "维修站名称")
    @ExcelProperty("维修站名称")
    private String maintenanceStationName;

    /** 最近一次扫码进入公众号时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最近一次扫码进入公众号时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty("最近一次扫码进入公众号时间")
    private Date subscribeTime;
}
