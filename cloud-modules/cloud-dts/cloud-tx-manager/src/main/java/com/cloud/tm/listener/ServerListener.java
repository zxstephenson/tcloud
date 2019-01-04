/**
 * <p>文件名称: ServerListener.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.listener;

import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cloud.tm.listener.service.InitService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月5日
 */

@Component
public class ServerListener implements ServletContextListener
{

    private WebApplicationContext springContext;

    private InitService initService;

    @Override
    public void contextInitialized(ServletContextEvent event)
    {
        springContext = WebApplicationContextUtils
                .getWebApplicationContext(event.getServletContext());
        initService = springContext.getBean(InitService.class);
        initService.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event)
    {
        initService.close();
    }

}
