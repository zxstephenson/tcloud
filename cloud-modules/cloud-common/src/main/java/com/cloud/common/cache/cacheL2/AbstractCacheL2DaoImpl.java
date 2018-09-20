/**
 * <p>文件名称: AbstractCacheL2DaoImpl.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
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
