package com.ruoyi.maintenance.service;

import com.ruoyi.maintenance.domain.SonyChannelConsumerInfo;
import com.ruoyi.maintenance.domain.dto.SonyChannelConsumerInfoDTO;
import com.ruoyi.maintenance.domain.excel.SonyChannelConsumerInfoExportVO;
import com.ruoyi.maintenance.domain.vo.SonyChannelConsumerInfoVO;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import java.util.List;

/**
 * 客户渠道信息Service接口
 * 
 * @author Abel
 * @date 2023-02-01
 */
public interface ISonyChannelConsumerInfoService 
{
    /**
     * 查询客户渠道信息
     * 
     * @param id 客户渠道信息主键
     * @return 客户渠道信息
     */
    public SonyChannelConsumerInfo selectSonyChannelConsumerInfoById(Long id);

    /**
     * 查询客户渠道信息列表
     * 
     * @param sonyChannelConsumerInfo 客户渠道信息
     * @return 客户渠道信息集合
     */
    public List<SonyChannelConsumerInfo> selectSonyChannelConsumerInfoList(SonyChannelConsumerInfo sonyChannelConsumerInfo);

    /**
     * 新增客户渠道信息
     * 
     * @param sonyChannelConsumerInfo 客户渠道信息
     * @return 结果
     */
    public int insertSonyChannelConsumerInfo(SonyChannelConsumerInfo sonyChannelConsumerInfo);

    /**
     * 修改客户渠道信息
     * 
     * @param sonyChannelConsumerInfo 客户渠道信息
     * @return 结果
     */
    public int updateSonyChannelConsumerInfo(SonyChannelConsumerInfo sonyChannelConsumerInfo);

    /**
     * 批量删除客户渠道信息
     * 
     * @param ids 需要删除的客户渠道信息主键集合
     * @return 结果
     */
    public int deleteSonyChannelConsumerInfoByIds(Long[] ids);

    /**
     * 删除客户渠道信息信息
     * 
     * @param id 客户渠道信息主键
     * @return 结果
     */
    public int deleteSonyChannelConsumerInfoById(Long id);
    
    List<SonyChannelConsumerInfoVO> selectSonyChannelConsumerInfoListByDTO(SonyChannelConsumerInfoDTO dto);
    
    List<SonyChannelConsumerInfoExportVO> selectSonyChannelConsumerInfoListByIds(List<Integer> ids);

    void saveSonyChannelConsumerInfo ( WxMpXmlMessage wxMessage, WxMpUser userWxInfo );
}
