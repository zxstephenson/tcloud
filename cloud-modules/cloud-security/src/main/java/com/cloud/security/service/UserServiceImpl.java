package com.cloud.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月12日
 */
@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserDao userDao;
    
    @Cacheable(cacheNames = "authority", key = "#username")
    @Override
    public SysUser getUserByName(String username){
        return userDao.selectByName(username);
    }
}
