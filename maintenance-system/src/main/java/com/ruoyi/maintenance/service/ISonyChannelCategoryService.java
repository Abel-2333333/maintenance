package com.ruoyi.maintenance.service;

import com.ruoyi.maintenance.domain.SonyChannelCategory;
import com.ruoyi.maintenance.domain.dto.SonyChannelCategoryDTO;
import com.ruoyi.maintenance.domain.excel.SonyChannelCategoryExportVO;
import com.ruoyi.maintenance.domain.vo.SonyChannelCategoryIndexVO;
import com.ruoyi.maintenance.domain.vo.SonyChannelCategoryVO;

import java.util.List;

/**
 * 渠道关系Service接口
 * 
 * @author Abel
 * @date 2023-02-07
 */
public interface ISonyChannelCategoryService 
{
    /**
     * 查询渠道关系
     * 
     * @param id 渠道关系主键
     * @return 渠道关系
     */
    public SonyChannelCategory selectSonyChannelCategoryById(Integer id);

    /**
     * 查询渠道关系列表
     * 
     * @param sonyChannelCategory 渠道关系
     * @return 渠道关系集合
     */
    public List<SonyChannelCategory> selectSonyChannelCategoryList(SonyChannelCategory sonyChannelCategory);

    /**
     * 新增渠道关系
     * 
     * @param sonyChannelCategory 渠道关系
     * @return 结果
     */
    public void insertSonyChannelCategory(SonyChannelCategory sonyChannelCategory);

    /**
     * 修改渠道关系
     * 
     * @param sonyChannelCategory 渠道关系
     * @return 结果
     */
    public int updateSonyChannelCategory(SonyChannelCategory sonyChannelCategory);

    /**
     * 批量删除渠道关系
     * 
     * @param ids 需要删除的渠道关系主键集合
     * @return 结果
     */
    public int deleteSonyChannelCategoryByIds(List<Integer> ids);

    /**
     * 删除渠道关系信息
     * 
     * @param id 渠道关系主键
     * @return 结果
     */
    public int deleteSonyChannelCategoryById(Integer id);

    List<SonyChannelCategoryVO> selectChannelList(SonyChannelCategory sonyChannelCategory);

    List<SonyChannelCategoryVO> selectSecondaryChannelList(SonyChannelCategory sonyChannelCategory);
	
	void deleteSonyChannelCategoryByChannelName(String channelName);
    
    List<SonyChannelCategoryVO> selectChannelListByChannelId ( Integer id);
    
    List<SonyChannelCategoryIndexVO> selectChannelCategoryListByChannelCategoryDTO(SonyChannelCategoryDTO dto);

    void checkChannelName(SonyChannelCategoryDTO sonyChannelCategoryDTO);

    SonyChannelCategoryDTO checkChannelNameByName(SonyChannelCategoryDTO sonyChannelCategoryDTO);
    
    SonyChannelCategoryDTO getSonyChannelCategoryDTO(String primaryChannel, String secondaryChannel);

    List<SonyChannelCategoryExportVO> selectSonyChannelCategoryIndexVOByIds(List<Integer> ids);
    
    List<SonyChannelCategory> selectSonyChannelCategoryByIds(List<Integer> idList);

    void batchInsert ( List <SonyChannelCategory> sonyChannelCategoryList );

    void batchUpdate ( List <SonyChannelCategory> sonyChannelCategoryList );
}
