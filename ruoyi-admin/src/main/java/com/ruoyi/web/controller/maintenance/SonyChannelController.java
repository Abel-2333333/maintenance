package com.ruoyi.web.controller.maintenance;

import cn.hutool.core.date.DateUtil;
import com.google.zxing.WriterException;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.wechat.util.FileUtils;
import com.ruoyi.common.wechat.util.RedisUtils;
import com.ruoyi.maintenance.domain.SonyChannel;
import com.ruoyi.maintenance.domain.dto.SonyChannelDTO;
import com.ruoyi.maintenance.domain.excel.SonyChannelExcelVO;
import com.ruoyi.maintenance.service.IQrCodeService;
import com.ruoyi.maintenance.service.ISonyChannelService;
import com.ruoyi.maintenance.service.IWechatService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 渠道信息Controller
 *
 * @author Abel
 * @date 2023-02-01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/maintenance/channel")
@Validated
public class SonyChannelController extends BaseController {
    private final ISonyChannelService sonyChannelService;

    private final IWechatService wechatService;

    private final IQrCodeService qrCodeService;

    private final RedisCache redisCache;

    private final RedisUtils redisUtils;

    /**
     * 查询渠道信息列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channel:list')")
    @GetMapping("/list")
    public TableDataInfo list(SonyChannelDTO sonyChannel) {
        startPage();
        List<SonyChannel> list = sonyChannelService.selectSonyChannelListByDTO(sonyChannel);
        return getDataTable(list);
    }

    /**
     * 导出渠道信息列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channel:export')")
    @Log(title = "渠道信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestParam(required = false) List<Integer> ids) {
        List<SonyChannelExcelVO> list = sonyChannelService.selectSonyChannelListByIds(ids);
        list.forEach(e -> {
            e.setQrCode(FileUtils.getQrCodePath(e.getChannelCode()));
            e.setQrCodeWithLogo(FileUtils.getQrCodeWithLogoPath(e.getChannelCode()));
        });
        try {
            FileUtils.export(response, "渠道信息", list);
        } catch (Exception e) {
            logger.error("导出渠道信息时出错了", e);
            throw new ServiceException("导出渠道信息出错", HttpStatus.ERROR);
        }
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
    public AjaxResult add(@Valid @RequestBody SonyChannel sonyChannel) {
        // 生成渠道代码. 规则: yyyyMMdd + 四位递增数字(每天凌晨重置为0000), 例: 202302050001
        String yyyyMMdd = DateUtil.format(LocalDateTime.now(), "yyyyMMdd");
        String dailyChannelNum;
        Integer tmpDailyChannelNum;
        try {
            Integer channelCodeVal = redisCache.getCacheObject(redisUtils.getChannelCodeKey());
            tmpDailyChannelNum = channelCodeVal;
            AtomicInteger num = new AtomicInteger(channelCodeVal);
            dailyChannelNum = String.format("%04d", num.incrementAndGet());
            redisCache.setCacheObject(redisUtils.getChannelCodeKey(), num);
        } catch (Exception e) {
            logger.error("Redis获取当日渠道数出错");
            throw new ServiceException("添加渠道失败", HttpStatus.ERROR);
        }
        String channelCode = String.format("%s%s", yyyyMMdd, dailyChannelNum);

        // 保存渠道信息
        sonyChannel.setChannelCode(channelCode);
        sonyChannel.setCreatedBy(getUsername());
        int id = sonyChannelService.insertSonyChannel(sonyChannel);

        String qrCodeUrl = null;
        try {
            // 获取微信二维码跳转url
            qrCodeUrl = qrCodeService.getQrCodeUrl(id);
            // 生成渠道码到指定位置
            qrCodeService.saveQrCode(sonyChannel, channelCode);
        } catch (WxErrorException e) {
            logger.error("添加编号为{}的渠道获取微信accessToken出错", id, e);
            throw new ServiceException("添加渠道失败", HttpStatus.ERROR);
        } catch (IOException | WriterException e) {
            logger.error("添加渠道 {} 生成渠道码出错", sonyChannel.getPrimaryChannel(), e);
            throw new ServiceException("添加渠道失败", HttpStatus.ERROR);
        } finally {
            // 抛异常需要恢复当天新增渠道数
            redisCache.setCacheObject(redisUtils.getChannelCodeKey(), tmpDailyChannelNum);
        }
        sonyChannel.setQrcodeUrl(qrCodeUrl);

        // 更新该渠道qrCodeUrl
        this.edit(sonyChannel);
        logger.info("添加渠道 {} 成功. 渠道信息: {}", sonyChannel.getPrimaryChannel(), sonyChannel);
        return toAjax(id);
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
