package com.ruoyi.web.controller.maintenance;

import com.alibaba.excel.EasyExcelFactory;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.maintenance.domain.SonyChannelCategory;
import com.ruoyi.maintenance.domain.dto.SonyChannelCategoryDTO;
import com.ruoyi.maintenance.domain.excel.SonyChannelCategoryExportVO;
import com.ruoyi.maintenance.domain.excel.SonyChannelCategoryImportVO;
import com.ruoyi.maintenance.domain.listener.SonyChannelCategoryListener;
import com.ruoyi.maintenance.domain.vo.SonyChannelCategoryIndexVO;
import com.ruoyi.maintenance.domain.vo.SonyChannelCategoryVO;
import com.ruoyi.maintenance.service.ISonyChannelCategoryService;
import com.ruoyi.maintenance.wechat.util.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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
	private final ISonyChannelCategoryService sonyChannelCategoryService;

	private final ThreadPoolTaskExecutor executor;
	
	/**
	 * 查询渠道关系列表
	 */
//	@PreAuthorize("@ss.hasPermi('maintenance:channelCategory:list')")
//	@GetMapping("/list")
//	public TableDataInfo list(SonyChannelCategory sonyChannelCategory) {
//		startPage();
//		List<SonyChannelCategory> list = sonyChannelCategoryService.selectSonyChannelCategoryList(sonyChannelCategory);
//		return getDataTable(list);
//	}
	
	/**
	 * 获取所有一级渠道
	 */
	@PostMapping("/primary/all")
	public AjaxResult getAllPrimaryChannel() {
		SonyChannelCategory sonyChannelCategory = new SonyChannelCategory();
		sonyChannelCategory.setParentId(-1);
		List<SonyChannelCategoryVO> list = sonyChannelCategoryService.selectChannelList(sonyChannelCategory);
		return success(list);
	}
	
	/**
	 * 分页查询一级渠道或根据channelName查询
	 */
	@PostMapping("/primary/list")
	public TableDataInfo getPrimaryChannel(@RequestBody(required = false) Integer id) {
		startPage();
		List<SonyChannelCategoryVO> list = sonyChannelCategoryService.selectChannelListByChannelId (id);
		return getDataTable(list);
	}
	
	/**
	 * 查询所有二级渠道或一级渠道下所有二级渠道
	 */
	@PostMapping("/secondary/all")
	public AjaxResult getAllSecondaryChannel(@RequestBody(required = false) Integer id) {
		SonyChannelCategory sonyChannelCategory = new SonyChannelCategory();
		sonyChannelCategory.setParentId(id);
		List<SonyChannelCategoryVO> list =
				sonyChannelCategoryService.selectSecondaryChannelList(sonyChannelCategory);
		return success(list);
	}
	
	/**
	 * 分页查询二级渠道
	 */
	@PostMapping("/secondary/list")
	public TableDataInfo getSecondaryChannel( @RequestBody SonyChannelCategoryDTO dto) {
		startPage();
		List<SonyChannelCategoryIndexVO> list =
				sonyChannelCategoryService.selectChannelCategoryListByChannelCategoryDTO(dto);
		return getDataTable(list);
	}

	/**
	 * 新增渠道关系
	 */
	@PreAuthorize("@ss.hasPermi('maintenance:channelCategory:add')")
	@Log(title = "渠道关系", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	public AjaxResult add(@RequestBody SonyChannelCategory sonyChannelCategory) {
		sonyChannelCategoryService.insertSonyChannelCategory(sonyChannelCategory);
		return success();
	}
	
	/**
	 * 根据渠道id删除渠道
	 */
	@PostMapping("/remove")
	public AjaxResult deletePrimaryChannel(@RequestBody List<Integer> ids) {
		sonyChannelCategoryService.deleteSonyChannelCategoryByIds(ids);
		return success();
	}
	
	/**
	 * 获取渠道关系详细信息
	 */
	@PreAuthorize("@ss.hasPermi('maintenance:channelCategory:query')")
	@GetMapping(value = "/{id}")
	public AjaxResult getInfo(@PathVariable("id") Integer id) {
		return success(sonyChannelCategoryService.selectSonyChannelCategoryById(id));
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
	 * 导出渠道关系列表
	 */
	@PreAuthorize("@ss.hasPermi('maintenance:channelCategory:export')")
	@Log(title = "渠道关系", businessType = BusinessType.EXPORT)
	@PostMapping("/export")
	public void export(HttpServletResponse response, @RequestBody(required = false) List<Integer> ids)  {
		List<SonyChannelCategoryExportVO> list = sonyChannelCategoryService.selectSonyChannelCategoryIndexVOByIds(ids);
		try {
			FileUtils.export(response, "渠道维护", list);
		} catch (Exception e) {
			logger.error("导出渠道关系表时出错", e);
			throw new ServiceException("导出渠道关系表出错", HttpStatus.ERROR);
		}
	}

	/**
	 * 批量导入
	 */
	@PreAuthorize ( "@ss.hasPermi('maintenance:channelCategory:import')" )
	@Log ( title = "渠道关系", businessType = BusinessType.IMPORT )
	@PostMapping ( "/import" )
	public AjaxResult upload ( @RequestParam ( "file" ) MultipartFile file ) throws IOException {
		List <SonyChannelCategoryVO> allSonyChannelCategories = sonyChannelCategoryService.selectChannelList ( new SonyChannelCategory () );
		// 一级渠道channelName:VO的Map
		Map <String, SonyChannelCategoryVO> primaryChannelToVOMap = allSonyChannelCategories.stream ()
				.filter ( e -> e.getParentId () == -1 )
				.collect ( Collectors.toMap ( SonyChannelCategoryVO::getChannelName, Function.identity () ) );
		// 一级渠道id:VO的Map
		Map <Integer, SonyChannelCategoryVO> parentIdToVOMap = allSonyChannelCategories.stream ()
				.filter ( e -> e.getParentId () == -1 )
				.collect ( Collectors.toMap ( SonyChannelCategoryVO::getId, Function.identity () ) );
		// 二级渠道channelName:VO的Map
		Map <String, SonyChannelCategoryVO> secondaryChannelToVOMap = allSonyChannelCategories.stream ()
				.filter ( e -> e.getParentId () != -1 )
				.collect ( Collectors.toMap ( SonyChannelCategoryVO::getChannelName, Function.identity () ) );
		// 二级渠道id:VO的Map
		Map <Integer, SonyChannelCategoryVO> secondaryIdToVOMap = allSonyChannelCategories.stream ()
				.filter ( e -> e.getParentId () != -1 )
				.collect ( Collectors.toMap ( SonyChannelCategoryVO::getId, Function.identity () ) );

		EasyExcelFactory.read ( file.getInputStream (), SonyChannelCategoryImportVO.class,
				new SonyChannelCategoryListener ( sonyChannelCategoryService, executor,
						primaryChannelToVOMap, secondaryChannelToVOMap, secondaryIdToVOMap, parentIdToVOMap ) )
				.sheet ().doRead ();
		return success ();
	}
}
