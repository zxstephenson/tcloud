package com.cloud.shiro.mapper;

import com.cloud.shiro.domain.Role;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月23日
 */

public interface RoleMapper
{
    public void addRole(Role role);
    
    public Role queryRolesByUserId(String userId);
}
