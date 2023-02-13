package com.ruoyi.maintenance.service;

import com.ruoyi.maintenance.wechat.entity.QrCodeResponseBody;
import com.ruoyi.maintenance.wechat.util.WechatUtil;
import com.ruoyi.maintenance.domain.EphemeralSceneBody;
import com.ruoyi.maintenance.domain.SceneBody;
import me.chanjar.weixin.common.error.WxErrorException;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 微信相关接口
 * @author Abel
 * @since 2/3/2023 4:30 PM
 */
public  interface IWechatService {

	default SceneBody<String> getSceneBody(Integer id) {
		return getSceneBody(Collections.singletonList(id.toString()), false);
	}

	default SceneBody<String> getSceneBody(List<String> ids) {
		return getSceneBody(Collections.singletonList(ids.toString()), false);
	}

	default SceneBody<String> getSceneBody(Integer id, boolean ephemeral) {
		return getSceneBody(Collections.singletonList(id.toString()), ephemeral);
	}

	/**
	 * 生成场景值请求体
	 * @param ids 渠道id. 跳转公众号要携带的场景值id
	 * @param ephemeral 是否为临时二维码
	 * @return 场景值请求体
	 */
	default SceneBody<String> getSceneBody(List<String> ids, boolean ephemeral) {
		if (ephemeral) {
			// 临时二维码
			EphemeralSceneBody<String> sceneBody = new EphemeralSceneBody<>();
			Map<String, String> sceneVal = Collections.singletonMap(WechatUtil.ACTION_INFO_SCENE_STR, String.join(",", ids));
			Map<String, Map<String, String>> actionInfoVal = Collections.singletonMap(WechatUtil.ACTION_INFO_SCENE, sceneVal);
			sceneBody.setActionName(WechatUtil.EPHEMERAL_ACTION_NAME_STR);
			sceneBody.setActionInfo(actionInfoVal);
			sceneBody.setExpireSeconds(604800L);
			return sceneBody;
		} else {
			// 永久二维码
			SceneBody<String> sceneBody = new SceneBody<>();
			Map<String, String> sceneVal = Collections.singletonMap(WechatUtil.ACTION_INFO_SCENE_STR, String.join(",", ids));
			Map<String, Map<String, String>> actionInfoVal = Collections.singletonMap(WechatUtil.ACTION_INFO_SCENE, sceneVal);
			sceneBody.setActionName(WechatUtil.ACTION_NAME_SCENE_STR);
			sceneBody.setActionInfo(actionInfoVal);
			return sceneBody;
		}
	}
	
	QrCodeResponseBody getQrCodeResponseBody(Integer id) throws WxErrorException;
}
