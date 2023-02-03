package com.ruoyi.maintenance.service;

import me.chanjar.weixin.common.error.WxErrorException;

/**
 * @author Abel
 * @since 2/3/2023 4:30 PM
 */
public interface IWechatService {

	String getQrCodeUrl(Integer id) throws WxErrorException;
}
