package com.cloud.ribbon.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.cloud.common.bean.RequestData;

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
     * GET 请求，返回<T>类型对象
     * @param serviceId
     * @param requestData
     * @param responseType
     * @return
     */
    public <T> T getForObject(String serviceId, RequestData requestData,
    		Class<T> responseType){
    	
    	ResponseEntity<T> responseEntity = getForEntity(serviceId, requestData, responseType);
    	return responseEntity.getBody();
    }
    
    /**
     * GET 请求，返回ResponseEntity<T>对象
     * @param serviceId
     * @param requestData
     * @param responseType
     * @return
     */
    public <T> ResponseEntity<T> getForEntity(String serviceId, RequestData requestData,
    		Class<T> responseType){
    	
    	StringBuilder url = new StringBuilder();
        url.append("http://").append(serviceId).append("/service");
    	return request(url.toString(), HttpMethod.GET, requestData, responseType, null);
    }
    
    /**
     * POST 请求
     * @param serviceId
     * @param requestData
     * @param responseType
     * @return
     */
    public <T> T postForObject(String serviceId, RequestData requestData,
    		Class<T> responseType){
    	ResponseEntity<T> responseEntity = postForEntity(serviceId, requestData, responseType);
    	return responseEntity.getBody();
    }
    
    /**
     * POST 请求
     * @param serviceId
     * @param requestData
     * @param responseType
     * @return
     */
    public <T> ResponseEntity<T> postForEntity(String serviceId, RequestData requestData,
    		Class<T> responseType){
    	StringBuilder url = new StringBuilder();
        url.append("http://").append(serviceId).append("/service");
    	return request(url.toString(), HttpMethod.POST, requestData, responseType, null);
    }
    
    /**
     * PUT 请求
     * @param serviceId
     * @param requestData
     * @param responseType
     * @return
     */
    public <T> T putForObject(String serviceId, RequestData requestData,
    		Class<T> responseType){
    	ResponseEntity<T> responseEntity = putForEntity(serviceId, requestData, responseType);
    	return responseEntity.getBody();
    }
    
    /**
     * PUT 请求
     * @param serviceId
     * @param requestData
     * @param responseType
     * @return
     */
    public <T> ResponseEntity<T> putForEntity(String serviceId, RequestData requestData,
    		Class<T> responseType){
    	StringBuilder url = new StringBuilder();
        url.append("http://").append(serviceId).append("/service");
    	return request(url.toString(), HttpMethod.PUT, requestData, responseType, null);
    }
    
    /**
     * DELETE 请求
     * @param serviceId
     * @param requestData
     * @param responseType
     * @return
     */
    public <T> T deleteForObject(String serviceId, RequestData requestData,
    		Class<T> responseType){
    	ResponseEntity<T> responseEntity = deleteForEntity(serviceId, requestData, responseType);
    	return responseEntity.getBody();
    }
    
    /**
     * DELETE 请求
     * @param serviceId
     * @param requestData
     * @param responseType
     * @return
     */
    public <T> ResponseEntity<T> deleteForEntity(String serviceId, RequestData requestData,
    		Class<T> responseType){
    	StringBuilder url = new StringBuilder();
        url.append("http://").append(serviceId).append("/service");
    	return request(url.toString(), HttpMethod.DELETE, requestData, responseType, null);
    }
    
    /**
     * 
     * @param url			请求url
     * @param method		请求方法
     * @param requestData	请求参数对象
     * @param responseType	请求响应类型
     * @param headers		请求中HTTP header信息
     * @return
     */
    public <T> ResponseEntity<T> request(String url, HttpMethod method, RequestData requestData,
    		Class<T> responseType, Map<String, String> headers){
    	HttpHeaders httpHeaders = new HttpHeaders();
        if(headers != null && !headers.isEmpty()){
        	
	        for(Map.Entry<String, String> entry : headers.entrySet()){
	        	httpHeaders.add(entry.getKey(), entry.getValue());
	        }
        }
    	HttpEntity<RequestData> requestEntity = new HttpEntity<RequestData>(requestData, httpHeaders);
    	return restTemplate.exchange(url, method, requestEntity, responseType);
    }
    
}
