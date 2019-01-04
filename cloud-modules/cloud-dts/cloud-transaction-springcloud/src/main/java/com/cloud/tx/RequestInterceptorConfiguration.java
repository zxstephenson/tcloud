package com.cloud.tx;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloud.tx.springcloud.feign.TransactionRestTemplateInterceptor;

/**
 * create by lorne on 2018/1/18
 */
@Configuration
public class RequestInterceptorConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor(){
        return new TransactionRestTemplateInterceptor();
    }
}
