package com.ruoyi.web.controller.maintenance;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.maintenance.domain.SonyWechatUser;
import com.ruoyi.maintenance.service.ISonyWechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 微信用户基本信息Controller
 * 
 * @author Abel
 * @date 2023-02-01
 */
@RestController
@RequestMapping("/maintenance/wechatMaintenance")
public class SonyWechatUserController extends BaseController
{
    @Autowired
    private ISonyWechatUserService sonyWechatUserService;

    /**
     * 查询微信用户基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:wechatMaintenance:list')")
    @GetMapping("/list")
    public TableDataInfo list(SonyWechatUser sonyWechatUser)
    {
        startPage();
        List<SonyWechatUser> list = sonyWechatUserService.selectSonyWechatUserList(sonyWechatUser);
        return getDataTable(list);
    }

    /**
     * 导出微信用户基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:wechatMaintenance:export')")
    @Log(title = "微信用户基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SonyWechatUser sonyWechatUser)
    {
        List<SonyWechatUser> list = sonyWechatUserService.selectSonyWechatUserList(sonyWechatUser);
        ExcelUtil<SonyWechatUser> util = new ExcelUtil<SonyWechatUser>(SonyWechatUser.class);
        util.exportExcel(response, list, "微信用户基本信息数据");
    }

    /**
     * 获取微信用户基本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:wechatMaintenance:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sonyWechatUserService.selectSonyWechatUserById(id));
    }

    /**
     * 新增微信用户基本信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:wechatMaintenance:add')")
    @Log(title = "微信用户基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SonyWechatUser sonyWechatUser)
    {
        return toAjax(sonyWechatUserService.insertSonyWechatUser(sonyWechatUser));
    }

    /**
     * 修改微信用户基本信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:wechatMaintenance:edit')")
    @Log(title = "微信用户基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SonyWechatUser sonyWechatUser)
    {
        return toAjax(sonyWechatUserService.updateSonyWechatUser(sonyWechatUser));
    }

    /**
     * 删除微信用户基本信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:wechatMaintenance:remove')")
    @Log(title = "微信用户基本信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sonyWechatUserService.deleteSonyWechatUserByIds(ids));
    }
}
