package com.cloud.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月23日
 */
@Component
public class ContextHolder implements ApplicationContextAware
{
    private static ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException
    {
        ContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext()
    {
        return applicationContext;
    }
    
    public static <T> T getBean(Class<T> clazz)
    {
        return applicationContext.getBean(clazz);
    }
    
    public static Object getBean(String instanceName)
    {
        return applicationContext.getBean(instanceName);
    }
    
    public static <T> T getBean(String instanceName, Class<T> clazz)
    {
        return applicationContext.getBean(instanceName, clazz);
    }
    
}
