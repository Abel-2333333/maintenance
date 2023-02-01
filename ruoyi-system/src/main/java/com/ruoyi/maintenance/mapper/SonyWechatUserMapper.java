package com.ruoyi.maintenance.mapper;

import com.ruoyi.maintenance.domain.SonyWechatUser;

import java.util.List;

/**
 * 微信用户基本信息Mapper接口
 * 
 * @author Abel
 * @date 2023-02-01
 */
public interface SonyWechatUserMapper 
{
    /**
     * 查询微信用户基本信息
     * 
     * @param id 微信用户基本信息主键
     * @return 微信用户基本信息
     */
    public SonyWechatUser selectSonyWechatUserById(Long id);

    /**
     * 查询微信用户基本信息列表
     * 
     * @param sonyWechatUser 微信用户基本信息
     * @return 微信用户基本信息集合
     */
    public List<SonyWechatUser> selectSonyWechatUserList(SonyWechatUser sonyWechatUser);

    /**
     * 新增微信用户基本信息
     * 
     * @param sonyWechatUser 微信用户基本信息
     * @return 结果
     */
    public int insertSonyWechatUser(SonyWechatUser sonyWechatUser);

    /**
     * 修改微信用户基本信息
     * 
     * @param sonyWechatUser 微信用户基本信息
     * @return 结果
     */
    public int updateSonyWechatUser(SonyWechatUser sonyWechatUser);

    /**
     * 删除微信用户基本信息
     * 
     * @param id 微信用户基本信息主键
     * @return 结果
     */
    public int deleteSonyWechatUserById(Long id);

    /**
     * 批量删除微信用户基本信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSonyWechatUserByIds(Long[] ids);
}
