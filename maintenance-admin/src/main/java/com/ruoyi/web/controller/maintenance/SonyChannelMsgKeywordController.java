package com.ruoyi.web.controller.maintenance;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.maintenance.domain.SonyChannelMsgKeyword;
import com.ruoyi.maintenance.service.ISonyChannelMsgKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 自动回复关键词Controller
 * 
 * @author Abel
 * @date 2023-02-01
 */
@RestController
@RequestMapping("/maintenance/channelMsgKeyword")
public class SonyChannelMsgKeywordController extends BaseController
{
    @Autowired
    private ISonyChannelMsgKeywordService sonyChannelMsgKeywordService;

    /**
     * 查询自动回复关键词列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelMsgKeyword:list')")
    @GetMapping("/list")
    public TableDataInfo list(SonyChannelMsgKeyword sonyChannelMsgKeyword)
    {
        startPage();
        List<SonyChannelMsgKeyword> list = sonyChannelMsgKeywordService.selectSonyChannelMsgKeywordList(sonyChannelMsgKeyword);
        return getDataTable(list);
    }

    /**
     * 导出自动回复关键词列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelMsgKeyword:export')")
    @Log(title = "自动回复关键词", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SonyChannelMsgKeyword sonyChannelMsgKeyword)
    {
        List<SonyChannelMsgKeyword> list = sonyChannelMsgKeywordService.selectSonyChannelMsgKeywordList(sonyChannelMsgKeyword);
        ExcelUtil<SonyChannelMsgKeyword> util = new ExcelUtil<SonyChannelMsgKeyword>(SonyChannelMsgKeyword.class);
        util.exportExcel(response, list, "自动回复关键词数据");
    }

    /**
     * 获取自动回复关键词详细信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelMsgKeyword:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sonyChannelMsgKeywordService.selectSonyChannelMsgKeywordById(id));
    }

    /**
     * 新增自动回复关键词
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelMsgKeyword:add')")
    @Log(title = "自动回复关键词", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SonyChannelMsgKeyword sonyChannelMsgKeyword)
    {
        return toAjax(sonyChannelMsgKeywordService.insertSonyChannelMsgKeyword(sonyChannelMsgKeyword));
    }

    /**
     * 修改自动回复关键词
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelMsgKeyword:edit')")
    @Log(title = "自动回复关键词", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SonyChannelMsgKeyword sonyChannelMsgKeyword)
    {
        return toAjax(sonyChannelMsgKeywordService.updateSonyChannelMsgKeyword(sonyChannelMsgKeyword));
    }

    /**
     * 删除自动回复关键词
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelMsgKeyword:remove')")
    @Log(title = "自动回复关键词", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sonyChannelMsgKeywordService.deleteSonyChannelMsgKeywordByIds(ids));
    }
}
