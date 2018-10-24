package com.cloud.context.init;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloud.common.access.ApiMetaParse;
import com.cloud.common.bean.Api;
import com.cloud.common.bean.ServiceInstanceInfo;
import com.cloud.common.context.SystemStarted;
import com.cloud.context.ContextHolder;
import com.cloud.context.configuration.ContextProperties;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月24日
 */
@Component
public class ApiInit implements SystemStarted
{
    @Autowired
    private ContextProperties contextProperties; 

    @Autowired
    private ServiceInstanceInfo serviceInstanceInfo;
    
    @Override
    public void run()
    {
        String apiMetaImpl = contextProperties.getApi().getApiMetaImpl();
        
        ApiMetaParse apiMetaParse = (ApiMetaParse) ContextHolder.getBean(apiMetaImpl);
        List<Api> apiList = apiMetaParse.parse();
        String serviceName = serviceInstanceInfo.getServiceName();
        
        
        
        
        
    }
    
}
