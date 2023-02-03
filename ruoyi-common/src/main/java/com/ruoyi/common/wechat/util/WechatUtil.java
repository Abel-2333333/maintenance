package com.ruoyi.common.wechat.util;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;

/**
 * 微信工具类
 * @author Abel
 * @since 2/2/2023 11:53 PM
 */
public class WechatUtil {

    private static final  String CREATE_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
    public static final String ACTION_NAME_SCENE_STR = "QR_LIMIT_STR_SCENE";
    public static final String ACTION_NAME_SCENE_INT = "QR_LIMIT_SCENE";
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
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.get("url", String.class);
    }
    
}
