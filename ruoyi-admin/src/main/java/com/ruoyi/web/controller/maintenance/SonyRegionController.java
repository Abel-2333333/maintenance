package com.ruoyi.web.controller.maintenance;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.maintenance.domain.SonyRegion;
import com.ruoyi.maintenance.service.ISonyRegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 省市关系Controller
 * 
 * @author Abel
 * @date 2023-02-01
 */
@RestController
@RequestMapping("/maintenance/region")
public class SonyRegionController extends BaseController
{
    @Autowired
    private ISonyRegionService sonyRegionService;

    /**
     * 查询省市关系列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:region:list')")
    @GetMapping("/list")
    public AjaxResult list(SonyRegion sonyRegion)
    {
        List<SonyRegion> list = sonyRegionService.selectSonyRegionList(sonyRegion);
        Map<String, List<SonyRegion>> parentIdToSonyRegionMap = list.stream().collect(Collectors.groupingBy(SonyRegion::getRegionParentId));
        list.forEach(region -> {
            String parentRegionId = region.getRegionId();
            List<SonyRegion> children = parentIdToSonyRegionMap.get(parentRegionId);
            region.setChildren(children);
        });
        return success(list);
    }

    /**
     * 查询所有省
     */
    @GetMapping("/province")
    public AjaxResult getProvinces() {
        SonyRegion sonyRegion = new SonyRegion();
        sonyRegion.setRegionParentId("-1");
        List<SonyRegion> sonyRegionList = sonyRegionService.selectSonyRegionList(sonyRegion);
        return success(sonyRegionList);
    }

    /**
     * 根据regionId查省下面的子级辖区
     */
    @GetMapping("city")
    public AjaxResult getCities(String regionId) {
        return success(sonyRegionService.selectSonyRegionListByParentRegionId(regionId));
    }

    /**
     * 导出省市关系列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:region:export')")
    @Log(title = "省市关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SonyRegion sonyRegion)
    {
        List<SonyRegion> list = sonyRegionService.selectSonyRegionList(sonyRegion);
        ExcelUtil<SonyRegion> util = new ExcelUtil<SonyRegion>(SonyRegion.class);
        util.exportExcel(response, list, "省市关系数据");
    }

    /**
     * 获取省市关系详细信息
     */
    @PreAuthorize("@ss.hasPermi('maintenance:region:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sonyRegionService.selectSonyRegionById(id));
    }

    /**
     * 新增省市关系
     */
    @PreAuthorize("@ss.hasPermi('maintenance:region:add')")
    @Log(title = "省市关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SonyRegion sonyRegion)
    {
        return toAjax(sonyRegionService.insertSonyRegion(sonyRegion));
    }

    /**
     * 修改省市关系
     */
    @PreAuthorize("@ss.hasPermi('maintenance:region:edit')")
    @Log(title = "省市关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SonyRegion sonyRegion)
    {
        return toAjax(sonyRegionService.updateSonyRegion(sonyRegion));
    }

    /**
     * 删除省市关系
     */
    @PreAuthorize("@ss.hasPermi('maintenance:region:remove')")
    @Log(title = "省市关系", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sonyRegionService.deleteSonyRegionByIds(ids));
    }
}
