package com.ruoyi.web.controller.maintenance;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.maintenance.domain.SonyChannelMsgReplyRule;
import com.ruoyi.maintenance.service.ISonyChannelMsgReplyRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 公众号回复规则Controller
 * 
 * @author Abel
 * @date 2023-02-01
 */
@RestController
@RequestMapping("/maintenance/channelMsgReplyRule")
public class SonyChannelMsgReplyRuleController extends BaseController
{
    @Autowired
    private ISonyChannelMsgReplyRuleService sonyChannelMsgReplyRuleService;

    /**
     * 查询公众号回复规则列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelMsgReplyRule:list')")
    @GetMapping("/list")
    public TableDataInfo list(SonyChannelMsgReplyRule sonyChannelMsgReplyRule)
    {
        startPage();
        List<SonyChannelMsgReplyRule> list = sonyChannelMsgReplyRuleService.selectSonyChannelMsgReplyRuleList(sonyChannelMsgReplyRule);
        return getDataTable(list);
    }

    /**
     * 导出公众号回复规则列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelMsgReplyRule:export')")
    @Log(title = "公众号回复规则", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SonyChannelMsgReplyRule sonyChannelMsgReplyRule)
    {
        List<SonyChannelMsgReplyRule> list = sonyChannelMsgReplyRuleService.selectSonyChannelMsgReplyRuleList(sonyChannelMsgReplyRule);
        ExcelUtil<SonyChannelMsgReplyRule> util = new ExcelUtil<SonyChannelMsgReplyRule>(SonyChannelMsgReplyRule.class);
        util.exportExcel(response, list, "公众号回复规则数据");
    }

    /**
     * 获取公众号回复规则详细信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelMsgReplyRule:query')")
    @GetMapping(value = "/{ruleId}")
    public AjaxResult getInfo(@PathVariable("ruleId") Long ruleId)
    {
        return success(sonyChannelMsgReplyRuleService.selectSonyChannelMsgReplyRuleByRuleId(ruleId));
    }

    /**
     * 新增公众号回复规则
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelMsgReplyRule:add')")
    @Log(title = "公众号回复规则", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SonyChannelMsgReplyRule sonyChannelMsgReplyRule)
    {
        return toAjax(sonyChannelMsgReplyRuleService.insertSonyChannelMsgReplyRule(sonyChannelMsgReplyRule));
    }

    /**
     * 修改公众号回复规则
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelMsgReplyRule:edit')")
    @Log(title = "公众号回复规则", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SonyChannelMsgReplyRule sonyChannelMsgReplyRule)
    {
        return toAjax(sonyChannelMsgReplyRuleService.updateSonyChannelMsgReplyRule(sonyChannelMsgReplyRule));
    }

    /**
     * 删除公众号回复规则
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelMsgReplyRule:remove')")
    @Log(title = "公众号回复规则", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ruleIds}")
    public AjaxResult remove(@PathVariable Long[] ruleIds)
    {
        return toAjax(sonyChannelMsgReplyRuleService.deleteSonyChannelMsgReplyRuleByRuleIds(ruleIds));
    }
}
