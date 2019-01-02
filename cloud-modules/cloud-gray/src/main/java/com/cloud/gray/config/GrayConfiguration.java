package com.cloud.gray.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloud.gray.bean.GrayServicesRulesInfo;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月26日
 */
@Configuration
public class GrayConfiguration
{
    @Bean
    public GrayServicesRulesInfo getGrayInfo()
    {
        return new GrayServicesRulesInfo();
    }
}
