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
import com.ruoyi.maintenance.domain.SonyChannelConsumerInfo;
import com.ruoyi.maintenance.service.ISonyChannelConsumerInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客户渠道信息Controller
 * 
 * @author Abel
 * @date 2023-02-01
 */
@RestController
@RequestMapping("/maintenance/channelConsumerInfo")
public class SonyChannelConsumerInfoController extends BaseController
{
    @Autowired
    private ISonyChannelConsumerInfoService sonyChannelConsumerInfoService;

    /**
     * 查询客户渠道信息列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelConsumerInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(SonyChannelConsumerInfo sonyChannelConsumerInfo)
    {
        startPage();
        List<SonyChannelConsumerInfo> list = sonyChannelConsumerInfoService.selectSonyChannelConsumerInfoList(sonyChannelConsumerInfo);
        return getDataTable(list);
    }

    /**
     * 导出客户渠道信息列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelConsumerInfo:export')")
    @Log(title = "客户渠道信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SonyChannelConsumerInfo sonyChannelConsumerInfo)
    {
        List<SonyChannelConsumerInfo> list = sonyChannelConsumerInfoService.selectSonyChannelConsumerInfoList(sonyChannelConsumerInfo);
        ExcelUtil<SonyChannelConsumerInfo> util = new ExcelUtil<SonyChannelConsumerInfo>(SonyChannelConsumerInfo.class);
        util.exportExcel(response, list, "客户渠道信息数据");
    }

    /**
     * 获取客户渠道信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelConsumerInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sonyChannelConsumerInfoService.selectSonyChannelConsumerInfoById(id));
    }

    /**
     * 新增客户渠道信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelConsumerInfo:add')")
    @Log(title = "客户渠道信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SonyChannelConsumerInfo sonyChannelConsumerInfo)
    {
        return toAjax(sonyChannelConsumerInfoService.insertSonyChannelConsumerInfo(sonyChannelConsumerInfo));
    }

    /**
     * 修改客户渠道信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelConsumerInfo:edit')")
    @Log(title = "客户渠道信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SonyChannelConsumerInfo sonyChannelConsumerInfo)
    {
        return toAjax(sonyChannelConsumerInfoService.updateSonyChannelConsumerInfo(sonyChannelConsumerInfo));
    }

    /**
     * 删除客户渠道信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelConsumerInfo:remove')")
    @Log(title = "客户渠道信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sonyChannelConsumerInfoService.deleteSonyChannelConsumerInfoByIds(ids));
    }
}
