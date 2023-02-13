package com.ruoyi.common.utils.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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

    public static int DEFAULT_WIDTH = 430;
    public static int DEFAULT_HEIGHT = 430;

    private static Logger logger = LoggerFactory.getLogger ( QrCodeGenerator.class );

    /**
     * 生成默认大小的渠道码
     *
     * @param content 二维码内容
     */
    public static BufferedImage generateQRCodeImage ( String content ) throws WriterException {
        return generateQRCodeImage ( content, DEFAULT_WIDTH, DEFAULT_HEIGHT );
    }

    /**
     * 生成二维码
     *
     * @param content 二维码内容
     * @param width   二维码宽度
     * @param height  二维码高度
     * @throws WriterException 获取QRCodeWriter异常
     */
    public static BufferedImage generateQRCodeImage ( String content, int width1, int height1 ) throws WriterException {
        BitMatrix bitMatrix = getBitMatrix ( content, width1, height1 );
        int width = bitMatrix.getWidth ();
        int height = bitMatrix.getHeight ();
        BufferedImage image = new BufferedImage ( width, height, BufferedImage.TYPE_INT_RGB );
        for ( int x = 0; x < width; x++ ) {
            for ( int y = 0; y < height; y++ ) {
                image.setRGB ( x, y, bitMatrix.get ( x, y ) ? 0xFF000000 : 0xFFFFFFFF );
            }
        }
        return image;
    }

    /**
     * @param content   二维码内容
     * @param imagePath 图片路径
     * @throws WriterException 获取QRCodeWriter异常
     */
    public static void uploadQRCodeImage ( String content, String imagePath ) throws WriterException, IOException {
        uploadQRCodeImage ( content, DEFAULT_WIDTH, DEFAULT_HEIGHT, imagePath );
    }

    public static void uploadQRCodeImage ( String content, int width, int height, String imagePath ) throws WriterException, IOException {
        BufferedImage qrCode = generateQRCodeImage ( content, width, height );
        ImageIO.write ( qrCode, QrCodeGenerator.IMAGE_TYPE_JPEG, new File ( imagePath ) );
    }

    public static void uploadQRCodeImageWithLogo ( String content, String imagePath, String logoPath ) throws WriterException, IOException {
        uploadQRCodeImageWithLogo ( content, DEFAULT_WIDTH, DEFAULT_HEIGHT, imagePath, logoPath );
    }

    public static void uploadQRCodeImageWithLogo ( String content, int width, int height, String imagePath, String logoPath ) throws WriterException, IOException {
        BufferedImage qrCode = generateQRCodeImageWithLogo ( content, width, height, logoPath );
        ImageIO.write ( qrCode, QrCodeGenerator.IMAGE_TYPE_JPEG, new File ( imagePath ) );
    }

    /**
     * 生成带logo二维码
     *
     * @param content  二维码内容
     * @param logoPath logo存放的绝对路径
     * @return 带logo二维码
     */
    public static BufferedImage generateQRCodeImageWithLogo ( String content, int width, int height, String logoPath ) throws WriterException, IOException {
        BufferedImage qrCode = generateQRCodeImage ( content, width, height );
        Graphics2D graphics = qrCode.createGraphics ();
        Image logo = null;
        try {
            File logoFile = new File ( logoPath );
            logo = ImageIO.read ( logoFile );
        } catch ( IOException e ) {
            logger.error ( "logo路径: {} 读取失败", logoPath );
            throw new IOException ( e );
        }
        // 设置logo宽高为二维码的0.2
        int logoHeight = qrCode.getHeight () * 2 / 10;
        int logoWidth = qrCode.getWidth () * 2 / 10;
        // 设置logo在二维码中心
        int x = ( qrCode.getWidth () - logoWidth ) / 2;
        int y = ( qrCode.getHeight () - logoWidth ) / 2;
        //右下角，15为调整值
        int rightDownX = qrCode.getWidth () - logoWidth - 15;
        int rightDownY = qrCode.getHeight () - logoHeight - 15;
        graphics.drawImage ( logo, x, y, logoWidth, logoHeight, null );
//        Shape shape = new RoundRectangle2D.Float(x, y, logoWidth, logoHeight, 6, 6);
        graphics.drawRoundRect ( rightDownX, rightDownY, logoWidth, logoHeight, 10, 10 );
        //logo边框大小
        graphics.setStroke ( new BasicStroke ( (float) (width * 0.02) ) );
        //logo边框颜色
        graphics.setColor ( new Color ( 247, 247, 247 ) );
        graphics.drawRoundRect ( x, y, logoWidth, logoHeight, 10, 10 );
        graphics.dispose ();
        logo.flush ();
        qrCode.flush ();

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
    private static BitMatrix getBitMatrix ( String content, int width, int height ) throws WriterException {
        MultiFormatWriter qrCodeWriter = new MultiFormatWriter ();
        Hashtable <EncodeHintType, Object> hints = new Hashtable <> ();
        hints.put ( EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H );
        hints.put ( EncodeHintType.CHARACTER_SET, CHARSET );
        hints.put ( EncodeHintType.MARGIN, 1 );
        return qrCodeWriter.encode ( content, BarcodeFormat.QR_CODE, width, height, hints );
    }

    /**
     * 生成二维码
     *
     * @param image        二维码
     * @param outputStream 二维码输出流
     * @throws IOException MatrixToImageWriter写成steam流会抛的异常
     */
    public static void writeToStream ( BufferedImage image, OutputStream outputStream ) throws IOException {
        ImageIO.write ( image, QrCodeGenerator.IMAGE_TYPE_JPEG, outputStream );
    }

    public static void downloadQrCode ( String content, int width, int height, ServletOutputStream outputStream ) throws IOException, WriterException {
        downloadQrCode ( content, width, height, outputStream, null );
    }

    public static void downloadQrCode ( String content, int width, int height, ServletOutputStream outputStream, String logo ) throws IOException, WriterException {
        BufferedImage bufferedImage;
        try {
            if ( logo == null || logo.equals ( "" ) ) {
                bufferedImage = generateQRCodeImage ( content, width, height );
            } else {
                bufferedImage = generateQRCodeImageWithLogo ( content, width, height, logo );
            }
            QrCodeGenerator.writeToStream ( bufferedImage, outputStream );
        } catch ( WriterException e ) {
            logger.error ( "生成二维码出错" );
            throw new WriterException ( e );
        } catch ( IOException e ) {
            logger.error ( "生成二维码出错" );
            throw new IOException ( e );
        } finally {
            outputStream.close ();
        }
    }
}
