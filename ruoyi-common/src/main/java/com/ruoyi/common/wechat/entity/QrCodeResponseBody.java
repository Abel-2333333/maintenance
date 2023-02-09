package com.ruoyi.common.wechat.entity;

import lombok.Data;

/**
 * 调用微信获取二维码接口返回的结果
 * @author Abel
 * @since 2/9/2023 10:46 AM
 */
@Data
public class QrCodeResponseBody {
	private String url;
	private String ticket;
	
	private Long expireTime;
}
