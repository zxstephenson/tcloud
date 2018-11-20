package com.cloud.security.service;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月12日
 */

public class SysRole
{

    public SysRole(){
        
    }
    
    public SysRole(String roleCode, String roleDesc){
        this.roleCode = roleCode;
        this.roleDesc = roleDesc;
    }
    
    private String roleCode;
    
    private String roleDesc;
    
    private List<SysPermission> PermissionList;

    /**
     * @return the roleCode
     */
    public String getRoleCode()
    {
        return roleCode;
    }

    /**
     * @param roleCode the roleCode to set
     */
    public void setRoleCode(String roleCode)
    {
        this.roleCode = roleCode;
    }

    /**
     * @return the roleDesc
     */
    public String getRoleDesc()
    {
        return roleDesc;
    }

    /**
     * @param roleDesc the roleDesc to set
     */
    public void setRoleDesc(String roleDesc)
    {
        this.roleDesc = roleDesc;
    }

    /**
     * @return the permissionList
     */
    public List<SysPermission> getPermissionList()
    {
        return PermissionList;
    }

    /**
     * @param permissionList the permissionList to set
     */
    public void setPermissionList(List<SysPermission> permissionList)
    {
        PermissionList = permissionList;
    }
    
    
}
