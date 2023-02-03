package com.ruoyi.common.wechat.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 微信工具类
 * @author Abel
 * @since 2/2/2023 11:53 PM
 */
public class WechatUtil {

    private static final Logger logger = LoggerFactory.getLogger(WechatUtil.class);

    private static final  String CREATE_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
    public static final String ACTION_NAME_SCENE_STR = "QR_LIMIT_STR_SCENE";
    public static final String ACTION_NAME_SCENE_INT = "QR_LIMIT_SCENE";
    public static final String EPHEMERAL_ACTION_NAME_STR = "QR_STR_SCENE";
    public static final String EPHEMERAL_ACTION_NAME_SCENE_INT = "QR_SCENE";
    public static final String ACTION_INFO_SCENE = "scene";
    public static final String ACTION_INFO_SCENE_STR = "scene_str";
    
    /**
     * 获取微信二维码的ticket和url
     * @param accessToken 微信access_token
     * @param body 请求体
     * @return url
     */
    public static String getQRCodeUrl(String accessToken, String body) {
        String concatUrl = CREATE_TICKET_URL +accessToken;
        String response = HttpUtil.post(concatUrl, body);
        logger.info("获取ticket和url响应体: {}", response);
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.containsKey("errcode")) {
            throw new ServiceException("生成二维码出错", HttpStatus.ERROR);
        }
        return jsonObject.get("url", String.class);
    }
    
}
