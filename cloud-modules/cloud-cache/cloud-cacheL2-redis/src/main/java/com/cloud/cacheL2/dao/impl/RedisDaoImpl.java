package com.cloud.cacheL2.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.cloud.common.cache.cacheL2.AbstractCacheL2DaoImpl;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月19日
 */
@Repository
public class RedisDaoImpl extends AbstractCacheL2DaoImpl
{

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    
    @Resource(name="redisTemplate")
    private ValueOperations<Object, Object> valOps;
    
    @Resource(name="redisTemplate")
    private HashOperations<Object, Object, Object> hashOps;
    
    
    @Override
    public void set(final String key, final Object value){
        valOps.set(key, value);
    }

    @Override
    public void setex(final String key, final int seconds, final Object value){
        valOps.set(key, value, seconds, TimeUnit.SECONDS);
    }

    @Override
    public void mset(final Map<? extends Object, ? extends Object> map){
        valOps.multiSet(map);
    }
    
    @Override
    public List<Object> mget(final Collection<Object> keys){
        return valOps.multiGet(keys);
    }
    
    @Override
    public Object get(final String key){
        return valOps.get(key);
    }
    
    @Override
    public Boolean delete(final Object key){
        return redisTemplate.delete(key);
    }
    
    @Override
    public Long multiDelete(final Collection<Object> keys){
        return redisTemplate.delete(keys);
    }

    @Override
    public Boolean expire(final String key, final int seconds){
        return redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    @Override
    public void hset(final Object key, final Object field, final Object value){
        hashOps.put(key, field, value);
    }
    
    @Override
    public void mhSet(Object key, Map<? extends Object, ? extends Object> map){
        hashOps.putAll(key, map);
    }
    
    @Override
    public Object hget(final Object key, final Object field){
        return hashOps.get(key, field);
    }

    @Override
    public void mhGet(Object key, Collection<Object> hashFields){
        hashOps.multiGet(key, hashFields);
    }
    
    @Override
    public void hdelete(final Object key, final Object... hashFields){
        hashOps.delete(key, hashFields);
    }
    
}
