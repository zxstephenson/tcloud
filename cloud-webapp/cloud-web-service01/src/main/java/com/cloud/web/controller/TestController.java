package com.cloud.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.bean.User;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月21日
 */

@RestController
public class TestController
{
    
    @Autowired
    private DiscoveryClient discoveryClient;
    
    @Autowired
    private Environment environment;
    
    @Autowired
    private User user;
    

    @RequestMapping("/testDiscoveryClient")
    public void testDiscoveryClient(){
        List<String> services = discoveryClient.getServices();
        
        for(String service : services){
            System.out.println("******************************");
            System.out.println("serviceId = " + service);
            List<ServiceInstance> serviceInstance = discoveryClient.getInstances(service);
            
            for(ServiceInstance instance : serviceInstance){
                System.out.println(instance.getHost() + " : " + instance.getPort());
            }
            System.out.println("******************************");
        }
    }
    
    @RequestMapping("/getProperty/{key}")
    public String getProperty(@PathVariable("key") String key){
        System.out.println("----------------------");
        System.out.println("----------------------user = " + user);
        System.out.println("----------------------");
        return environment.getProperty(key);
    }
    
    
}
