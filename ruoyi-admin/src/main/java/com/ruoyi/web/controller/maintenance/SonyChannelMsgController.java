package com.ruoyi.web.controller.maintenance;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.maintenance.domain.SonyChannelMsg;
import com.ruoyi.maintenance.service.ISonyChannelMsgService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 自动回复消息Controller
 * 
 * @author Abel
 * @date 2023-02-01
 */
@RestController
@RequestMapping("/maintenance/channelMessage")
public class SonyChannelMsgController extends BaseController
{
    @Autowired
    private ISonyChannelMsgService sonyChannelMsgService;

    /**
     * 查询自动回复消息列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelMessage:list')")
    @GetMapping("/list")
    public TableDataInfo list(SonyChannelMsg sonyChannelMsg)
    {
        startPage();
        List<SonyChannelMsg> list = sonyChannelMsgService.selectSonyChannelMsgList(sonyChannelMsg);
        return getDataTable(list);
    }

    /**
     * 导出自动回复消息列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelMessage:export')")
    @Log(title = "自动回复消息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SonyChannelMsg sonyChannelMsg)
    {
        List<SonyChannelMsg> list = sonyChannelMsgService.selectSonyChannelMsgList(sonyChannelMsg);
        ExcelUtil<SonyChannelMsg> util = new ExcelUtil<SonyChannelMsg>(SonyChannelMsg.class);
        util.exportExcel(response, list, "自动回复消息数据");
    }

    /**
     * 获取自动回复消息详细信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelMessage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sonyChannelMsgService.selectSonyChannelMsgById(id));
    }

    /**
     * 新增自动回复消息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelMessage:add')")
    @Log(title = "自动回复消息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SonyChannelMsg sonyChannelMsg)
    {
        return toAjax(sonyChannelMsgService.insertSonyChannelMsg(sonyChannelMsg));
    }

    /**
     * 修改自动回复消息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelMessage:edit')")
    @Log(title = "自动回复消息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SonyChannelMsg sonyChannelMsg)
    {
        return toAjax(sonyChannelMsgService.updateSonyChannelMsg(sonyChannelMsg));
    }

    /**
     * 删除自动回复消息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelMessage:remove')")
    @Log(title = "自动回复消息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sonyChannelMsgService.deleteSonyChannelMsgByIds(ids));
    }
}
