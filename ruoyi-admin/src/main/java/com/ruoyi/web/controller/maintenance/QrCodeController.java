package com.ruoyi.web.controller.maintenance;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.utils.qrcode.QrCodeGenerator;
import com.ruoyi.maintenance.service.ISonyChannelService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Abel
 * @since 2/2/2023 9:51 PM
 */
@RestController("/maintenance/channel")
public class QrCodeController {

    @Resource
    private ISonyChannelService sonyChannelService;

    @GetMapping("code")
    public ResponseEntity<byte[]> getQRImageByResponseEntity(String id) {


        // Header设置文件类型（对于ResponseEntity响应的方式，必须设置文件类型）
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>("qrCode".getBytes(), headers, HttpStatus.CREATED);
    }
}
