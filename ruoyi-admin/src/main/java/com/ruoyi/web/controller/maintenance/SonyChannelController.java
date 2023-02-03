package com.ruoyi.web.controller.maintenance;

import com.google.zxing.WriterException;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.qrcode.QrCodeGenerator;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.common.wechat.util.JsonUtils;
import com.ruoyi.common.wechat.util.WechatUtil;
import com.ruoyi.maintenance.domain.SceneBody;
import com.ruoyi.maintenance.domain.SonyChannel;
import com.ruoyi.maintenance.service.ISonyChannelService;
import com.ruoyi.maintenance.service.IWechatService;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 渠道信息Controller
 *
 * @author Abel
 * @date 2023-02-01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/maintenance/channel")
public class SonyChannelController extends BaseController {
	private final ISonyChannelService sonyChannelService;
	
	private final IWechatService wechatService;
	
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
		// TODO: channelCode生成规则待定
		String channelCode = IdUtils.fastSimpleUUID();
		sonyChannel.setChannelCode(channelCode);
		sonyChannel.setCreatedBy(getUsername());
		int id = sonyChannelService.insertSonyChannel(sonyChannel);
		
		// 获取ticket和url
		try {
			String qrCodeUrl = wechatService.getQrCodeUrl(id);
			sonyChannel.setQrcodeUrl(qrCodeUrl);
		} catch (WxErrorException e) {
			logger.error("添加渠道 {} 获取微信accessToken出错", sonyChannel.getPrimaryChannel(), e);
			throw new ServiceException("添加渠道失败", HttpStatus.ERROR);
		}
		
		// 生成渠道码到指定位置
		try {
			String content = "http://weixin.qq.com/q/028K_RoQBEeL010000M07o";
			String filePath = RuoYiConfig.getUploadPath() + channelCode + ".png";
			QrCodeGenerator.uploadQRCodeImage(content, filePath);
		} catch (WriterException | IOException e) {
			logger.error("添加渠道 {} 生成渠道码出错",  sonyChannel.getPrimaryChannel(), e);
			throw new ServiceException("添加渠道失败", HttpStatus.ERROR);
		}
		
		// 更新该渠道qrCodeUrl
		this.edit(sonyChannel);
		logger.info("添加渠道 {} 成功", sonyChannel.getPrimaryChannel());
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
