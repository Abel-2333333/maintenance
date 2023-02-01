package com.ruoyi.maintenance.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 微信用户基本信息对象 sony_wechat_user
 * 
 * @author Abel
 * @date 2023-02-01
 */
public class SonyWechatUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 微信用户标识openId */
    @Excel(name = "微信用户标识openId")
    private String openId;

    /** 区分微信用户的唯一性 */
    @Excel(name = "区分微信用户的唯一性")
    private String unionId;

    /** 索尼关联用户id */
    @Excel(name = "索尼关联用户id")
    private String bpId;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickname;

    /** 性别 0.男 1. 女 默认男 */
    @Excel(name = "性别 0.男 1. 女 默认男")
    private Integer gender;

    /** 用户的语言，简体中文为zh_CN */
    @Excel(name = "用户的语言，简体中文为zh_CN")
    private String language;

    /** 国家 */
    @Excel(name = "国家")
    private String country;

    /** 省 */
    @Excel(name = "省")
    private String province;

    /** 市 */
    @Excel(name = "市")
    private String city;

    /** 头像url */
    @Excel(name = "头像url")
    private String avatarUrl;

    /** 关注状态 0. 未关注 1.已关注 默认已关注 */
    @Excel(name = "关注状态 0. 未关注 1.已关注 默认已关注")
    private Integer subscribeStatus;

    /** 用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENE_PROFILE_LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_WECHAT_ADVERTISEMENT 微信广告，ADD_SCENE_REPRINT 他人转载 ,ADD_SCENE_LIVESTREAM 视频号直播，ADD_SCENE_CHANNELS 视频号 , ADD_SCENE_OTHERS 其他 */
    @Excel(name = "用户关注的渠道来源，ADD_SCENE_SEARCH 公众号搜索，ADD_SCENE_ACCOUNT_MIGRATION 公众号迁移，ADD_SCENE_PROFILE_CARD 名片分享，ADD_SCENE_QR_CODE 扫描二维码，ADD_SCENE_PROFILE_LINK 图文页内名称点击，ADD_SCENE_PROFILE_ITEM 图文页右上角菜单，ADD_SCENE_PAID 支付后关注，ADD_SCENE_WECHAT_ADVERTISEMENT 微信广告，ADD_SCENE_REPRINT 他人转载 ,ADD_SCENE_LIVESTREAM 视频号直播，ADD_SCENE_CHANNELS 视频号 , ADD_SCENE_OTHERS 其他")
    private String subscribeScene;

    /** 关注时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "关注时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date subscribeTime;

    /** 用户所在的分组ID（兼容旧的用户分组接口） */
    @Excel(name = "用户所在的分组ID", readConverterExp = "兼=容旧的用户分组接口")
    private Long groupId;

    /** 二维码扫码场景 */
    @Excel(name = "二维码扫码场景")
    private Long qrScene;

    /** 二维码扫码场景描述 */
    @Excel(name = "二维码扫码场景描述")
    private String qrSceneStr;

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
    public void setGender(Integer gender) 
    {
        this.gender = gender;
    }

    public Integer getGender() 
    {
        return gender;
    }
    public void setLanguage(String language) 
    {
        this.language = language;
    }

    public String getLanguage() 
    {
        return language;
    }
    public void setCountry(String country) 
    {
        this.country = country;
    }

    public String getCountry() 
    {
        return country;
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
    public void setAvatarUrl(String avatarUrl) 
    {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() 
    {
        return avatarUrl;
    }
    public void setSubscribeStatus(Integer subscribeStatus) 
    {
        this.subscribeStatus = subscribeStatus;
    }

    public Integer getSubscribeStatus() 
    {
        return subscribeStatus;
    }
    public void setSubscribeScene(String subscribeScene) 
    {
        this.subscribeScene = subscribeScene;
    }

    public String getSubscribeScene() 
    {
        return subscribeScene;
    }
    public void setSubscribeTime(Date subscribeTime) 
    {
        this.subscribeTime = subscribeTime;
    }

    public Date getSubscribeTime() 
    {
        return subscribeTime;
    }
    public void setGroupId(Long groupId) 
    {
        this.groupId = groupId;
    }

    public Long getGroupId() 
    {
        return groupId;
    }
    public void setQrScene(Long qrScene) 
    {
        this.qrScene = qrScene;
    }

    public Long getQrScene() 
    {
        return qrScene;
    }
    public void setQrSceneStr(String qrSceneStr) 
    {
        this.qrSceneStr = qrSceneStr;
    }

    public String getQrSceneStr() 
    {
        return qrSceneStr;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("openId", getOpenId())
            .append("unionId", getUnionId())
            .append("bpId", getBpId())
            .append("nickname", getNickname())
            .append("gender", getGender())
            .append("language", getLanguage())
            .append("country", getCountry())
            .append("province", getProvince())
            .append("city", getCity())
            .append("avatarUrl", getAvatarUrl())
            .append("subscribeStatus", getSubscribeStatus())
            .append("subscribeScene", getSubscribeScene())
            .append("subscribeTime", getSubscribeTime())
            .append("remark", getRemark())
            .append("groupId", getGroupId())
            .append("qrScene", getQrScene())
            .append("qrSceneStr", getQrSceneStr())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
