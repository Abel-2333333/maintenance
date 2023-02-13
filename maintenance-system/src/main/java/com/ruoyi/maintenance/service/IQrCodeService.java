package com.ruoyi.maintenance.service;

import com.google.zxing.WriterException;
import com.ruoyi.maintenance.wechat.entity.QrCodeResponseBody;
import me.chanjar.weixin.common.error.WxErrorException;

import java.io.IOException;

/**
 * @author Abel
 * @since 2/5/2023 2:07 PM
 */
public interface IQrCodeService {

    /**
     * 调用微信接口获取二维码返回的响应体
     * @param id 渠道id. 也是场景值.
     * @return url
     */
    QrCodeResponseBody getQrCodeResponseBody(Integer id) throws WxErrorException;

    /**
     * 生成并保存渠道码
     * @param sonyChannel 渠道信息
     * @param channelCode 渠道代码
     */
    void saveQrCode(String qrCodeUrl, String channelCode) throws IOException, WriterException;
}
