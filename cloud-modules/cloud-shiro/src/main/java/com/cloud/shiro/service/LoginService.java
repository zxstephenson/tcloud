package com.cloud.shiro.service;

import com.cloud.shiro.domain.Role;
import com.cloud.shiro.domain.User;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月23日
 */

public interface LoginService
{
    public User addUser(User user);
    
    public Role addRole(Role role);
    
    public User findByName(String username);

}
