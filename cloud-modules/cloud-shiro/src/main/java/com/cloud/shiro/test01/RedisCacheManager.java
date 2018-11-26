package com.cloud.shiro.test01;

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
//        return new ShiroCache<K, V>(name, redisTemplate);
    }

   /* public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }*/

}