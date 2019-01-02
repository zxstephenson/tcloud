package com.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年12月3日
 */
@RestController
public class UserController
{

    @Value("${server.port}")
    private int port;
    
    
    @GetMapping("/getPort")
    public String getPort(){
        return "current server port is " + port;
    }
    
    @RequestMapping("/test")
    public String test(){
        
        return "";
    }
}
