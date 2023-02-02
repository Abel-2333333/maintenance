package com.ruoyi.web.controller.maintenance;

import com.google.zxing.WriterException;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.qrcode.QrCodeGenerator;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.maintenance.domain.SonyChannel;
import com.ruoyi.maintenance.service.ISonyChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 渠道信息Controller
 *
 * @author Abel
 * @date 2023-02-01
 */
@RestController
@RequestMapping("/maintenance/channel")
public class SonyChannelController extends BaseController {
    @Autowired
    private ISonyChannelService sonyChannelService;

    /**
     * 查询渠道信息列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channel:list')")
    @GetMapping("/list")
    public TableDataInfo list(SonyChannel sonyChannel) {
        startPage();
        List<SonyChannel> list = sonyChannelService.selectSonyChannelList(sonyChannel);
        return getDataTable(list);
    }

    /**
     * 导出渠道信息列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channel:export')")
    @Log(title = "渠道信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SonyChannel sonyChannel) {
        List<SonyChannel> list = sonyChannelService.selectSonyChannelList(sonyChannel);
        ExcelUtil<SonyChannel> util = new ExcelUtil<SonyChannel>(SonyChannel.class);
        util.exportExcel(response, list, "渠道信息数据");
    }

    /**
     * 获取渠道信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(sonyChannelService.selectSonyChannelById(id));
    }

    /**
     * 新增渠道信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channel:add')")
    @Log(title = "渠道信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional
    public AjaxResult add(@RequestBody SonyChannel sonyChannel) {
        // 保存渠道信息
        String channelCode = IdUtils.fastSimpleUUID();
        sonyChannel.setChannelCode(channelCode);
        sonyChannel.setCreatedBy(getUsername());
        int rows = sonyChannelService.insertSonyChannel(sonyChannel);

        // 生成渠道码到指定位置
        try {
            BufferedImage qrCode = QrCodeGenerator.generateQRCode("http://weixin.qq.com/q/022LOroJBEeL01PdfBxz1y");
            File file = new File(RuoYiConfig.getUploadPath() + channelCode + ".png");
            ImageIO.write(qrCode, QrCodeGenerator.IMAGE_TYPE_PNG, file);
        } catch (WriterException | IOException e) {
            logger.error("渠道代码 {} 生成渠道码出错", channelCode);
            throw new RuntimeException(e);
        }
        return toAjax(rows);
    }

    /**
     * 修改渠道信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channel:edit')")
    @Log(title = "渠道信息", businessType = BusinessType.UPDATE)
    @PutMapping("update")
    public AjaxResult edit(@RequestBody SonyChannel sonyChannel) {
        sonyChannel.setUpdatedBy(getUsername());
        return toAjax(sonyChannelService.updateSonyChannel(sonyChannel));
    }

    /**
     * 删除渠道信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channel:remove')")
    @Log(title = "渠道信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sonyChannelService.deleteSonyChannelByIds(ids));
    }
}
