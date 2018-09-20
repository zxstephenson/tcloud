package com.cloud.cacheL1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

/**
 * 
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月20日
 */
@Configuration
public class EhCacheConfiguration
{
    
    @Bean
    public CacheManager getCacheManager() throws Exception{
        CacheManager cacheManager = CacheManager.create();
        CacheConfiguration configuration = new CacheConfiguration();
        configuration.maxEntriesLocalHeap(0);
        configuration.setName("myEhcache");
        Ehcache cache = new Cache(configuration);
        cacheManager.addCache(cache);
        Element element = new Element("username", "stephen");
        cache.put(element);
       
        Element element2 = new Element("age", "12312");
        cache.put(element2);
        
        return cacheManager;
    }
    
}
