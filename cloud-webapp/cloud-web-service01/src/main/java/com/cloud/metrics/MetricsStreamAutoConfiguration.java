package com.cloud.metrics;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月19日
 */

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.actuator.HasFeatures;
import org.springframework.cloud.client.discovery.simple.SimpleDiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.annotation.EnableScheduling;


@RefreshScope
@Configuration
@ConditionalOnClass({EnableBinding.class })
@ConditionalOnProperty(value = "metrics.stream.queue.enabled", matchIfMissing = true)
@EnableConfigurationProperties
@EnableScheduling
@EnableBinding(MetricsStreamClient.class)
public class MetricsStreamAutoConfiguration {

    @Autowired
    private BindingServiceProperties bindings;

    @Autowired
    private MetricsStreamProperties properties;

    @Autowired
    @Output(MetricsStreamClient.OUTPUT)
    private MessageChannel outboundChannel;

    @Autowired(required = false)
    private Registration registration;
    
    
    @Autowired
    private MetricsEndpoint metricsEndpoint;
    

    @Bean
    public HasFeatures metricsStreamQueueFeature() {
        return HasFeatures.namedFeature("Metrics Stream (Queue)",
                MetricsStreamAutoConfiguration.class);
    }

    @PostConstruct
    public void init() {
        BindingProperties outputBinding = this.bindings.getBindings()
                .get(MetricsStreamClient.OUTPUT);
        if (outputBinding == null) {
            this.bindings.getBindings().put(MetricsStreamClient.OUTPUT,
                    new BindingProperties());
        }
        BindingProperties output = this.bindings.getBindings()
                .get(MetricsStreamClient.OUTPUT);
        if (output.getDestination() == null) {
            output.setDestination(this.properties.getDestination());
        }
        if (output.getContentType() == null) {
            output.setContentType(this.properties.getContentType());
        }
    }
    @Bean
    public MetricsStreamTask metricsStreamTask(SimpleDiscoveryClient simpleDiscoveryClient) {
        ServiceInstance serviceInstance = this.registration;
        /*if (serviceInstance == null) {
            serviceInstance = simpleDiscoveryClient.getLocalServiceInstance();
        }*/
        return new MetricsStreamTask(this.outboundChannel, serviceInstance,
                this.properties,this.metricsEndpoint);
    }
}