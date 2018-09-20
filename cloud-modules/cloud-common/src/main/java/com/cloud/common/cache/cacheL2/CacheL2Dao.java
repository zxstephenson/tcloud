package com.cloud.common.cache.cacheL2;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月19日
 */
public interface CacheL2Dao
{
    public void set(final String key,final Object value);
    
    public void setex(final String key, final int seconds, final Object value);
    
    public void mset(final Map<? extends Object, ? extends Object> map);
    
    public List<Object> mget(final Collection<Object> keys);
    
    public Object get(final String key);
    
    public Boolean delete(final Object key);
    
    public Long multiDelete(final Collection<Object> keys);
    
    public Boolean expire(final String key, final int seconds);
    
    public void hset(final Object key, final Object field, final Object value);
    
    public void mhSet(Object key, Map<? extends Object, ? extends Object> map);
    
    public Object hget(final Object key, final Object field);
    
    public void mhGet(Object key, Collection<Object> hashFields);
    
    public void hdelete(final Object key, final Object... hashFields);
}
