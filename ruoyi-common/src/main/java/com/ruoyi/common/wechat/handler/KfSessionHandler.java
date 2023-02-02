package com.ruoyi.common.wechat.handler;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Abel Wong
 * 2023-01-16 20:39
 */
@Component
public class KfSessionHandler extends AbstractHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, 
                                    Map<String, Object> context, WxMpService wxMpService, 
                                    WxSessionManager sessionManager) throws WxErrorException {
        //TODO 对会话做处理
        return null;
    }
}
