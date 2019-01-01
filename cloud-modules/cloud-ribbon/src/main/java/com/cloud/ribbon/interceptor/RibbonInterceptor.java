package com.cloud.ribbon.interceptor;

/*
 * Copyright 2016-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.ClientHttpResponseStatusCodeException;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRecoveryCallback;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryContext;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryPolicy;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.retry.RetryListener;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.NoBackOffPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

import com.cloud.ribbon.config.RibbonProperties;
import com.cloud.ribbon.config.RibbonProperties.Retry;

/**
 * @author Ryan Baxter
 * @author Will Tran
 * @author Gang Li
 */
public class RibbonInterceptor implements ClientHttpRequestInterceptor {

    private LoadBalancerClient loadBalancer;
    private LoadBalancerRequestFactory requestFactory;
    private LoadBalancedRetryFactory lbRetryFactory;

    @Autowired
    private RibbonProperties ribbonProperties;
    
    public RibbonInterceptor(LoadBalancerClient loadBalancer,
	            LoadBalancerRequestFactory requestFactory,
	            LoadBalancedRetryFactory lbRetryFactory) {
        this.loadBalancer = loadBalancer;
        this.requestFactory = requestFactory;
        this.lbRetryFactory = lbRetryFactory;

    }

    @Override
    public ClientHttpResponse intercept(final HttpRequest request, final byte[] body,
                    final ClientHttpRequestExecution execution) throws IOException {
        final URI originalUri = request.getURI();
        final String serviceName = originalUri.getHost();
        Assert.state(serviceName != null, "Request URI does not contain a valid hostname: " + originalUri);
        final LoadBalancedRetryPolicy retryPolicy = lbRetryFactory.createRetryPolicy(serviceName,
                loadBalancer);
        RetryTemplate template = createRetryTemplate(serviceName, request, retryPolicy);
        return template.execute(context -> {
            ServiceInstance serviceInstance = null;
            if (context instanceof LoadBalancedRetryContext) {
                LoadBalancedRetryContext lbContext = (LoadBalancedRetryContext) context;
                serviceInstance = lbContext.getServiceInstance();
            }
            if (serviceInstance == null) {
                serviceInstance = loadBalancer.choose(serviceName);
            }
            ClientHttpResponse response = RibbonInterceptor.this.loadBalancer.execute(
                    serviceName, serviceInstance,
                    requestFactory.createRequest(request, body, execution));
            int statusCode = response.getRawStatusCode();
            if (retryPolicy != null && retryPolicy.retryableStatusCode(statusCode)) {
                byte[] bodyCopy = StreamUtils.copyToByteArray(response.getBody());
                response.close();
                throw new ClientHttpResponseStatusCodeException(serviceName, response, bodyCopy);
            }
            return response;
        }, new LoadBalancedRecoveryCallback<ClientHttpResponse, ClientHttpResponse>() {
            //This is a special case, where both parameters to LoadBalancedRecoveryCallback are
            //the same.  In most cases they would be different.
            @Override
            protected ClientHttpResponse createResponse(ClientHttpResponse response, URI uri) {
                return response;
            }
        });
    }

    private RetryTemplate createRetryTemplate(String serviceName, HttpRequest request, LoadBalancedRetryPolicy retryPolicy) {
        RetryTemplate template = new RetryTemplate();
        BackOffPolicy backOffPolicy = lbRetryFactory.createBackOffPolicy(serviceName);
        template.setBackOffPolicy(backOffPolicy == null ? new NoBackOffPolicy() : backOffPolicy);
        template.setThrowLastExceptionOnExhausted(true);
        RetryListener[] retryListeners = lbRetryFactory.createRetryListeners(serviceName);
        if (retryListeners != null && retryListeners.length != 0) {
            template.setListeners(retryListeners);
        }
        
        //添加需要重试的异常类
        Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<Class<? extends Throwable>, Boolean>();
        
        Retry retry = ribbonProperties.getRetry();
        List<String> listExceptions = retry.getRetryableExceptions();
        listExceptions.parallelStream().forEach(item -> {
            try
            {
                Class<? extends Throwable> clazz = (Class<? extends Throwable>) Class.forName(item);
                retryableExceptions.put(clazz, true);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        });
        int maxAttempts = retry.getMaxAttempts();
        maxAttempts += 1;
        //设置retryPolicy
        template.setRetryPolicy((!retry.isEnable() || retryPolicy == null) ?
                    new NeverRetryPolicy() : new SimpleRetryPolicy(maxAttempts, retryableExceptions));
        
        return template;
    }
}
