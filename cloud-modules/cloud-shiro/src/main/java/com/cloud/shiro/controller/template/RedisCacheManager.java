/**
 * <p>文件名称: RedisCacheManager.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.shiro.controller.template;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version   2.4.2 2018年8月31日
 */
public class RedisCacheManager implements CacheManager
{
    private RedisCache redisCache;

    /**
     * @param redisCache
     *            the redisCache to set
     */
    public void setRedisCache(RedisCache redisCache)
    {
        this.redisCache = redisCache;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String arg0) throws CacheException
    {
        return redisCache;
    }

}
