package com.ruoyi.common.wechat.util;

import cn.hutool.http.HttpUtil;
import com.ruoyi.common.utils.http.HttpUtils;

/**
 * 微信工具类
 * @author Abel
 * @since 2/2/2023 11:53 PM
 */
public class WechatUtil {
    public Object getTicket(String url, String accessToken, String body) {
        return HttpUtil.post(url, body);
    }


}
