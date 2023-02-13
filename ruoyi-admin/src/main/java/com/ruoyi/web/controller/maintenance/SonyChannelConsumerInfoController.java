package com.ruoyi.web.controller.maintenance;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.maintenance.wechat.util.FileUtils;
import com.ruoyi.maintenance.domain.SonyChannelConsumerInfo;
import com.ruoyi.maintenance.domain.dto.SonyChannelConsumerInfoDTO;
import com.ruoyi.maintenance.domain.excel.SonyChannelConsumerInfoExportVO;
import com.ruoyi.maintenance.domain.vo.SonyChannelConsumerInfoVO;
import com.ruoyi.maintenance.service.ISonyChannelConsumerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
    @PostMapping("/list")
    public TableDataInfo list(@RequestBody SonyChannelConsumerInfoDTO dto)
    {
        startPage();
        List<SonyChannelConsumerInfoVO> list = sonyChannelConsumerInfoService
                .selectSonyChannelConsumerInfoListByDTO(dto);
        return getDataTable(list);
    }

    /**
     * 导出客户渠道信息列表
     */
    @PreAuthorize("@ss.hasPermi('maintenance:channelConsumerInfo:export')")
    @Log(title = "客户渠道信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, @RequestBody(required = false) List<Integer> ids)
    {
        List<SonyChannelConsumerInfoExportVO> list = sonyChannelConsumerInfoService.selectSonyChannelConsumerInfoListByIds(ids);
        try {
            FileUtils.export(response, "客户渠道信息", list, SonyChannelConsumerInfoExportVO.class);
        } catch (Exception e) {
            logger.error("导出客户渠道信息表时出错", e);
            throw new ServiceException("导出客户渠道信息出错", HttpStatus.ERROR);
        }
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
