package com.cloud.common.cache.cacheL2;

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
    
    public Object get(final String key);
    
    public Boolean expire(final String key, final int seconds);
    
    
}
