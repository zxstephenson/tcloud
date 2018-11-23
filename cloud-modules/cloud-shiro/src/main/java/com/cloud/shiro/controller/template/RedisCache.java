/**
 * <p>文件名称: RedisCache.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.shiro.controller.template;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;

import com.szkingdom.jros.cache.dao.JedisClient;

/**
 * 使用Redis缓存用户权限数据实现类 
 * 
 * @author    zhangxin4
 * @version   2.4.2 2018年8月31日
 */
public class RedisCache<K, V> implements Cache<K, V>
{

    @Autowired
    private JedisClient jedisClient;

    /**
     * 用户权限数据缓存，默认过期时间，单位为秒,默认30分钟
     */
    private int authorityExpireSeconds = 1800;

    private static final String SHIRO_AUTHORITY_REDIS = "shiro:authority:redis";

    public void setAuthorityExpireSeconds(int authorityExpireSeconds)
    {
        this.authorityExpireSeconds = authorityExpireSeconds;
    }

    private byte[] getKey(K k)
    {

        if (k instanceof String)
        {
            return (SHIRO_AUTHORITY_REDIS + ":" + k).getBytes();
        }
        return SerializationUtils.serialize(k);
    }

    @Override
    public void clear() throws CacheException
    {
        // 可以不用重写，清除会清空redis中的所有的数据，有风险
    }

    /**
     * 从redis中获取权限数据
     */
    @Override
    public V get(K key) throws CacheException
    {
        byte[] value = jedisClient.get(getKey(key));
        if (value != null)
        {
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public Set<K> keys()
    {

        Set<byte[]> keys = jedisClient.keys(SHIRO_AUTHORITY_REDIS.getBytes());

        Set<K> newKeys = new HashSet<>();

        if (CollectionUtils.isEmpty(keys))
        {
            return newKeys;
        }

        for (byte[] key : keys)
        {
            newKeys.add((K) key);
        }
        return newKeys;
    }

    /**
     * 将权限数据更新到Redis缓存中
     */
    @Override
    public V put(K k, V v) throws CacheException
    {
        byte[] key = getKey(k);
        byte[] value = SerializationUtils.serialize(v);
        
        //如果超时时间设置为小于等于0的值，则不设置超时时间
        if(authorityExpireSeconds <= 0) 
        {
        	jedisClient.set(key, value);
        }else 
        {
        	jedisClient.set(key, value, authorityExpireSeconds);
        }
        return v;
    }

    /**
     * 清除用户缓存信息
     */
    @Override
    public V remove(K k) throws CacheException
    {
    	
        byte[] key = getKey(k);
        byte[] value = jedisClient.get(key);
        jedisClient.del(key);
        if (value != null)
        {
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public int size()
    {
        return 0;
    }

    @Override
    public Collection<V> values()
    {
        return null;
    }

}
