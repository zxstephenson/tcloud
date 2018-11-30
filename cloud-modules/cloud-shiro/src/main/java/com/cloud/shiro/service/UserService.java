package com.cloud.shiro.service;

import java.util.List;

import com.cloud.shiro.domain.User;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月28日
 */

public interface UserService
{
    public User createUser(User user);// 创建用户
    
    public User updateUser(User user);// 更新用户
    
    public void deleteUser(Long userId);// 删除用户
    
    public void changePassword(Long userId, String newPassword); //修改密码
    
    User findOne(Long userId);// 根据 id 查找用户
    
    List<User> findAll();// 得到所有用户
    
    public User findByUsername(String username);// 根据用户名查找用户

}
