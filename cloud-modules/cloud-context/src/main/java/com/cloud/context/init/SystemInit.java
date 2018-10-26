package com.cloud.context.init;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cloud.common.context.SystemStarted;
import com.cloud.context.ContextHolder;
import com.cloud.context.configuration.ContextProperties;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月23日
 */
@Component
public class SystemInit implements ApplicationRunner
{

    @Autowired
    private ContextProperties contextProperties;
    
    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        List<String> list = contextProperties.getSystemInit();
        ApplicationContext applicationContext = ContextHolder.getApplicationContext();
        list.stream().forEach(item -> {
            Object obj = applicationContext.getBean(item);
            if(obj != null)
            {
                SystemStarted instance = (SystemStarted)obj;
                instance.run();
            }
        });
    }

}
