package com.cloud.ribbon.config;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.ClientHttpResponseStatusCodeException;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRecoveryCallback;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryContext;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryPolicy;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequestFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRetryProperties;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.retry.RetryListener;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.NoBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月8日
 */
public class RetryLoadBalancerInterceptor implements ClientHttpRequestInterceptor {

    private LoadBalancerClient loadBalancer;
    private LoadBalancerRetryProperties lbProperties;
    private LoadBalancerRequestFactory requestFactory;
    private LoadBalancedRetryFactory lbRetryFactory;

    public RetryLoadBalancerInterceptor(LoadBalancerClient loadBalancer,
                                        LoadBalancerRetryProperties lbProperties,
                                        LoadBalancerRequestFactory requestFactory,
                                        LoadBalancedRetryFactory lbRetryFactory) {
        this.loadBalancer = loadBalancer;
        this.lbProperties = lbProperties;
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
            ClientHttpResponse response = RetryLoadBalancerInterceptor.this.loadBalancer.execute(
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
        Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<Class<? extends Throwable>, Boolean>();
        retryableExceptions.put(SocketTimeoutException.class, true);
        
        template.setRetryPolicy(new SimpleRetryPolicy(3, retryableExceptions));
        return template;
    }
}
