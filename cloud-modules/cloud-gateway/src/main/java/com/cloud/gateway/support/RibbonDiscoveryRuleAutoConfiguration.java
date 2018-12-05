package com.cloud.gateway.support;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.ribbon.RibbonClientConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.cloud.gateway.filter.GrayscaleReleaseFilter;
import com.cloud.gateway.predicate.MetadataAwarePredicate;
import com.cloud.gateway.rule.DiscoveryEnabledRule;
import com.cloud.gateway.rule.MetadataAwareRule;

import com.netflix.niws.loadbalancer.DiscoveryEnabledNIWSServerList;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月30日
 */
@Configuration
@ConditionalOnClass(DiscoveryEnabledNIWSServerList.class)
@AutoConfigureBefore(RibbonClientConfiguration.class)
@ConditionalOnProperty(value = "ribbon.filter.metadata.enabled", matchIfMissing = false)
public class RibbonDiscoveryRuleAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @Scope("prototype")
    public DiscoveryEnabledRule metadataAwareRule() {
        return new MetadataAwareRule();
    }
    
    @Bean
    public RibbonFilterContextHolder ribbonFilterContextHolder(){
        return new RibbonFilterContextHolder();
    }
    
    @Bean
    public MetadataAwarePredicate metadataAwarePredicate()
    {
        return new MetadataAwarePredicate();
    }
    
    @Bean
    public GrayscaleReleaseFilter grayscaleReleaseFilter(){
    	return new GrayscaleReleaseFilter();
    }
}
