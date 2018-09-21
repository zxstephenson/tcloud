package com.cloud.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.common.bean.RequestData;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月20日
 */
@RestController
public class ServiceController
{

    @RequestMapping("/service")
    public Object service(RequestData requestData){
        
        
        return null;
    }
    
    
    
}
