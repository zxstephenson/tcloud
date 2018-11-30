package com.cloud.access.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloud.common.bean.Api;
import com.cloud.common.bean.ServiceInstanceInfo;
import com.cloud.common.cache.cacheL2.CacheL2DAO;
import com.cloud.common.constant.Constants;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月25日
 */
@Component
public class ApiService
{
    @Autowired
    private CacheL2DAO cacheL2Client;
    
    @Autowired
    private ServiceInstanceInfo serviceInstanceInfo;
    
    @SuppressWarnings("unchecked")
    public Api getApiByApiCode(String apiCode)
    {
        String serviceName = serviceInstanceInfo.getServiceName();
        List<Api> listApi = (List<Api>) cacheL2Client.hget(Constants.PREFIX + Constants.SERVICE_API, serviceName);
        Api api = listApi.stream().filter(item -> apiCode.equals(item.getCode())).findFirst().orElse(null);
        return api;
    }
    
    
    
    
}
