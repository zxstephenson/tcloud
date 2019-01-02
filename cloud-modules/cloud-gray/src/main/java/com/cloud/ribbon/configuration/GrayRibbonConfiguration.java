package com.cloud.ribbon.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloud.gray.annotation.ExcludeFromComponetScan;
import com.cloud.gray.lb.GrayLoadBalancer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import com.netflix.loadbalancer.ServerListFilter;
import com.netflix.loadbalancer.ServerListUpdater;

/**
 * 配置类，修改ILoadBalancer的默认实现，默认使用的ILoadBalancer为ZoneAwareLoadBalancer 相关配置，
 * {@link org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration}
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月26日
 */
@ExcludeFromComponetScan
@Configuration
public class GrayRibbonConfiguration
{
    @Bean
    public ILoadBalancer ribbonLoadBalancer(IClientConfig config,
            ServerList<Server> serverList,
            ServerListFilter<Server> serverListFilter, IRule rule, IPing ping,
            ServerListUpdater serverListUpdater)
    {
        return new GrayLoadBalancer(config, rule, ping, serverList,
                serverListFilter, serverListUpdater);
    }

    /*
     * @Bean public IRule selfRule(){ return new SelfRule(); }
     */
    
}
