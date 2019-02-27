package com.cloud.security.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2019年2月26日
 */

public class MyUserDetails implements UserDetails
{

    private static final long serialVersionUID = 6761914547784746372L;

    private UserInfo userInfo;
    
    private List<Role> roles;
    
    public MyUserDetails(UserInfo userInfo, List<Role> roles){
        this.userInfo = userInfo;
        this.roles = roles;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        List<SimpleGrantedAuthority> listRoles = new ArrayList<SimpleGrantedAuthority>();
        roles.stream().forEach(role -> {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleName());
            listRoles.add(authority);
        });
        
        return listRoles;
    }

    @Override
    public String getPassword()
    {
        return userInfo.getPassword();
    }

    @Override
    public String getUsername()
    {
        return userInfo.getUsername();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }

}
