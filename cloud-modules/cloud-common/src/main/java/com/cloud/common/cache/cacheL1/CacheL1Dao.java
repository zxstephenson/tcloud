package com.cloud.common.cache.cacheL1;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月20日
 */

public interface CacheL1Dao
{
    /**
     * 设置cacheName指定的key-value
     * @param cacheName
     * @param key
     * @param value
     */
    public void set(final String cacheName, final Object key, final Object value);
    
    /**
     * 获取cacheName中key的value值
     * @param cacheName
     * @param key
     * @return
     */
    public Object get(final String cacheName, final Object key);
    
    /**
     * 删除cacheName指定的Cache中的指定key
     * @param cacheName
     * @param key
     */
    public Boolean remove(final String cacheName, final Object key);
    
    /**
     * 删除cacheName指定Cache中的所有key-value
     * @param cacheName
     */
    public void removeAll(final String cacheName);

    /**
     * 根据cacheName创建一个默认的Cache
     * @param cacheName
     */
    public Boolean addCache(final String cacheName);
    
    /**
     * 删除cacheName指定的Cache
     * @param cacheName
     */
    public void removeCache(final String cacheName);
    
    /**
     * 删除所有的Cache
     */
    public void removeAllCaches();
    
}
