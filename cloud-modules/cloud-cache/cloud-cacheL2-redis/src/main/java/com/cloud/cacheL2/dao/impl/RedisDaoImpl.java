package com.cloud.cacheL2.dao.impl;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.cloud.common.cache.cacheL2.CacheL2Dao;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月19日
 */
@Repository
public class RedisDaoImpl implements CacheL2Dao
{

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    
    @Resource(name="redisTemplate")
    private ValueOperations<Object, Object> valOps;
    
    @Override
    public void set(final String key, final Object value)
    {
        valOps.set(key, value);
    }

    @Override
    public void setex(final String key, final int seconds, final Object value)
    {
        valOps.set(key, value, seconds, TimeUnit.SECONDS);
    }

    @Override
    public Object get(final String key)
    {
        return valOps.get(key);
    }

    @Override
    public Boolean expire(final String key, final int seconds)
    {
        return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

}
