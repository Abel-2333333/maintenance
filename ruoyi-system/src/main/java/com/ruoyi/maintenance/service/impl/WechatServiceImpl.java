package com.ruoyi.maintenance.service.impl;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.wechat.config.WxMpProperties;
import com.ruoyi.common.wechat.entity.QrCodeResponseBody;
import com.ruoyi.common.wechat.util.WechatUtil;
import com.ruoyi.maintenance.domain.SceneBody;
import com.ruoyi.maintenance.service.IWechatService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Abel
 * @since 2/3/2023 4:30 PM
 */
@Slf4j
@Service
public class WechatServiceImpl implements IWechatService {
	@Resource
	private WxMpService wxMpService;

	@Resource
	private IWechatService wechatService;

	@Resource
	private WxMpProperties wxMpProperties;
	
	/**
	 * 调微信接口获取生成二维码必需的ticket及url
	 * @param id 渠道id
	 * @return url 扫描二维码跳转的url
	 * @throws WxErrorException 获取access_token异常
	 */
	@Override
	public QrCodeResponseBody getQrCodeResponseBody(Integer id) throws WxErrorException {
		boolean ephemeral = wxMpProperties.isEphemeral();
		SceneBody<String> sceneBody = wechatService.getSceneBody(id, ephemeral);
		String accessToken = wxMpService.getAccessToken();
		return WechatUtil.getQrCodeResponseBody(accessToken, JSON.toJSONString(sceneBody));
	}

}
