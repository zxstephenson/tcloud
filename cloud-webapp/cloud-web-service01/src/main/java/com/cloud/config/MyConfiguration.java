package com.cloud.config;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloud.common.bean.User;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月21日
 */

@Configuration
public class MyConfiguration
{
    
    @Bean
    @RefreshScope
    public User getUser(){
        System.out.println("##############################");
        User user = new User();
        user.setId(123);
        user.setUsername("stephen");
        return user;
    }
    
    
}
