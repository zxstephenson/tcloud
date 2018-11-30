package com.cloud.common.cache.cacheL2;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月19日
 */
public interface CacheL2DAO
{
    default void set(final String key,final Object value){};
    
    default void set(final String key,final byte[] value){};
    
    default void setex(final String key, final Object value, final int seconds){};
    
    default void setex(final String key, final byte[] value, final int seconds){};
    
    default Set<String> keys(final String keyPrefix){
        return null;
    }
    
    default void mset(final Map<? extends Object, ? extends Object> map){};
    
    default List<Object> mget(final Collection<Object> keys){
        return null;
    };
    
    default Object get(final String key){
        return null;
    };
    
    default byte[] getByteValue(final String key){
        return null;
    };
    
    default Boolean del(final String key){
        return null;
    };
    
    default Long multiDel(final Collection<String> keys){
        return null;
    };
    
    default Boolean expire(final String key, final int seconds){
        return null;
    };
    
    default void hset(final Object key, final Object field, final Object value){};
    
    default void mhSet(Object key, Map<? extends Object, ? extends Object> map){};
    
    default Object hget(final Object key, final Object field){
        return null;
    };
    
    default void mhGet(Object key, Collection<Object> hashFields){};
    
    default void hdelete(final Object key, final Object... hashFields){};
}
