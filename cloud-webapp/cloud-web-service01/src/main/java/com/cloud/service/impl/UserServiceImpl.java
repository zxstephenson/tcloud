package com.cloud.service.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloud.access.RequestAspect;
import com.cloud.bean.User;
import com.cloud.common.annotation.DefineApi;
import com.cloud.common.cache.cacheL1.CacheL1Dao;
import com.cloud.common.utils.JsonUtil;
import com.cloud.common.utils.ReflectionUtil;
import com.cloud.service.UserService;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月25日
 */
@Component("userServiceImpl")
public class UserServiceImpl implements UserService
{
    private static final Map<Integer, User> users = new HashMap<Integer, User>();
    
    @Autowired
    private CacheL1Dao cacheL1Client;
    
    @Override
    @DefineApi("username")
    public String getUsername(int id, List<String> list)
    {
        User user = users.get(id);
        System.out.println("list = " + JsonUtil.beanToJson(list));
        return "hello";
    }

    @Override
    @DefineApi("createUser")
    public User createUser(int id, int age, Map<String, Object> feature, Map<String, Object> map)
    {
        User user = new User();
        user.setId(id);
        user.setUsername("carlos");
        System.out.println("feature ====> " + JsonUtil.beanToJson(feature));
        System.out.println("map ====> " + JsonUtil.beanToJson(map));
        return user;
    }
    
    @Override
    public void test01(){
        Object username = cacheL1Client.get("myEhcache", "username");
        System.out.println("username =====>" + username);
    }

}
