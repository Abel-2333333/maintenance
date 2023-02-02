package com.ruoyi.common.wechat.handler;

import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Abel Wong
 * 2023-01-16 20:37
 */
public abstract class AbstractHandler implements WxMpMessageHandler {
    protected Logger logger = LoggerFactory.getLogger(getClass());
}
