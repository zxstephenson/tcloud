package com.cloud.service;

import org.springframework.stereotype.Component;

import com.cloud.common.annotation.DefineApi;
import com.cloud.common.bean.User;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月19日
 */
@Component("userService")
public class UserService
{
    @DefineApi("getUserInfo")
    public String getUserName(User user, int age, String username){
//        System.err.println("*****************");
//        System.err.println("user = " + user);
//        System.err.println("age = " + age);
//        System.err.println("username = " + username);
//        System.err.println("*****************");
        return "stephen";
    }
}
