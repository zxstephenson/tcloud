package com.cloud.shiro.authority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.SerializationUtils;

import com.cloud.common.cache.cacheL2.CacheL2DAO;
import com.cloud.shiro.config.ShiroProperties;

@SuppressWarnings("unchecked")
public class ShiroCache<K, V> implements Cache<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(ShiroCache.class);
    
    private static final String SHIRO_AUTHORITY_REDIS = "cloud:shiro:cache";
    
    @Autowired
    private CacheL2DAO cacheL2Dao;
    
    /**
     * 用户权限数据缓存，默认过期时间，单位为秒,默认30分钟
     */
    @Autowired
    private ShiroProperties shiroProperties;
    
    public ShiroCache(){
    	
    }
    
    private String getKey(K k)
    {
        if (k instanceof String)
        {
            return SHIRO_AUTHORITY_REDIS + ":" + k;
        }
        return k.toString();
    }
    

    @Override
    public V get(K key) throws CacheException {

        logger.error("=======get===========");
        byte[] value = cacheL2Dao.getByteValue(getKey(key));
        if (value != null)
        {
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public V put(K k, V v) throws CacheException 
    {
        logger.error("=======put===========");
        String key = getKey(k);
        byte[] value = SerializationUtils.serialize(v);
        int authorityExpireSeconds = shiroProperties.getSessionExpireTimeout();
        //如果超时时间设置为小于等于0的值，则不设置超时时间
        if(authorityExpireSeconds <= 0) 
        {
            cacheL2Dao.set(key, value);
        }else 
        {
            cacheL2Dao.setex(key, value, authorityExpireSeconds);
        }
        return v;
    
    }

    @Override
    public V remove(K k) throws CacheException 
    {
        logger.error("=======remove===========");
        String key = getKey(k);
        byte[] value = cacheL2Dao.getByteValue(key);
        cacheL2Dao.del(key);
        if (value != null)
        {
            return (V) SerializationUtils.deserialize(value);
        }
        return null;
    }

    @Override
    public void clear() throws CacheException {
        logger.error("=======clear===========");
    }

    @Override
    public int size() {
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        logger.error("=======keys===========");
        Set<String> keys = cacheL2Dao.keys(SHIRO_AUTHORITY_REDIS);

        Set<K> newKeys = new HashSet<>();

        if (CollectionUtils.isEmpty(keys))
        {
            return newKeys;
        }

        for (String key : keys)
        {
            newKeys.add((K) key);
        }
        return newKeys;
    
    }

    @Override
    public Collection<V> values() {
        logger.error("=======values===========");
        Set<K> set = keys();
        List<V> list = new ArrayList<>();
        for (K s : set) {
            list.add(get(s));
        }
        return list;
    }

}