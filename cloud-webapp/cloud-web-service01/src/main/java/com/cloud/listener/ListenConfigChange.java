/**
 * <p>文件名称: ListenConfigChange.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.listener;

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
