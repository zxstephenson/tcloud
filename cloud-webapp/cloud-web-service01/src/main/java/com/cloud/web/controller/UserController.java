package com.cloud.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.common.bean.RequestData;
import com.cloud.common.bean.RequestHeader;
import com.cloud.common.bean.User;
import com.cloud.ribbon.client.RibbonClient;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月8日
 */
@RestController
public class UserController
{

    @Autowired
    private RibbonClient ribbonClient;
    
    @PostMapping("/getUsername")
    public Object getUsername(@RequestBody User user){
        
        System.err.println("*****************");
        System.err.println("user = " + user);
//        System.err.println("age = " + age);
//        System.err.println("username = " + username);
        System.err.println("*****************");
        
        RequestData requestData = new RequestData();
        RequestHeader requestHeader = new RequestHeader();
        requestHeader.setCode("getUserInfo"); //接口编号
        
        Map<String, Object> requestBody = new HashMap<String, Object>();
        requestBody.put("user", user);
        requestBody.put("age", 1222);
        requestBody.put("username", "zhangxin");
        requestData.setHead(requestHeader);
        requestData.setBody(requestBody);
        return ribbonClient.remoteForPost("cloud-service03", requestData);
    }
    
    
}
