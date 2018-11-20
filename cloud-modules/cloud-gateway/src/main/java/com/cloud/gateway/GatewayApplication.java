package com.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 
 * 〈一句话功能简述〉 网关启动类
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   0.0.1 2018年8月20日
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class GatewayApplication 
{
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
