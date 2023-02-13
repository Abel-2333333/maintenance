package com.ruoyi.maintenance.service.impl;

import com.ruoyi.maintenance.domain.SonyChannelMsgKeyword;
import com.ruoyi.maintenance.mapper.SonyChannelMsgKeywordMapper;
import com.ruoyi.maintenance.service.ISonyChannelMsgKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 自动回复关键词Service业务层处理
 * 
 * @author Abel
 * @date 2023-02-01
 */
@Service
public class SonyChannelMsgKeywordServiceImpl implements ISonyChannelMsgKeywordService 
{
    @Autowired
    private SonyChannelMsgKeywordMapper sonyChannelMsgKeywordMapper;

    /**
     * 查询自动回复关键词
     * 
     * @param id 自动回复关键词主键
     * @return 自动回复关键词
     */
    @Override
    public SonyChannelMsgKeyword selectSonyChannelMsgKeywordById(Long id)
    {
        return sonyChannelMsgKeywordMapper.selectSonyChannelMsgKeywordById(id);
    }

    /**
     * 查询自动回复关键词列表
     * 
     * @param sonyChannelMsgKeyword 自动回复关键词
     * @return 自动回复关键词
     */
    @Override
    public List<SonyChannelMsgKeyword> selectSonyChannelMsgKeywordList(SonyChannelMsgKeyword sonyChannelMsgKeyword)
    {
        return sonyChannelMsgKeywordMapper.selectSonyChannelMsgKeywordList(sonyChannelMsgKeyword);
    }

    /**
     * 新增自动回复关键词
     * 
     * @param sonyChannelMsgKeyword 自动回复关键词
     * @return 结果
     */
    @Override
    public int insertSonyChannelMsgKeyword(SonyChannelMsgKeyword sonyChannelMsgKeyword)
    {
        return sonyChannelMsgKeywordMapper.insertSonyChannelMsgKeyword(sonyChannelMsgKeyword);
    }

    /**
     * 修改自动回复关键词
     * 
     * @param sonyChannelMsgKeyword 自动回复关键词
     * @return 结果
     */
    @Override
    public int updateSonyChannelMsgKeyword(SonyChannelMsgKeyword sonyChannelMsgKeyword)
    {
        return sonyChannelMsgKeywordMapper.updateSonyChannelMsgKeyword(sonyChannelMsgKeyword);
    }

    /**
     * 批量删除自动回复关键词
     * 
     * @param ids 需要删除的自动回复关键词主键
     * @return 结果
     */
    @Override
    public int deleteSonyChannelMsgKeywordByIds(Long[] ids)
    {
        return sonyChannelMsgKeywordMapper.deleteSonyChannelMsgKeywordByIds(ids);
    }

    /**
     * 删除自动回复关键词信息
     * 
     * @param id 自动回复关键词主键
     * @return 结果
     */
    @Override
    public int deleteSonyChannelMsgKeywordById(Long id)
    {
        return sonyChannelMsgKeywordMapper.deleteSonyChannelMsgKeywordById(id);
    }
}
