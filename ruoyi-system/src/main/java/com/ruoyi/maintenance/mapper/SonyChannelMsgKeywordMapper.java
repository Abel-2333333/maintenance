package com.ruoyi.maintenance.mapper;

import com.ruoyi.maintenance.domain.SonyChannelMsgKeyword;

import java.util.List;

/**
 * 自动回复关键词Mapper接口
 * 
 * @author Abel
 * @date 2023-02-01
 */
public interface SonyChannelMsgKeywordMapper 
{
    /**
     * 查询自动回复关键词
     * 
     * @param id 自动回复关键词主键
     * @return 自动回复关键词
     */
    public SonyChannelMsgKeyword selectSonyChannelMsgKeywordById(Long id);

    /**
     * 查询自动回复关键词列表
     * 
     * @param sonyChannelMsgKeyword 自动回复关键词
     * @return 自动回复关键词集合
     */
    public List<SonyChannelMsgKeyword> selectSonyChannelMsgKeywordList(SonyChannelMsgKeyword sonyChannelMsgKeyword);

    /**
     * 新增自动回复关键词
     * 
     * @param sonyChannelMsgKeyword 自动回复关键词
     * @return 结果
     */
    public int insertSonyChannelMsgKeyword(SonyChannelMsgKeyword sonyChannelMsgKeyword);

    /**
     * 修改自动回复关键词
     * 
     * @param sonyChannelMsgKeyword 自动回复关键词
     * @return 结果
     */
    public int updateSonyChannelMsgKeyword(SonyChannelMsgKeyword sonyChannelMsgKeyword);

    /**
     * 删除自动回复关键词
     * 
     * @param id 自动回复关键词主键
     * @return 结果
     */
    public int deleteSonyChannelMsgKeywordById(Long id);

    /**
     * 批量删除自动回复关键词
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSonyChannelMsgKeywordByIds(Long[] ids);
}
