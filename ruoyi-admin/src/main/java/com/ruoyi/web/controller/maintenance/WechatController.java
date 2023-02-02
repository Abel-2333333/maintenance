package com.ruoyi.web.controller.maintenance;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

/**
 * @author Abel
 * @since 1/10/2023 9:39 PM
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/wx/checkSignature/{appId}")
@Api("微信开发者接口")
public class WechatController {

    private final WxMpService wxService;
    private final WxMpMessageRouter messageRouter;

    /**
     * 接受微信开放平台认证请求
     */
    @ApiOperation(value="微信公众号开发者认证请求")
    @GetMapping(produces = "text/plain;charset=utf-8")
    public String checkSignature(
            @PathVariable String appId,
            @RequestParam(name = "signature", required = false) String signature,
            @RequestParam(name = "timestamp", required = false) String timestamp,
            @RequestParam(name = "nonce", required = false) String nonce,
            @RequestParam(name = "echostr", required = false) String echostr) throws IOException {

        log.info("接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature,
                timestamp, nonce, echostr);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }
        if (!this.wxService.switchover(appId)) {
            throw new IllegalArgumentException(String.format("未找到对应appId=[%s]的配置，请核实！", appId));
        }

        if (wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }
        return "非法请求";
    }

    /**
     * 处理微信服务器的请求, 如自动回复, 客服消息等
     */
    @ApiOperation("微信公众号各功能调用")
    @PostMapping(produces = "application/xml; charset=UTF-8")
    public String post(
            @PathVariable(required = false) String appId,
            @RequestBody String requestBody,
            @RequestParam("signature") String signature,
            @RequestParam("timestamp") String timestamp,
            @RequestParam("nonce") String nonce,
            @RequestParam(value = "openid") String openid,
            @RequestParam(name = "encrypt_type", required = false) String encType,
            @RequestParam(name = "msg_signature", required = false) String msgSignature) {
        log.info(" 接收微信请求：[openid=[{}], [signature=[{}], encType=[{}], msgSignature=[{}],"
                        + " timestamp=[{}], nonce=[{}], requestBody=[ {}] ",
                openid, signature, encType, msgSignature, timestamp, nonce, requestBody);

        if (!this.wxService.switchover(appId)) {
            throw new IllegalArgumentException(String.format("未找到对应appId=[%s]的配置，请核实！", appId));
        }

        if (!wxService.checkSignature(timestamp, nonce, signature)) {
            throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
        }
        String out = null;
        if (encType == null) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
            log.debug("\n消息内容为：\n{} ", inMessage.toString());
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toXml();
        } else if ("aes".equalsIgnoreCase(encType)) {
            // aes加密的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxService.getWxMpConfigStorage(),
                    timestamp, nonce, msgSignature);
            log.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
            WxMpXmlOutMessage outMessage = this.route(inMessage);
            if (outMessage == null) {
                return "";
            }

            out = outMessage.toEncryptedXml(wxService.getWxMpConfigStorage());
        }
//            WxMpXmlOutNewsMessage outTextMessage = new WxMpXmlOutNewsMessage();
//            outTextMessage.setToUserName(outMessage.getToUserName());
//            outTextMessage.setFromUserName(outMessage.getFromUserName());
//            outTextMessage.setCreateTime(System.currentTimeMillis());
//            outTextMessage.setMsgType("news");
//            outTextMessage.setArticleCount(1);
//            WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
//            item.setTitle("测试图文链接");
//            item.setDescription("这是一个图文测试的demo");
//            item.setPicUrl("https://img1.baidu.com/it/u=2633894183,3957099900&fm=253&fmt=auto&app=138&f=PNG?w=500&h=500");
//            item.setUrl("http://www.baidu.com");
//            outTextMessage.addArticle(item);
//            out = outTextMessage.toXml();

        log.debug(" 组装回复信息：{}", out);
        return out;
    }

    private WxMpXmlOutMessage route(WxMpXmlMessage message) {
        try {
            return this.messageRouter.route(message);
        } catch (Exception e) {
            log.error("路由消息时出现异常！", e);
        }
        return null;
    }

    public static void main(String[] args) {
        File file = new File("usr/java/upload");
        if (!file.isDirectory()) {
            file.mkdir();
        }
    }
}
