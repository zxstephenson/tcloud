package com.cloud.cacheL2.dao.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import com.cloud.common.cache.cacheL2.CacheL2DAO;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月19日
 */
@Repository
public class RedisDaoImpl implements CacheL2DAO
{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Autowired
    private RedisTemplate<String, byte[]> redisTemplate;
    
    @Resource(name="stringRedisTemplate")
    private ValueOperations<Object, Object> stringValOps;
    
    @Resource(name="stringRedisTemplate")
    private HashOperations<Object, Object, Object> stringHashOps;
    
    @Resource(name="redisTemplate")
    private ValueOperations<String, byte[]> byteValueOps;
    
    
    @Override
    public void set(final String key, final Object value){
        stringValOps.set(key, value);
    }

    @Override
    public void setex(final String key, final Object value, final int seconds){
        stringValOps.set(key, value, seconds, TimeUnit.SECONDS);
    }

    @Override
    public void setex(String key, byte[] value, int seconds)
    {
        byteValueOps.set(key, value, seconds, TimeUnit.SECONDS);
    }
    
    @Override
    public Set<String> keys(String keyPrefix)
    {
        return redisTemplate.keys(keyPrefix);
    }
    
    @Override
    public void mset(final Map<? extends Object, ? extends Object> map){
        stringValOps.multiSet(map);
    }
    
    @Override
    public List<Object> mget(final Collection<Object> keys){
        return stringValOps.multiGet(keys);
    }
    
    @Override
    public Object get(final String key){
        return stringValOps.get(key);
    }
    
    @Override
    public byte[] getByteValue(final String key){
        return byteValueOps.get(key);
    }
    
    @Override
    public Boolean del(final String key){
        return redisTemplate.delete(key);
    }
    
    @Override
    public Long multiDel(final Collection<String> keys){
        return redisTemplate.delete(keys);
    }

    @Override
    public Boolean expire(final String key, final int seconds){
        return stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    @Override
    public void hset(final Object key, final Object field, final Object value){
        stringHashOps.put(key, field, value);
    }
    
    @Override
    public void mhSet(Object key, Map<? extends Object, ? extends Object> map){
        stringHashOps.putAll(key, map);
    }
    
    @Override
    public Object hget(final Object key, final Object field){
        return stringHashOps.get(key, field);
    }

    @Override
    public void mhGet(Object key, Collection<Object> hashFields){
        stringHashOps.multiGet(key, hashFields);
    }
    
    @Override
    public void hdelete(final Object key, final Object... hashFields){
        stringHashOps.delete(key, hashFields);
    }
    
}
