package com.cloud.config.client.listener;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloud.config.client.locator.NativePropertySourceLocator;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月20日
 */
@Configuration
public class ConfigClientConfig
{
    @Bean
    @ConditionalOnMissingBean(ConfigServicePropertySourceLocator.class)
    @ConditionalOnProperty(value = "spring.cloud.config.enabled", matchIfMissing = true)
    public NativePropertySourceLocator definedPropertySourceLocator(){
        return new NativePropertySourceLocator();
    }
    
}
