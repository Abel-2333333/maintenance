package com.ruoyi.maintenance.mapper;

import com.ruoyi.maintenance.domain.SonyChannel;
import com.ruoyi.maintenance.domain.dto.SonyChannelDTO;
import com.ruoyi.maintenance.domain.excel.SonyChannelExcelVO;
import com.ruoyi.maintenance.domain.excel.SonyChannelImportVO;
import com.ruoyi.maintenance.domain.vo.SonyChannelVO;

import java.util.List;

/**
 * 渠道信息Mapper接口
 * 
 * @author Abel
 * @date 2023-02-03
 */
public interface SonyChannelMapper 
{
    /**
     * 查询渠道信息
     * 
     * @param id 渠道信息主键
     * @return 渠道信息
     */
    public SonyChannel selectSonyChannelById(Long id);

    /**
     * 查询渠道信息列表
     * 
     * @param sonyChannel 渠道信息
     * @return 渠道信息集合
     */
    public List<SonyChannel> selectSonyChannelList(SonyChannel sonyChannel);

    List<SonyChannelVO> selectSonyChannelListByDTO(SonyChannelDTO sonyChannelDTO);

    /**
     * 新增渠道信息
     * 
     * @param sonyChannel 渠道信息
     * @return 结果
     */
    public int insertSonyChannel(SonyChannel sonyChannel);

    /**
     * 修改渠道信息
     * 
     * @param sonyChannel 渠道信息
     * @return 结果
     */
    public int updateSonyChannel(SonyChannel sonyChannel);

    /**
     * 删除渠道信息
     * 
     * @param id 渠道信息主键
     * @return 结果
     */
    public int deleteSonyChannelById(Long id);

    /**
     * 批量删除渠道信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSonyChannelByIds(Long[] ids);

    /**
     * @param ids 带查询渠道id
     * @return 匹配的结果
     */
    List<SonyChannelExcelVO> selectSonyChannelListByIds(List<Integer> ids);
	
	List<SonyChannel> selectSonyChannelByIds(List<Long> ids);
    
    int batchDelete(List<Long>  ids);

    void batchUpdateSonyChannel(List<SonyChannelImportVO> cachedDataList);

    void batchInsert(List<SonyChannelImportVO> cachedDataList);
}
