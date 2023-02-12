package com.ruoyi.maintenance.wechat.util;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.qrcode.QrCodeGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * @author Abel
 * @since 2/5/2023 10:01 PM
 */
public class FileUtils <T> {

    private static final Logger logger = LoggerFactory.getLogger ( FileUtils.class );

    /**
     * 获取二维码绝对路径
     *
     * @param channelCode 渠道代码
     * @return 绝对路径
     */
    public static String getQrCodePath ( String channelCode ) {
        return RuoYiConfig.getUploadPath () + getQrCodeName ( channelCode );
    }

    /**
     * 获取带logo二维码绝对路径
     *
     * @param channelCode 渠道代码
     * @return 带logo二维码的绝对路径
     */
    public static String getQrCodeWithLogoPath ( String channelCode ) {
        return RuoYiConfig.getUploadPath () + getQrCodeWithLogoName ( channelCode );
    }

    /**
     * 获取二维码文件名
     *
     * @param channelCode 渠道代码
     * @return 二维码文件名
     */
    public static String getQrCodeName ( String channelCode ) {
        return channelCode + "." + QrCodeGenerator.IMAGE_TYPE_JPEG;
    }

    /**
     * 获取带logo二维码文件名
     *
     * @param channelCode 渠道代码
     * @return 带logo二维码文件名
     */
    public static String getQrCodeWithLogoName ( String channelCode ) {
        return channelCode + "_logo" + "." + QrCodeGenerator.IMAGE_TYPE_JPEG;
    }

    public static String getQrCodeUrl ( String channelCode ) {
        return RuoYiConfig.getImagePath () + getQrCodeName ( channelCode );
    }

    public static String getLogoPath () {
        return RuoYiConfig.getLogo ();
    }

    public static String getQrCodeWithLogoUrl ( String channelCode ) {
        return RuoYiConfig.getImagePath () + getQrCodeWithLogoName ( channelCode );
    }

    /**
     * @param response  HttpServletResponse
     * @param sheetName 工作页名
     * @param list      待导出数据
     */
    public static <T> void export ( HttpServletResponse response, String sheetName, List <T> list ) throws IOException {
        response.setContentType ( "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
        response.setCharacterEncoding ( "utf-8" );
        EasyExcelFactory.write ( response.getOutputStream (), list.get ( 0 ).getClass () )
                .registerWriteHandler ( new LongestMatchColumnWidthStyleStrategy () )
                .sheet ( sheetName )
                .doWrite ( list );
    }

    public static <T> void export ( HttpServletResponse response, String sheetName, List <T> list, Class <T> clazz ) throws IOException {
        response.setContentType ( "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" );
        response.setCharacterEncoding ( "utf-8" );
        EasyExcelFactory.write ( response.getOutputStream (), clazz )
                .registerWriteHandler ( new LongestMatchColumnWidthStyleStrategy () )
                .sheet ( sheetName )
                .doWrite ( list );
    }

    public static void removeFile ( String filepath ) throws IOException {
        File file = new File ( filepath );
        Path path = FileSystems.getDefault ().getPath ( file.getAbsolutePath () );
        try {
            Files.deleteIfExists ( path );
        } catch ( IOException e ) {
            logger.error ( "删除{}失败", file.getName () );
            throw new IOException ( e );
        }
    }
}
