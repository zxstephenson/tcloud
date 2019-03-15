package com.cloud.security.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cloud.security.bean.MyUserDetails;
import com.cloud.security.bean.Role;
import com.cloud.security.bean.UserInfo;
import com.cloud.security.service.UserService;

/**
 * 自定义实现根据用户名查询用户信息以及用户权限
 * 
 * @author    zhangxin4
 * @version   3.1.0 2019年2月25日
 */
public class DaoUserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(DaoUserDetailsServiceImpl.class);
    @Autowired
    private UserService userService;
    
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException
    {
        UserInfo userInfo = userService.findUserByName(username);
        List<Role> roles = userService.findRolesByUserId(userInfo.getUserId());
        MyUserDetails userDetails = new MyUserDetails(userInfo, roles);
        log.info("============>userInfo = " + userInfo);
        return userDetails;
    }

}
