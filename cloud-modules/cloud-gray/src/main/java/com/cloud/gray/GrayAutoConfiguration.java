package com.cloud.gray;

import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.cloud.ribbon.configuration.GrayRibbonConfiguration;

/**
 * 灰度发布自动配置类
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月24日
 */
@Configuration
@ComponentScan
@RibbonClients(defaultConfiguration = GrayRibbonConfiguration.class)
public class GrayAutoConfiguration
{

}
