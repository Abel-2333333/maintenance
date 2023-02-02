package com.ruoyi.common.wechat.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Abel Wong
 * 2023-01-16 20:50
 */
public class JsonUtils {
    public static String toJson(Object obj) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
        return gson.toJson(obj);
    }
}
