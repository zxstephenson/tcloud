package com.cloud.shiro.authority;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

public class RedisCacheManager implements CacheManager {

	@Autowired
	private ShiroCache shiroCache;
	
    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
    	return this.shiroCache;
    }

}