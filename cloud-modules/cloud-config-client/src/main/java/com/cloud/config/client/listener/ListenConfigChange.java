package com.cloud.config.client.listener;

import org.springframework.beans.BeansException;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月21日
 */
@Component
public class ListenConfigChange implements ApplicationContextAware, ApplicationListener<EnvironmentChangeEvent> 
{

    @Override
    public void onApplicationEvent(EnvironmentChangeEvent event)
    {
        System.err.println("**********************");
        System.err.println("keys = " + event.getKeys());
        System.err.println("**********************");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
        
    }

}
