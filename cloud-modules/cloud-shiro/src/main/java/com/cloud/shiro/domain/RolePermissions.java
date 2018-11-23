package com.cloud.shiro.domain;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月23日
 */

public class RolePermissions
{
    private String id;
    
    private String roleId;
    
    private List<Permission> permissions;

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the roleId
     */
    public String getRoleId()
    {
        return roleId;
    }

    /**
     * @param roleId the roleId to set
     */
    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }

    /**
     * @return the permissions
     */
    public List<Permission> getPermissions()
    {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(List<Permission> permissions)
    {
        this.permissions = permissions;
    }
    
}
