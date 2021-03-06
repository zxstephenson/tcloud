package com.cloud.ribbon.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.cloud.ribbon.handle.DefaultResponseErrorHandler;
import com.cloud.ribbon.interceptor.RibbonInterceptor;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月21日
 */
@Configuration
public class RibbonConfiguration
{
    @Autowired
    private RibbonProperties ribbonProperties;
    
   /* @Bean
    public RetryPolicy getRetryPolicy(){
        Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<Class<? extends Throwable>, Boolean>();
        retryableExceptions.put(SocketTimeoutException.class, true);
        return new SimpleRetryPolicy(2, retryableExceptions);
    }
    */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate()
    {
        
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(ribbonProperties.getMaxConnTotal())
                .setMaxConnPerRoute(ribbonProperties.getMaxConnPerRoute())
                .build();
        
        HttpComponentsClientHttpRequestFactory httpRequestFactory =  
                new HttpComponentsClientHttpRequestFactory(httpClient);
        
        if (ribbonProperties != null)
        {
            int connectTimeout = ribbonProperties.getConnectionTimeout();
            int readTimeout = ribbonProperties.getReadTimeout();
            if (connectTimeout > 0)
            {
                httpRequestFactory.setConnectTimeout(connectTimeout);
            }
            if (readTimeout > 0)
            {
                httpRequestFactory.setReadTimeout(readTimeout);
            }
        }
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
        return restTemplate;
    }
    
	/*@Bean
	public RibbonInterceptor getRibbonInterceptor(
	      LoadBalancerClient loadBalancerClient,
	      LoadBalancerRequestFactory requestFactory,
	      LoadBalancedRetryFactory loadBalancedRetryFactory) {
	 
	   return new RibbonInterceptor(loadBalancerClient, 
	          requestFactory, loadBalancedRetryFactory);
	}*/
    
}
