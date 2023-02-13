package com.ruoyi.maintenance.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客户渠道信息对象 sony_channel_consumer_info
 * 
 * @author Abel
 * @date 2023-02-01
 */
public class SonyChannelConsumerInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 微信用户标识id */
    @Excel(name = "微信用户标识id")
    private String openId;

    /** 微信用户unionId */
    @Excel(name = "微信用户unionId")
    private String unionId;

    /** 索尼关联用户id */
    @Excel(name = "索尼关联用户id")
    private String bpId;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickname;

    /** 姓名 */
    @Excel(name = "姓名")
    private String realName;

    /** 性别 */
    @Excel(name = "性别")
    private Long gender;

    /** 省 */
    @Excel(name = "省")
    private String province;

    /** 城市 */
    @Excel(name = "城市")
    private String city;

    /** 是否关注. 默认是 */
    @Excel(name = "是否关注. 默认是")
    private Integer subscribeStatus;

    /** 二维码关联的场景值 */
    @Excel(name = "二维码关联的场景值")
    private String qrSceneStr;

    /** 最近一次扫码进入公众号时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最近一次扫码进入公众号时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date subscribeTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOpenId(String openId) 
    {
        this.openId = openId;
    }

    public String getOpenId() 
    {
        return openId;
    }
    public void setUnionId(String unionId) 
    {
        this.unionId = unionId;
    }

    public String getUnionId() 
    {
        return unionId;
    }
    public void setBpId(String bpId) 
    {
        this.bpId = bpId;
    }

    public String getBpId() 
    {
        return bpId;
    }
    public void setNickname(String nickname) 
    {
        this.nickname = nickname;
    }

    public String getNickname() 
    {
        return nickname;
    }
    public void setRealName(String realName) 
    {
        this.realName = realName;
    }

    public String getRealName() 
    {
        return realName;
    }
    public void setGender(Long gender) 
    {
        this.gender = gender;
    }

    public Long getGender() 
    {
        return gender;
    }
    public void setProvince(String province) 
    {
        this.province = province;
    }

    public String getProvince() 
    {
        return province;
    }
    public void setCity(String city) 
    {
        this.city = city;
    }

    public String getCity() 
    {
        return city;
    }
    public void setSubscribeStatus(Integer subscribeStatus) 
    {
        this.subscribeStatus = subscribeStatus;
    }

    public Integer getSubscribeStatus() 
    {
        return subscribeStatus;
    }
    public void setQrSceneStr(String qrSceneStr) 
    {
        this.qrSceneStr = qrSceneStr;
    }

    public String getQrSceneStr() 
    {
        return qrSceneStr;
    }
    public void setSubscribeTime(Date subscribeTime) 
    {
        this.subscribeTime = subscribeTime;
    }

    public Date getSubscribeTime() 
    {
        return subscribeTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("openId", getOpenId())
            .append("unionId", getUnionId())
            .append("bpId", getBpId())
            .append("nickname", getNickname())
            .append("realName", getRealName())
            .append("gender", getGender())
            .append("province", getProvince())
            .append("city", getCity())
            .append("subscribeStatus", getSubscribeStatus())
            .append("qrSceneStr", getQrSceneStr())
            .append("createTime", getCreateTime())
            .append("subscribeTime", getSubscribeTime())
            .toString();
    }
}
