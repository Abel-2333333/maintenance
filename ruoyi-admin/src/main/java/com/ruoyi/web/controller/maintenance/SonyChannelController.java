package com.ruoyi.web.controller.maintenance;

import cn.hutool.extra.servlet.ServletUtil;
import com.alibaba.excel.EasyExcelFactory;
import com.google.zxing.WriterException;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.qrcode.QrCodeGenerator;
import com.ruoyi.maintenance.wechat.util.FileUtils;
import com.ruoyi.maintenance.domain.SonyChannel;
import com.ruoyi.maintenance.domain.dto.QrCodeDownloadDTO;
import com.ruoyi.maintenance.domain.dto.SonyChannelDTO;
import com.ruoyi.maintenance.domain.excel.SonyChannelExcelVO;
import com.ruoyi.maintenance.domain.excel.SonyChannelImportVO;
import com.ruoyi.maintenance.domain.listener.SonyChannelListener;
import com.ruoyi.maintenance.domain.vo.SonyChannelVO;
import com.ruoyi.maintenance.mapper.SonyChannelMapper;
import com.ruoyi.maintenance.service.ISonyChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 渠道信息Controller
 *
 * @author Abel
 * @date 2023-02-01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping ( "/maintenance/channel" )
@Validated
public class SonyChannelController extends BaseController {
    private final ISonyChannelService sonyChannelService;

    private final SonyChannelMapper sonyChannelMapper;

    private final ThreadPoolTaskExecutor executor;

    /**
     * 查询渠道信息列表
     */
    @PreAuthorize ( "@ss.hasPermi('maintenance:channel:list')" )
    @PostMapping ( "/list" )
    public TableDataInfo list ( @RequestBody SonyChannelDTO sonyChannel ) {
        startPage ();
        List <SonyChannelVO> list = sonyChannelService.selectSonyChannelListByDTO ( sonyChannel );
        return getDataTable ( list );
    }


    /**
     * 获取渠道信息详细信息
     */
    @PreAuthorize ( "@ss.hasPermi('maintenance:channel:query')" )
    @GetMapping ( value = "/{id}" )
    public AjaxResult getInfo ( @PathVariable ( "id" ) Long id ) {
        return success ( sonyChannelService.selectSonyChannelById ( id ) );
    }

    /**
     * 新增渠道信息
     */
    @PreAuthorize ( "@ss.hasPermi('maintenance:channel:add')" )
    @Log ( title = "渠道信息", businessType = BusinessType.INSERT )
    @PostMapping ( "/add" )
    @Transactional
    public AjaxResult add ( @Valid @RequestBody SonyChannel sonyChannel ) {
        sonyChannelService.addChannel ( sonyChannel );
        return success ();
    }

    /**
     * 修改渠道信息
     */
    @PreAuthorize ( "@ss.hasPermi('maintenance:channel:edit')" )
    @Log ( title = "渠道信息", businessType = BusinessType.UPDATE )
    @PostMapping ( "update" )
    public AjaxResult edit ( @RequestBody SonyChannel sonyChannel ) {
        sonyChannel.setUpdatedBy ( getUsername () );
        return toAjax ( sonyChannelService.updateSonyChannel ( sonyChannel ) );
    }

    /**
     * 删除渠道信息
     */
    @PreAuthorize ( "@ss.hasPermi('maintenance:channel:remove')" )
    @Log ( title = "渠道信息", businessType = BusinessType.DELETE )
    @PostMapping ( "/delete" )
    public AjaxResult remove ( @NotEmpty ( message = "没有要删除的记录" ) @RequestBody List <Long> ids ) {
        sonyChannelService.batchDelete ( ids );
        return success ();
    }

    /**
     * 获取维修站名称
     */
    @PreAuthorize ( "@ss.hasPermi('maintenance:channel:query')" )
    @PostMapping ( "/names" )
    public AjaxResult getStationNames () {
        List <SonyChannel> list = sonyChannelService.selectSonyChannelList ( new SonyChannel () );
        List <String> stationNameList = list.stream ().map ( SonyChannel::getMaintenanceStationName ).distinct ().filter ( Objects::nonNull ).collect ( Collectors.toList () );
        return success ( stationNameList );
    }

    /**
     * 重新生成渠道码
     */
    @PreAuthorize ( "@ss.hasPermi('maintenance:channel:download')" )
    @PostMapping ( "/generate" )
    public AjaxResult regenerateQrCode ( @NotNull ( message = "请选择要重新生成渠道码的渠道" ) @RequestBody List <Long> ids ) {
        sonyChannelService.regenerateQrCode ( ids );
        return success ();
    }

    /**
     * 下载渠道模板
     */
    @PreAuthorize ( "@ss.hasPermi('maintenance:channel:download')" )
    @PostMapping ( "/download" )
    public void downloadTemplate ( HttpServletResponse response ) {
        logger.info ( "{}下载了模板", getUsername () );
        try {
            ClassPathResource classPathResource = new ClassPathResource ( "template/channel_template.xlsx" );
            BufferedInputStream inputStream = new BufferedInputStream ( classPathResource.getInputStream () );
            // 设置Content-Type
            response.setContentType ( "application/octet-stream" );
            response.setHeader ( "content-type", "application/octet-stream" );

            ServletUtil.write ( response, inputStream );
        } catch ( IOException e ) {
            logger.error ( "获取文件流失败", e );
            throw new ServiceException ( "下载模板失败", HttpStatus.ERROR );
        }
    }

    /**
     * 批量导入
     */
    @PreAuthorize ( "@ss.hasPermi('maintenance:channel:import')" )
    @Log ( title = "渠道信息", businessType = BusinessType.IMPORT )
    @PostMapping ( "/upload" )
    public AjaxResult upload ( @RequestParam ( "file" ) MultipartFile file ) throws IOException {
        EasyExcelFactory.read ( file.getInputStream (), SonyChannelImportVO.class,
                new SonyChannelListener ( sonyChannelService, executor ) ).sheet ().doRead ();
        return success ();
    }


    /**
     * 导出渠道信息列表
     */
    @PreAuthorize ( "@ss.hasPermi('maintenance:channel:export')" )
    @Log ( title = "渠道信息", businessType = BusinessType.EXPORT )
    @PostMapping ( "/export" )
    public void export ( HttpServletResponse response, @RequestBody ( required = false ) List <Integer> ids ) {
        List <SonyChannelExcelVO> list = sonyChannelService.selectSonyChannelListByIds ( ids );
        list.forEach ( e -> {
            // 填充二维码绝对路径
            e.setQrCode ( FileUtils.getQrCodePath ( e.getChannelCode () ) );
            e.setQrCodeWithLogo ( FileUtils.getQrCodeWithLogoPath ( e.getChannelCode () ) );
        } );
        try {
            FileUtils.export ( response, "渠道信息", list );
        } catch ( Exception e ) {
            logger.error ( "导出渠道信息时出错了", e );
            throw new ServiceException ( "导出渠道信息出错", HttpStatus.ERROR );
        }
    }

    /**
     * 不同尺寸渠道码下载
     */
    @PreAuthorize ( "@ss.hasPermi('maintenance:channel:download')" )
    @Log ( title = "渠道信息", businessType = BusinessType.DOWNLOAD )
    @PostMapping ( "/downloadCode" )
    public void downloadCode ( HttpServletResponse response, @RequestBody QrCodeDownloadDTO dto ) {
        Long id = dto.getId ();
        SonyChannel sonyChannel = sonyChannelService.selectSonyChannelById ( id );

        String qrcodeUrl = Objects.requireNonNull ( sonyChannel, "该渠道不存在" ).getQrcodeUrl ();
        int width = dto.getWidth ();
        int height = dto.getHeight ();

        // 生成二维码并响应
        String logo = null;
        try {
            if ( dto.isWithLogo () ) {
                logo = FileUtils.getLogoPath ();
            }
            response.setCharacterEncoding ( "UTF-8" );
            response.setContentType("image/jpeg");
            QrCodeGenerator.downloadQrCode ( qrcodeUrl, width, height, response.getOutputStream (), logo );
        } catch ( WriterException | IOException e ) {
            throw new ServiceException ( "下载渠道码出错", HttpStatus.ERROR );
        }
    }
}
