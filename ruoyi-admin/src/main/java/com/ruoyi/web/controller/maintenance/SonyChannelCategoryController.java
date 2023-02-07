package com.ruoyi.web.controller.maintenance;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.maintenance.domain.SonyChannelCategory;
import com.ruoyi.maintenance.domain.vo.SonyChannelCategoryVO;
import com.ruoyi.maintenance.service.ISonyChannelCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 渠道关系Controller
 *
 * @author Abel
 * @date 2023-02-07
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/maintenance/category")
public class SonyChannelCategoryController extends BaseController {
    @Autowired
    private ISonyChannelCategoryService sonyChannelCategoryService;

    /**
     * 查询渠道关系列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelCategory:list')")
    @GetMapping("/list")
    public TableDataInfo list(SonyChannelCategory sonyChannelCategory) {
        startPage();
        List<SonyChannelCategory> list = sonyChannelCategoryService.selectSonyChannelCategoryList(sonyChannelCategory);
        return getDataTable(list);
    }

    /**
     * 查询一级渠道或根据parendId查询二级渠道
     */
    @GetMapping("/primary")
    public TableDataInfo getPrimaryChannel(@RequestParam(required = false) Integer parentId) {
        SonyChannelCategory sonyChannelCategory = new SonyChannelCategory();
        sonyChannelCategory.setParentId(parentId == null ? -1 : parentId);
        startPage();
        List<SonyChannelCategoryVO> list = sonyChannelCategoryService.selectChannelList(sonyChannelCategory);
        return getDataTable(list);
    }

    /**
     * 查询二级渠道
     */
    @GetMapping("/secondary")
    public TableDataInfo getSecondaryChannel() {
        SonyChannelCategory sonyChannelCategory = new SonyChannelCategory();
        startPage();
        List<SonyChannelCategoryVO> list = sonyChannelCategoryService.selectSecondaryChannelList(sonyChannelCategory);
        return getDataTable(list);
    }

    /**
     * 删除渠道
     */

    /**
     * 获取渠道关系详细信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelCategory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(sonyChannelCategoryService.selectSonyChannelCategoryById(id));
    }

    /**
     * 新增渠道关系
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelCategory:add')")
    @Log(title = "渠道关系", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody SonyChannelCategory sonyChannelCategory) {
        try {
            sonyChannelCategoryService.insertSonyChannelCategory(sonyChannelCategory);
        } catch (Exception e) {
            logger.error("{}重复", sonyChannelCategory.getChannelName());
            throw new ServiceException("渠道名重复");
        }
        return success();
    }

    /**
     * 修改渠道关系
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelCategory:edit')")
    @Log(title = "渠道关系", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult edit(@RequestBody SonyChannelCategory sonyChannelCategory) {
        return toAjax(sonyChannelCategoryService.updateSonyChannelCategory(sonyChannelCategory));
    }

    /**
     * 删除渠道关系
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelCategory:remove')")
    @Log(title = "渠道关系", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(sonyChannelCategoryService.deleteSonyChannelCategoryByIds(ids));
    }

    /**
     * 导出渠道关系列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelCategory:export')")
    @Log(title = "渠道关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SonyChannelCategory sonyChannelCategory) {
        List<SonyChannelCategory> list = sonyChannelCategoryService.selectSonyChannelCategoryList(sonyChannelCategory);
        ExcelUtil<SonyChannelCategory> util = new ExcelUtil<>(SonyChannelCategory.class);
        util.exportExcel(response, list, "渠道关系数据");
    }
}
