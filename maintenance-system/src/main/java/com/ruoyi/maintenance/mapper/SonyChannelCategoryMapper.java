package com.ruoyi.maintenance.mapper;

import com.ruoyi.maintenance.domain.SonyChannelCategory;
import com.ruoyi.maintenance.domain.dto.SonyChannelCategoryDTO;
import com.ruoyi.maintenance.domain.excel.SonyChannelCategoryExportVO;
import com.ruoyi.maintenance.domain.vo.SonyChannelCategoryIndexVO;
import com.ruoyi.maintenance.domain.vo.SonyChannelCategoryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 渠道关系Mapper接口
 * 
 * @author Abel
 * @date 2023-02-07
 */
public interface SonyChannelCategoryMapper 
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
    public int insertSonyChannelCategory(SonyChannelCategory sonyChannelCategory);

    /**
     * 修改渠道关系
     * 
     * @param sonyChannelCategory 渠道关系
     * @return 结果
     */
    public int updateSonyChannelCategory(SonyChannelCategory sonyChannelCategory);

    /**
     * 删除渠道关系
     * 
     * @param id 渠道关系主键
     * @return 结果
     */
    public int deleteSonyChannelCategoryById(Integer id);

    /**
     * 批量删除渠道关系
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSonyChannelCategoryByIds(List<Integer> ids);

    List<SonyChannelCategoryVO> selectChannelList(SonyChannelCategory sonyChannelCategory);

    List<SonyChannelCategoryVO> selectSecondaryChannelList(SonyChannelCategory sonyChannelCategory);
    
    void deleteSonyChannelCategoryByChannelName(String channelName);
    
    SonyChannelCategoryIndexVO selectSonyChannelCategoryIndexVOByChannelName(String channelName);
    
    List<SonyChannelCategory> selectChannelListByIds(List<Integer> ids);
    
    SonyChannelCategory selectSonyChannelCategoryByChannelName(@Param("channelName") String channelName);
    
    SonyChannelCategoryVO selectChannelCategoryByChannelName(@Param("channelName") String channelName);
    
    List<SonyChannelCategoryIndexVO> selectChannelCategoryListByChannelCategoryDTO(SonyChannelCategoryDTO dto);

    List<Integer> selectChannelListByParentId(List<Integer> ids);

    List<SonyChannelCategoryExportVO> selectSonyChannelCategoryIndexVOByIds(List<Integer> ids);

    void batchInsertSonyChannelCategory ( List<SonyChannelCategory> sonyChannelCategoryList );

    void batchUpdateSonyChannelCategory ( List<SonyChannelCategory> sonyChannelCategoryList );
}
