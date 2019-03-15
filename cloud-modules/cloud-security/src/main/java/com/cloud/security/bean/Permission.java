package com.cloud.security.bean;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2019年2月26日
 */

public class Permission
{
    private String permissionId;
    
    private String permissionValue;
    
    private String permissionDesc;

    public String getPermissionId()
    {
        return permissionId;
    }

    public void setPermissionId(String permissionId)
    {
        this.permissionId = permissionId;
    }

    public String getPermissionValue()
    {
        return permissionValue;
    }

    public void setPermissionValue(String permissionValue)
    {
        this.permissionValue = permissionValue;
    }

    public String getPermissionDesc()
    {
        return permissionDesc;
    }

    public void setPermissionDesc(String permissionDesc)
    {
        this.permissionDesc = permissionDesc;
    }
    
}
