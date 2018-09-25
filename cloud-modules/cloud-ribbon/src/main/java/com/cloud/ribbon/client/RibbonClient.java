package com.cloud.ribbon.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cloud.common.bean.RequestData;
import com.cloud.common.bean.ResponseData;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月21日
 */
@Component
public class RibbonClient
{
    
    @Autowired
    private RestTemplate restTemplate;

    /**
     * post请求
     * 架构内服务调用，传入被调服务的服务名，请求对象参数RequestData
     * @param serviceId
     * @param requestData
     * @return
     */
    public ResponseData remoteForPost(String serviceId, RequestData requestData){
        StringBuilder url = new StringBuilder();
        url.append("http://").append(serviceId).append("/service");
        return restTemplate.postForObject(url.toString(), requestData, ResponseData.class);
    }

    /**
     * 
     * @param url
     * @param obj
     * @param clazz
     * @return
     */
    public <T> T remoteForPost(String url, Object requestObj, 
            Class<T> responseType, Map<String, ?> uriVariables){
        
        return restTemplate.postForObject(url, requestObj, responseType, uriVariables);
    }
    
    
    public ResponseData remoteForGet(String serviceId, RequestData requestData){
        StringBuilder url = new StringBuilder();
        url.append("http://").append(serviceId).append("/service");
        return restTemplate.getForObject(url.toString(), ResponseData.class, requestData);
    }
    
    /**
     * get请求
     * @param url
     * @param params
     * @return
     */
    public <T> T remoteForGet(String url, Map<String, ?> params, Class<T> responseType){
        return restTemplate.getForObject(url, responseType, params);
    }
    
    
}
