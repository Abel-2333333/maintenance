package com.ruoyi.maintenance.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.maintenance.domain.SonyChannelConsumerInfo;
import com.ruoyi.maintenance.domain.dto.SonyChannelConsumerInfoDTO;
import com.ruoyi.maintenance.domain.excel.SonyChannelConsumerInfoExportVO;
import com.ruoyi.maintenance.domain.vo.SonyChannelConsumerInfoVO;
import com.ruoyi.maintenance.mapper.SonyChannelConsumerInfoMapper;
import com.ruoyi.maintenance.service.ISonyChannelConsumerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客户渠道信息Service业务层处理
 * 
 * @author Abel
 * @date 2023-02-01
 */
@Service
public class SonyChannelConsumerInfoServiceImpl implements ISonyChannelConsumerInfoService 
{
    @Autowired
    private SonyChannelConsumerInfoMapper sonyChannelConsumerInfoMapper;

    /**
     * 查询客户渠道信息
     * 
     * @param id 客户渠道信息主键
     * @return 客户渠道信息
     */
    @Override
    public SonyChannelConsumerInfo selectSonyChannelConsumerInfoById(Long id)
    {
        return sonyChannelConsumerInfoMapper.selectSonyChannelConsumerInfoById(id);
    }

    /**
     * 查询客户渠道信息列表
     * 
     * @param sonyChannelConsumerInfo 客户渠道信息
     * @return 客户渠道信息
     */
    @Override
    public List<SonyChannelConsumerInfo> selectSonyChannelConsumerInfoList(SonyChannelConsumerInfo sonyChannelConsumerInfo)
    {
        return sonyChannelConsumerInfoMapper.selectSonyChannelConsumerInfoList(sonyChannelConsumerInfo);
    }

    /**
     * 新增客户渠道信息
     * 
     * @param sonyChannelConsumerInfo 客户渠道信息
     * @return 结果
     */
    @Override
    public int insertSonyChannelConsumerInfo(SonyChannelConsumerInfo sonyChannelConsumerInfo)
    {
        sonyChannelConsumerInfo.setCreateTime(DateUtils.getNowDate());
        return sonyChannelConsumerInfoMapper.insertSonyChannelConsumerInfo(sonyChannelConsumerInfo);
    }

    /**
     * 修改客户渠道信息
     * 
     * @param sonyChannelConsumerInfo 客户渠道信息
     * @return 结果
     */
    @Override
    public int updateSonyChannelConsumerInfo(SonyChannelConsumerInfo sonyChannelConsumerInfo)
    {
        return sonyChannelConsumerInfoMapper.updateSonyChannelConsumerInfo(sonyChannelConsumerInfo);
    }

    /**
     * 批量删除客户渠道信息
     * 
     * @param ids 需要删除的客户渠道信息主键
     * @return 结果
     */
    @Override
    public int deleteSonyChannelConsumerInfoByIds(Long[] ids)
    {
        return sonyChannelConsumerInfoMapper.deleteSonyChannelConsumerInfoByIds(ids);
    }

    /**
     * 删除客户渠道信息信息
     * 
     * @param id 客户渠道信息主键
     * @return 结果
     */
    @Override
    public int deleteSonyChannelConsumerInfoById(Long id)
    {
        return sonyChannelConsumerInfoMapper.deleteSonyChannelConsumerInfoById(id);
    }
	
	@Override
	public List<SonyChannelConsumerInfoVO> selectSonyChannelConsumerInfoListByDTO(SonyChannelConsumerInfoDTO dto) {
        return  sonyChannelConsumerInfoMapper.selectSonyChannelConsumerInfoListByDTO(dto);
	}
	
	@Override
	public List<SonyChannelConsumerInfoExportVO> selectSonyChannelConsumerInfoListByIds(List<Integer> ids) {
        return sonyChannelConsumerInfoMapper.selectSonyChannelConsumerInfoListByIds(ids);
	}
}
