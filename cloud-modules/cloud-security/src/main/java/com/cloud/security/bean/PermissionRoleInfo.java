package com.cloud.security.bean;

import java.util.List;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2019年2月26日
 */

public class PermissionRoleInfo
{
    private String resource;
    
    private List<String> roleName;

    public String getResource()
    {
        return resource;
    }

    public void setResource(String resource)
    {
        this.resource = resource;
    }

    public List<String> getRoleName()
    {
        return roleName;
    }

    public void setRoleName(List<String> roleName)
    {
        this.roleName = roleName;
    }
    
}
