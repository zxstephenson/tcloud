package com.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.common.access.DispatcherService;
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
    
    @Autowired
    private DispatcherService dispatcherService;

    @Value("${server.port:1111}")
    private int port;
    
    @RequestMapping(value="/service", method=RequestMethod.POST)
    public Object service(@RequestBody RequestData requestData){
        
        System.out.println("serviceController invoked!!! port = " + port);
        try
        {
            Thread.sleep(3000);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        
//        System.err.println("service invoked!!!!" + JsonUtil.beanToJson(requestData));
        
        Object obj = dispatcherService.dispatch(requestData);
//        System.out.println("----------------------");
//        System.out.println("----------------------" + JsonUtil.beanToJson(obj));
//        System.out.println("----------------------");
        return obj;
    }
    
    
    
}
