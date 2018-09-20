package com.cloud.common.cache.cacheL2;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月20日
 */

public abstract class AbstractCacheL2DaoImpl implements CacheL2Dao
{

    @Override
    public void set(String key, Object value)
    {

    }

    @Override
    public void setex(String key, int seconds, Object value)
    {

    }

    @Override
    public void mset(Map<? extends Object, ? extends Object> map)
    {

    }

    @Override
    public List<Object> mget(Collection<Object> keys)
    {
        return null;
    }

    @Override
    public Object get(String key)
    {
        return null;
    }

    @Override
    public Boolean delete(Object key)
    {
        return null;
    }

    @Override
    public Long multiDelete(Collection<Object> keys)
    {
        return null;
    }

    @Override
    public Boolean expire(String key, int seconds)
    {
        return null;
    }

    @Override
    public void hset(Object key, Object field, Object value)
    {

    }

    @Override
    public void mhSet(Object key, Map<? extends Object, ? extends Object> map)
    {

    }

    @Override
    public Object hget(Object key, Object field)
    {
        return null;
    }

    @Override
    public void mhGet(Object key, Collection<Object> hashFields)
    {

    }

    @Override
    public void hdelete(Object key, Object... hashFields)
    {

    }

}
