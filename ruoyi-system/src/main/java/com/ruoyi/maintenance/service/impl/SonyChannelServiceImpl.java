package com.ruoyi.maintenance.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.maintenance.domain.SonyChannel;
import com.ruoyi.maintenance.domain.dto.SonyChannelDTO;
import com.ruoyi.maintenance.domain.excel.SonyChannelExcelVO;
import com.ruoyi.maintenance.domain.vo.SonyChannelVO;
import com.ruoyi.maintenance.mapper.SonyChannelMapper;
import com.ruoyi.maintenance.service.ISonyChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 渠道信息Service业务层处理
 * 
 * @author Abel
 * @date 2023-02-01
 */
@Service
public class SonyChannelServiceImpl implements ISonyChannelService 
{
    @Autowired
    private SonyChannelMapper sonyChannelMapper;

    /**
     * 查询渠道信息
     * 
     * @param id 渠道信息主键
     * @return 渠道信息
     */
    @Override
    public SonyChannel selectSonyChannelById(Long id)
    {
        return sonyChannelMapper.selectSonyChannelById(id);
    }

    @Override
    public List<SonyChannel> selectSonyChannelList(SonyChannel sonyChannel) {
        return sonyChannelMapper.selectSonyChannelList(sonyChannel);
    }

    /**
     * 查询渠道信息列表
     * 
     * @param sonyChannel 渠道信息
     * @return 渠道信息
     */
    @Override
    public List<SonyChannelVO> selectSonyChannelListByDTO(SonyChannelDTO sonyChannel)
    {
        return sonyChannelMapper.selectSonyChannelListByDTO(sonyChannel);
    }

    /**
     * 新增渠道信息
     * 
     * @param sonyChannel 渠道信息
     * @return 结果
     */
    @Override
    public int insertSonyChannel(SonyChannel sonyChannel)
    {
        sonyChannel.setCreateTime(DateUtils.getNowDate());
        return sonyChannelMapper.insertSonyChannel(sonyChannel);
    }

    /**
     * 修改渠道信息
     * 
     * @param sonyChannel 渠道信息
     * @return 结果
     */
    @Override
    public int updateSonyChannel(SonyChannel sonyChannel)
    {
        sonyChannel.setUpdateTime(DateUtils.getNowDate());
        return sonyChannelMapper.updateSonyChannel(sonyChannel);
    }

    /**
     * 批量删除渠道信息
     * 
     * @param ids 需要删除的渠道信息主键
     * @return 结果
     */
    @Override
    public int deleteSonyChannelByIds(Long[] ids)
    {
        return sonyChannelMapper.deleteSonyChannelByIds(ids);
    }

    /**
     * 删除渠道信息信息
     * 
     * @param id 渠道信息主键
     * @return 结果
     */
    @Override
    public int deleteSonyChannelById(Long id)
    {
        return sonyChannelMapper.deleteSonyChannelById(id);
    }

    @Override
    public List<SonyChannelExcelVO> selectSonyChannelListByIds(List<Integer> ids) {
        return sonyChannelMapper.selectSonyChannelListByIds(ids);
    }
	
	@Override
	public List<SonyChannel> selectSonyChannelByIds(Long[] ids) {
        return sonyChannelMapper.selectSonyChannelByIds(ids);
	}
    
    @Override
    public int batchUpdateSonyChannel(Long[] ids) {
         return sonyChannelMapper.batchUpdate(ids);
    }
}
