package com.cloud.gateway.predicate;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.cloud.context.ContextHolder;
import com.cloud.gateway.api.RibbonFilterContext;
import com.cloud.gateway.support.RibbonFilterContextHolder;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月30日
 */
public class MetadataAwarePredicate extends DiscoveryEnabledPredicate {

    private static RibbonFilterContextHolder contextHolder;
    
    private RibbonFilterContextHolder getContextHolder(){
        if(null == contextHolder)
        {
            synchronized(MetadataAwarePredicate.class)
            {
                if(null == contextHolder)
                {
                    return ContextHolder.getBean(RibbonFilterContextHolder.class);                    
                }
            }
        }
        
        return contextHolder;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean apply(DiscoveryEnabledServer server) {

        final RibbonFilterContext context = getContextHolder().getCurrentContext();
        final Set<Map.Entry<String, String>> attributes = Collections.unmodifiableSet(context.getAttributes().entrySet());
        final Map<String, String> metadata = server.getInstanceInfo().getMetadata();
        //如果当前请求的header中没有传递
        if((null == attributes || attributes.isEmpty()))
        {
            //所有配置eureka.instance.metadata-map.lanch配置项的实例都认为是灰度实例
            if(!metadata.containsKey("lanch"))
            {
                return true; //当前实例不为灰度实例         
            }else {
                return false; //当前实例为灰度实例
            }
        }else{
            if(!metadata.containsKey("lanch"))
            {
                return false;
            }else {
                return metadata.entrySet().containsAll(attributes);
            }
        }
    }
}
