package com.cloud.zookeeper.registry.server.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月26日
 */
@RestController
public class TestController
{
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);
    
    @Value("${server.port}")
    private int port;
    
    @RequestMapping("/getPort")
    public String getPort(){
        logger.info("port = " + port);
        return "port = " + port;
    }
    
}
