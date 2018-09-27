package com.cloud.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.ribbon.client.RibbonClient;

/*import com.cloud.bus.producer.clients.ProducerClient;*/

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月25日
 */
@RestController
public class TestController
{

    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    
    @Autowired
    private RibbonClient ribbonClient;
    
   /* @Autowired
    private ProducerClient producerClient;
    
    
    @RequestMapping("/testBus")
    public void test(){
        producerClient.sendBroadcastMessage("hello");
        producerClient.sendGroupMessage("world");
        System.out.println("============================");
    }*/
    
    
    @RequestMapping("/sayHello")
    public String sayHello(){
        logger.info("=============before");
        String result = ribbonClient.remoteForGet("http://helloService/getPort", new HashMap<String, String>(), String.class);
        logger.info("=============after = " + result);
        return result;
    }
    
    
}
