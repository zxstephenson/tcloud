package com.cloud.security.bean;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2019年2月26日
 */

public class Role
{
    private String roleId;
    
    private String roleName;
    
    private String roleDesc;

    public String getRoleId()
    {
        return roleId;
    }

    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }

    public String getRoleName()
    {
        return roleName;
    }

    public void setRoleName(String roleName)
    {
        this.roleName = roleName;
    }

    public String getRoleDesc()
    {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc)
    {
        this.roleDesc = roleDesc;
    }
    
}
