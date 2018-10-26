package com.cloud.access.init;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloud.common.access.ApiMetaParse;
import com.cloud.common.bean.Api;
import com.cloud.common.bean.ServiceInstanceInfo;
import com.cloud.common.cache.cacheL2.CacheL2Dao;
import com.cloud.common.constant.Constants;
import com.cloud.common.context.SystemStarted;
import com.cloud.common.utils.JsonUtil;
import com.cloud.context.ContextHolder;
import com.cloud.context.configuration.ContextProperties;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月24日
 */
@Component("apiInit")
public class ApiInit implements SystemStarted
{
    @Autowired
    private ContextProperties contextProperties; 

    @Autowired
    private ServiceInstanceInfo serviceInstanceInfo;
    
    @Autowired
    private CacheL2Dao cacheL2Client;
    
    @Override
    public void run()
    {
        String apiMetaImpl = contextProperties.getApi().getApiMetaImpl();
        
        ApiMetaParse apiMetaParse = ContextHolder.getBean(apiMetaImpl, ApiMetaParse.class);
        List<Api> apiList = apiMetaParse.parse();
        String serviceName = serviceInstanceInfo.getServiceName();
        System.out.println("apis = " + JsonUtil.beanToJson(apiList));
        
        /**
         * 将当前接口的元数据存储在redis中
         */
        cacheL2Client.hset(Constants.PREFIX + Constants.SERVICE_API, serviceName, apiList);
    }
    
}
