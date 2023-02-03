package com.ruoyi.maintenance.service.impl;

import com.ruoyi.common.wechat.util.JsonUtils;
import com.ruoyi.common.wechat.util.WechatUtil;
import com.ruoyi.maintenance.domain.SceneBody;
import com.ruoyi.maintenance.service.IWechatService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Abel
 * @since 2/3/2023 4:30 PM
 */
@Slf4j
@Service
public class WechatServiceImpl implements IWechatService {
	@Resource
	private WxMpService wxMpService;
	
	/**
	 * 调微信接口获取生成二维码必需的ticket及url
	 * @param id 渠道id
	 * @return url 扫描二维码跳转的url
	 * @throws WxErrorException 获取access_token异常
	 */
	@Override
	public String getQrCodeUrl(Integer id) throws WxErrorException {
		SceneBody sceneBody = getSceneBody(id);
		String accessToken = wxMpService.getAccessToken();
		return WechatUtil.getQRCodeUrl(accessToken, JsonUtils.toJson(sceneBody));
	}
	
	
	/**
	 * 生成场景值请求体
	 * @param ids 渠道id. 跳转公众号要携带的场景值id
	 */
	public SceneBody getSceneBody(List<String> ids) {
		// 设置二维码字符串场景值
		SceneBody sceneBody = new SceneBody();
		Map<String, String> sceneStr = new HashMap<>();
		// 拼接渠道id作为场景值
		sceneStr.put(WechatUtil.ACTION_INFO_SCENE_STR, String.join(",", ids));
		HashMap<String, Map<String, ?>> actionInfo = new HashMap<>();
		actionInfo.put(WechatUtil.ACTION_INFO_SCENE, sceneStr);
		
		sceneBody.setActionName(WechatUtil.ACTION_NAME_SCENE_STR);
		sceneBody.setActionInfo(actionInfo);
		return sceneBody;
	}
	
	public SceneBody getSceneBody(Integer id) {
		return getSceneBody(Collections.singletonList(id.toString()));
	}
}
