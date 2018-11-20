package com.cloud.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月12日
 */
@Service
public class MyUserDetailsSerivce implements UserDetailsService
{

    @Autowired
    private UserService userService;
    
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException
    {
        SysUser sysUser = userService.getUserByName(username);
        if(null == sysUser){
            throw new UsernameNotFoundException(username);
        }
        
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(SysRole role : sysUser.getRoleList())
        {
            for(SysPermission permission : role.getPermissionList())
            {
                authorities.add(new SimpleGrantedAuthority(permission.getCode()));
            }
        }
        return new User(sysUser.getUsername(), sysUser.getPassword(), authorities);
    }

}
