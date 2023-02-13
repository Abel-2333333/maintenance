package com.ruoyi.maintenance.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.maintenance.domain.SonyWechatUser;
import com.ruoyi.maintenance.mapper.SonyWechatUserMapper;
import com.ruoyi.maintenance.service.ISonyWechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 微信用户基本信息Service业务层处理
 * 
 * @author Abel
 * @date 2023-02-01
 */
@Service
public class SonyWechatUserServiceImpl implements ISonyWechatUserService 
{
    @Autowired
    private SonyWechatUserMapper sonyWechatUserMapper;

    /**
     * 查询微信用户基本信息
     * 
     * @param id 微信用户基本信息主键
     * @return 微信用户基本信息
     */
    @Override
    public SonyWechatUser selectSonyWechatUserById(Long id)
    {
        return sonyWechatUserMapper.selectSonyWechatUserById(id);
    }

    /**
     * 查询微信用户基本信息列表
     * 
     * @param sonyWechatUser 微信用户基本信息
     * @return 微信用户基本信息
     */
    @Override
    public List<SonyWechatUser> selectSonyWechatUserList(SonyWechatUser sonyWechatUser)
    {
        return sonyWechatUserMapper.selectSonyWechatUserList(sonyWechatUser);
    }

    /**
     * 新增微信用户基本信息
     * 
     * @param sonyWechatUser 微信用户基本信息
     * @return 结果
     */
    @Override
    public int insertSonyWechatUser(SonyWechatUser sonyWechatUser)
    {
        sonyWechatUser.setCreateTime(DateUtils.getNowDate());
        return sonyWechatUserMapper.insertSonyWechatUser(sonyWechatUser);
    }

    /**
     * 修改微信用户基本信息
     * 
     * @param sonyWechatUser 微信用户基本信息
     * @return 结果
     */
    @Override
    public int updateSonyWechatUser(SonyWechatUser sonyWechatUser)
    {
        sonyWechatUser.setUpdateTime(DateUtils.getNowDate());
        return sonyWechatUserMapper.updateSonyWechatUser(sonyWechatUser);
    }

    /**
     * 批量删除微信用户基本信息
     * 
     * @param ids 需要删除的微信用户基本信息主键
     * @return 结果
     */
    @Override
    public int deleteSonyWechatUserByIds(Long[] ids)
    {
        return sonyWechatUserMapper.deleteSonyWechatUserByIds(ids);
    }

    /**
     * 删除微信用户基本信息信息
     * 
     * @param id 微信用户基本信息主键
     * @return 结果
     */
    @Override
    public int deleteSonyWechatUserById(Long id)
    {
        return sonyWechatUserMapper.deleteSonyWechatUserById(id);
    }
}
