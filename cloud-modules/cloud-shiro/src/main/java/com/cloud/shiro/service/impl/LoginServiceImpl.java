package com.cloud.shiro.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.common.utils.UUIDUtil;
import com.cloud.shiro.domain.Permission;
import com.cloud.shiro.domain.Role;
import com.cloud.shiro.domain.User;
import com.cloud.shiro.mapper.RoleMapper;
import com.cloud.shiro.mapper.UserMapper;
import com.cloud.shiro.service.LoginService;
import com.cloud.shiro.utils.PasswordUtil;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月23日
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private RoleMapper roleMapper;

    //添加用户
    @Override
    public User addUser(User user) {
    	user.setId(UUIDUtil.getUUID());
    	String credentialSalt = UUIDUtil.getUUID();
    	user.setCredentialSalt(credentialSalt);
    	user.setPassword(PasswordUtil.getEncryptPasswordHex(user.getPassword(), credentialSalt));
    	userMapper.addUser(user);
    	System.out.println("====>user = " + user);
    	System.out.println("=====>credentialSalt = " + credentialSalt);
        return user;
    }

    //添加角色
    @Override
    public Role addRole(Role role) {
//        User user = userMapper.findById(map.get("userId").toString());
        role.setRoleName(role.getRoleName());
        Permission permission1 = new Permission();
        permission1.setPermission("create");
        Permission permission2 = new Permission();
        permission2.setPermission("update");
        List<Permission> permissions = new ArrayList<Permission>();
        permissions.add(permission1);
        permissions.add(permission2);
        role.setPermissions(permissions);
        roleMapper.addRole(role);
        return role;
    }

    //查询用户通过用户名
    @Override
    public User findByName(String name) {
        return userMapper.findByName(name);
    }
}
