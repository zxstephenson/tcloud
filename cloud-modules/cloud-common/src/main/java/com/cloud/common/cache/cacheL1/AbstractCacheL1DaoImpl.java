package com.cloud.common.cache.cacheL1;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月20日
 */
public abstract class AbstractCacheL1DaoImpl implements CacheL1Dao
{

    @Override
    public void set(String cacheName, Object key, Object value)
    {

    }

    @Override
    public Object get(String cacheName, Object key)
    {
        return null;
    }

    @Override
    public Boolean remove(String cacheName, Object key)
    {
        return false;
    }

    @Override
    public void removeAll(String cacheName)
    {

    }

    @Override
    public Boolean addCache(String cacheName)
    {
        return false;
    }

    @Override
    public void removeCache(String cacheName)
    {

    }

    @Override
    public void removeAllCaches()
    {

    }

}
