package com.ruoyi.common.utils.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Hashtable;

import static org.springframework.data.redis.core.convert.Bucket.CHARSET;

/**
 * 二维码生成工具类
 *
 * @author Abel
 * @since 2/2/2023 9:25 PM
 */
public class QrCodeGenerator {

    /**
     * 几种常见的图片格式
     */
    public static String IMAGE_TYPE_GIF = "gif";// 图形交换格式
    public static String IMAGE_TYPE_JPG = "jpg";// 联合照片专家组
    public static String IMAGE_TYPE_JPEG = "jpeg";// 联合照片专家组
    public static String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
    public static String IMAGE_TYPE_PNG = "png";// 可移植网络图形
    public static String IMAGE_TYPE_PSD = "psd";// Photoshop的专用格式Photoshop

    public static int DEFAULT_WIDTH = 645;
    public static int DEFAULT_HEIGHT = 645;

    /**
     * 生成二维码, 写到指定路径
     *
     * @param content 二维码内容
     * @param width   二维码宽度
     * @param height  二维码高度
     * @param path    存放路径
     * @throws
     */
    public static BufferedImage generateQRCode(String content, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    /**
     * 生成默认大小的渠道码
     * @param content 二维码内容
     * @param path 存放路径
     */
    public static BufferedImage generateQRCode(String content) throws WriterException {
        return generateQRCode(content, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * 生成二维码，返回字节流
     *
     * @param content   二维码需要包含的信息
     * @param width  二维码宽度
     * @param height 二维码高度
     * @return 二维码字节流
     * @throws WriterException QRCodeWriter异常
     * @throws IOException     MatrixToImageWriter写成steam流会抛的异常
     */
    public static byte[] getQRCodeImage(String content, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, IMAGE_TYPE_PNG, pngOutputStream);
        return pngOutputStream.toByteArray();
    }


}
