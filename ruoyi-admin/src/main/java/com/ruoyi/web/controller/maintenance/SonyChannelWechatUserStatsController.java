package com.ruoyi.web.controller.maintenance;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.maintenance.domain.SonyChannelWechatUserStats;
import com.ruoyi.maintenance.service.ISonyChannelWechatUserStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户数据统计Controller
 * 
 * @author Abel
 * @date 2023-02-01
 */
@RestController
@RequestMapping("/maintenance/channelWechatUserStats")
public class SonyChannelWechatUserStatsController extends BaseController
{
    @Autowired
    private ISonyChannelWechatUserStatsService sonyChannelWechatUserStatsService;

    /**
     * 查询用户数据统计列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelWechatUserStats:list')")
    @GetMapping("/list")
    public TableDataInfo list(SonyChannelWechatUserStats sonyChannelWechatUserStats)
    {
        startPage();
        List<SonyChannelWechatUserStats> list = sonyChannelWechatUserStatsService.selectSonyChannelWechatUserStatsList(sonyChannelWechatUserStats);
        return getDataTable(list);
    }

    /**
     * 导出用户数据统计列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelWechatUserStats:export')")
    @Log(title = "用户数据统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SonyChannelWechatUserStats sonyChannelWechatUserStats)
    {
        List<SonyChannelWechatUserStats> list = sonyChannelWechatUserStatsService.selectSonyChannelWechatUserStatsList(sonyChannelWechatUserStats);
        ExcelUtil<SonyChannelWechatUserStats> util = new ExcelUtil<SonyChannelWechatUserStats>(SonyChannelWechatUserStats.class);
        util.exportExcel(response, list, "用户数据统计数据");
    }

    /**
     * 获取用户数据统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelWechatUserStats:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sonyChannelWechatUserStatsService.selectSonyChannelWechatUserStatsById(id));
    }

    /**
     * 新增用户数据统计
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelWechatUserStats:add')")
    @Log(title = "用户数据统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SonyChannelWechatUserStats sonyChannelWechatUserStats)
    {
        return toAjax(sonyChannelWechatUserStatsService.insertSonyChannelWechatUserStats(sonyChannelWechatUserStats));
    }

    /**
     * 修改用户数据统计
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelWechatUserStats:edit')")
    @Log(title = "用户数据统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SonyChannelWechatUserStats sonyChannelWechatUserStats)
    {
        return toAjax(sonyChannelWechatUserStatsService.updateSonyChannelWechatUserStats(sonyChannelWechatUserStats));
    }

    /**
     * 删除用户数据统计
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelWechatUserStats:remove')")
    @Log(title = "用户数据统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sonyChannelWechatUserStatsService.deleteSonyChannelWechatUserStatsByIds(ids));
    }
}
