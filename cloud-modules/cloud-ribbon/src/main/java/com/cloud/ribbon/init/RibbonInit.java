package com.cloud.ribbon.init;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.client.loadbalancer.RetryLoadBalancerInterceptor;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cloud.ribbon.interceptor.RibbonInterceptor;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月9日
 */
@Component
public class RibbonInit implements ApplicationRunner
{

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private RibbonInterceptor ribbonInterceptor;
    
    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        List<ClientHttpRequestInterceptor> list = new  ArrayList<ClientHttpRequestInterceptor>();
        interceptors.parallelStream().forEach(item ->{
            if(!(item instanceof RetryLoadBalancerInterceptor)){
                list.add(item);              
            }
        });
        list.add(ribbonInterceptor);
        restTemplate.setInterceptors(list);        
    }
    
}
