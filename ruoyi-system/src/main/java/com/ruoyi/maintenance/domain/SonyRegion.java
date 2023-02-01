package com.ruoyi.maintenance.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 省市关系对象 sony_region
 * 
 * @author Abel
 * @date 2023-02-01
 */
public class SonyRegion extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 地区id */
    @Excel(name = "地区id")
    private String regionId;

    /** 父级地区id. 省, 自治区, 直辖市父级id为-1 */
    @Excel(name = "父级地区id. 省, 自治区, 直辖市父级id为-1")
    private String regionParentId;

    /** 地区编号 */
    @Excel(name = "地区编号")
    private String regionCode;

    /** 地区名 */
    @Excel(name = "地区名")
    private String regionName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setRegionId(String regionId) 
    {
        this.regionId = regionId;
    }

    public String getRegionId() 
    {
        return regionId;
    }
    public void setRegionParentId(String regionParentId) 
    {
        this.regionParentId = regionParentId;
    }

    public String getRegionParentId() 
    {
        return regionParentId;
    }
    public void setRegionCode(String regionCode) 
    {
        this.regionCode = regionCode;
    }

    public String getRegionCode() 
    {
        return regionCode;
    }
    public void setRegionName(String regionName) 
    {
        this.regionName = regionName;
    }

    public String getRegionName() 
    {
        return regionName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("regionId", getRegionId())
            .append("regionParentId", getRegionParentId())
            .append("regionCode", getRegionCode())
            .append("regionName", getRegionName())
            .toString();
    }
}
