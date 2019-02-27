package com.cloud.security.service.impl;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cloud.security.bean.PermissionRoleInfo;
import com.cloud.security.bean.Role;
import com.cloud.security.bean.UserInfo;
import com.cloud.security.mapper.UserMapper;
import com.cloud.security.service.UserService;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2019年2月27日
 */

public class UserServiceImpl implements UserService
{

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Override
    public void addUser(UserInfo userInfo)
    {
        log.info("==================>add user");
        String password = passwordEncoder.encode(userInfo.getPassword());
        userInfo.setPassword(password);
        userInfo.setUserId(UUID.randomUUID().toString().replace("-", ""));
        userMapper.addUser(userInfo);
    }

    @Override
    public List<PermissionRoleInfo> queryAllPermissionRoleInfo()
    {
        return userMapper.queryAllPermissionRoleInfo();
    }

    @Override
    public UserInfo findUserByName(String username)
    {
        
        return userMapper.findUserByName(username);
    }

    @Override
    public List<Role> findRolesByUserId(String userId)
    {
        return userMapper.findRolesByUserId(userId);
    }


}
