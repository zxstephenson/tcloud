package com.cloud.security.service;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月12日
 */

public class SysUser
{

    private String id;
    
    private String username;
    
    private String password;

    private List<SysRole> roleList;
    
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
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * @return the roleList
     */
    public List<SysRole> getRoleList()
    {
        return roleList;
    }

    /**
     * @param roleList the roleList to set
     */
    public void setRoleList(List<SysRole> roleList)
    {
        this.roleList = roleList;
    }
    
    
}
