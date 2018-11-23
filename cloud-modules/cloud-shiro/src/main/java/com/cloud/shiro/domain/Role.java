package com.cloud.shiro.domain;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月23日
 */

public class Role {

    private String id;
    
    private String roleName;
    
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString()
    {
        return "Role [id=" + id + ", roleName=" + roleName + ", permissions="
                + permissions + "]";
    }
    
}