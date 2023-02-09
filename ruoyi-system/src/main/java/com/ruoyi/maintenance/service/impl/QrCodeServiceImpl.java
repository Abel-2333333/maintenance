package com.ruoyi.maintenance.service.impl;

import com.google.zxing.WriterException;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.qrcode.QrCodeGenerator;
import com.ruoyi.common.wechat.entity.QrCodeResponseBody;
import com.ruoyi.common.wechat.util.FileUtils;
import com.ruoyi.maintenance.service.IQrCodeService;
import com.ruoyi.maintenance.service.IWechatService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author Abel
 * @since 2/5/2023 2:07 PM
 */
@Service
public class QrCodeServiceImpl implements IQrCodeService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private IWechatService wechatService;

    @Override
    public QrCodeResponseBody getQrCodeResponseBody(Integer id) throws WxErrorException {
        return wechatService.getQrCodeResponseBody(id);
    }

    @Override
    public void saveQrCode(String qrCodeUrl, String channelCode) throws IOException, WriterException {
        String content = qrCodeUrl;
        String qrCodePath = FileUtils.getQrCodePath(channelCode);
        String qrCodeWithLogoPath = FileUtils.getQrCodeWithLogoPath(channelCode);
        String logoPath = RuoYiConfig.getLogo();
        // 生成不带logo二维码并保存
        QrCodeGenerator.uploadQRCodeImage(content, qrCodePath);
        // 生成带logo二维码并保存
        QrCodeGenerator.uploadQRCodeImageWithLogo(content, qrCodeWithLogoPath, logoPath);
    }
}
