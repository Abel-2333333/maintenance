package com.ruoyi.common.utils.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
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
     * 生成默认大小的渠道码
     *
     * @param content 二维码内容
     */
    public static BufferedImage generateQRCodeImage(String content) throws WriterException {
        return generateQRCodeImage(content, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    /**
     * 生成二维码
     *
     * @param content 二维码内容
     * @param width   二维码宽度
     * @param height  二维码高度
     * @throws WriterException 获取QRCodeWriter异常
     */
    public static BufferedImage generateQRCodeImage(String content, int width, int height) throws WriterException {
        BitMatrix bitMatrix = getBitMatrix(content, width, height);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    /**
     * @param content   二维码内容
     * @param imagePath 图片路径
     * @throws WriterException 获取QRCodeWriter异常
     */
    public static void uploadQRCodeImage(String content, String imagePath) throws WriterException, IOException {
        uploadQRCodeImage(content, DEFAULT_WIDTH, DEFAULT_HEIGHT, imagePath);
    }

    public static void uploadQRCodeImage(String content, int width, int height, String imagePath) throws WriterException, IOException {
        BufferedImage qrCode = generateQRCodeImage(content, width, height);
        ImageIO.write(qrCode, QrCodeGenerator.IMAGE_TYPE_PNG, new File(imagePath));
    }

    public static void uploadQRCodeImageWithLogo(String content, String imagePath, String logoPath) throws WriterException, IOException {
        uploadQRCodeImageWithLogo(content, DEFAULT_WIDTH, DEFAULT_HEIGHT, imagePath, logoPath);
    }

    public static void uploadQRCodeImageWithLogo(String content, int width, int height, String imagePath, String logoPath) throws WriterException, IOException {
        BufferedImage qrCode = generateQRCodeImageWithLogo(content, width, height, logoPath);
        ImageIO.write(qrCode, QrCodeGenerator.IMAGE_TYPE_PNG, new File(imagePath));
    }

    /**
     * 生成带logo二维码
     * @param content 二维码内容
     * @param logoPath logo存放的绝对路径
     * @return 带logo二维码
     */
    public static BufferedImage generateQRCodeImageWithLogo(String content, int width, int height, String logoPath) throws WriterException, IOException {
        BufferedImage qrCode = generateQRCodeImage(content, width, height);
        Graphics2D graphics = qrCode.createGraphics();
        BufferedImage logo = ImageIO.read(new File(logoPath));
        // 设置logo宽高为二维码的0.2
        int logoHeight = qrCode.getHeight() * 2 / 10;
        int logoWidth = qrCode.getWidth() * 2 / 10;
        // 设置logo在二维码中心
        int x = (qrCode.getWidth() - logoWidth) / 2;
        int y = (qrCode.getHeight() - logoWidth) / 2;
        //右下角，15为调整值
        int rightDownX = qrCode.getWidth() - logoWidth - 15;
        int rightDownY = qrCode.getHeight() - logoHeight - 15;
        graphics.drawImage(logo, x, y, logoWidth, logoHeight, null);
        graphics.drawRoundRect(rightDownX, rightDownY, logoWidth, logoHeight, 15, 15);
        //logo边框大小
        graphics.setStroke(new BasicStroke(2));
        //logo边框颜色
        graphics.setColor(Color.WHITE);
        graphics.drawRect(x, y, logoWidth, logoHeight);
        graphics.dispose();
        logo.flush();
        qrCode.flush();

        return qrCode;
    }

    /**
     * 生成位图
     *
     * @param content 二维码内容
     * @param width   二维码宽度
     * @param height  二维码高度
     * @return 位图
     * @throws WriterException 获取QRCodeWriter异常
     */
    private static BitMatrix getBitMatrix(String content, int width, int height) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        return qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
    }

    /**
     * 生成二维码，返回字节流
     *
     * @param content 二维码需要包含的信息
     * @param width   二维码宽度
     * @param height  二维码高度
     * @return 二维码字节流
     * @throws WriterException QRCodeWriter异常
     * @throws IOException     MatrixToImageWriter写成steam流会抛的异常
     */
    public static byte[] generateQRCodeStream(String content, int width, int height) throws WriterException, IOException {
        BitMatrix bitMatrix = getBitMatrix(content, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, IMAGE_TYPE_PNG, pngOutputStream);
        return pngOutputStream.toByteArray();
    }


}
