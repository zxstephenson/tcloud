package com.cloud.cacheL1.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloud.common.cache.cacheL1.AbstractCacheL1DaoImpl;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月20日
 */
@Component
public class EhcacheDaoImpl extends AbstractCacheL1DaoImpl
{

    private static final Logger logger = LoggerFactory.getLogger(EhcacheDaoImpl.class);
    
    @Autowired
    private CacheManager cacheManager; 
    
    @Override
    public void set(String cacheName, Object key, Object value)
    {
        Cache cache = getCache(cacheName);
        cache.put(new Element(key, value));
    }

    @Override
    public Object get(String cacheName, Object key)
    {
        Cache cache = getCache(cacheName);
        Element element = cache.get(key);
        if(element != null)
        {
            return element.getObjectValue();
        }
        return null;
    }

    @Override
    public Boolean remove(String cacheName, Object key)
    {
        Cache cache = getCache(cacheName);
        return cache.remove(key);
    }

    @Override
    public void removeAll(String cacheName)
    {
        Cache cache = getCache(cacheName);
        cache.removeAll();
    }

    @Override
    public Boolean addCache(String cacheName)
    {
        try{            
            cacheManager.addCache(cacheName);
        }catch(Exception e){
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public void removeCache(String cacheName)
    {
        cacheManager.removeCache(cacheName);
    }

    @Override
    public void removeAllCaches()
    {
        cacheManager.removeAllCaches();
    }

    private Cache getCache(String cacheName)
    {
        return cacheManager.getCache(cacheName);
    }

}
