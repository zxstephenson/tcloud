package com.cloud.shiro.domain;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月23日
 */
public class User {

    private String id;
    
    private String username;
    
    private String password;
    
    private List<Role> roles;

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
     * @return the roles
     */
    public List<Role> getRoles()
    {
        return roles;
    }

    /**
     * @param roles the roles to set
     */
    public void setRoles(List<Role> roles)
    {
        this.roles = roles;
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

    @Override
    public String toString()
    {
        return "User [id=" + id + ", username=" + username + ", password="
                + password + ", roles=" + roles + "]";
    }
    
    
}