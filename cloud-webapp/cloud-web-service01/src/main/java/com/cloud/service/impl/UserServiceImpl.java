package com.cloud.service.impl;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.cloud.access.RequestAspect;
import com.cloud.bean.User;
import com.cloud.common.annotation.DefineApi;
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
        RequestAspect a = new RequestAspect();
        Method[] methods = a.getClass().getMethods();
        for(Method method :  methods){
            
            List<String> list = ReflectionUtil.getParamterName(method);
            System.out.println(" list = ==>" + list);
        }
    }

}
