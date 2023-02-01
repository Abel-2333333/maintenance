package com.ruoyi.web.controller.maintenance;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.maintenance.domain.SonyChannel;
import com.ruoyi.maintenance.service.ISonyChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 渠道信息Controller
 * 
 * @author Abel
 * @date 2023-02-01
 */
@RestController
@RequestMapping("/maintenance/channel")
public class SonyChannelController extends BaseController
{
    @Autowired
    private ISonyChannelService sonyChannelService;

    /**
     * 查询渠道信息列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channel:list')")
    @GetMapping("/list")
    public TableDataInfo list(SonyChannel sonyChannel)
    {
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
    public void export(HttpServletResponse response, SonyChannel sonyChannel)
    {
        List<SonyChannel> list = sonyChannelService.selectSonyChannelList(sonyChannel);
        ExcelUtil<SonyChannel> util = new ExcelUtil<SonyChannel>(SonyChannel.class);
        util.exportExcel(response, list, "渠道信息数据");
    }

    /**
     * 获取渠道信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sonyChannelService.selectSonyChannelById(id));
    }

    /**
     * 新增渠道信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channel:add')")
    @Log(title = "渠道信息", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody SonyChannel sonyChannel)
    {
        sonyChannel.setChannelCode("ABC123");
        sonyChannel.setCreatedBy(getUsername());
        return toAjax(sonyChannelService.insertSonyChannel(sonyChannel));
    }

    /**
     * 修改渠道信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channel:edit')")
    @Log(title = "渠道信息", businessType = BusinessType.UPDATE)
    @PutMapping("update")
    public AjaxResult edit(@RequestBody SonyChannel sonyChannel)
    {
        sonyChannel.setUpdatedBy(getUsername());
        return toAjax(sonyChannelService.updateSonyChannel(sonyChannel));
    }

    /**
     * 删除渠道信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channel:remove')")
    @Log(title = "渠道信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sonyChannelService.deleteSonyChannelByIds(ids));
    }
}
