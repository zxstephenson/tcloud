package com.cloud.service;

import org.springframework.stereotype.Component;

import com.cloud.annotation.Api;
import com.cloud.bean.User;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月19日
 */
@Component("userService")
public class UserService
{
    @Api("getUserInfo")
    public String getUserName(User user, int age, String username){
        
        return "stephen";
    }
}
