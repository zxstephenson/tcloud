/**
 * <p>文件名称: RedisCacheHandler.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.shiro.controller.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.szkingdom.jros.cache.dao.JedisClient;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月6日
 */
@Component
public class RedisCacheHandler implements CacheHandler
{
    @Autowired
    private JedisClient jedisClient;
    
    @Override
    public void set(byte[] key, byte[] value)
    {
        jedisClient.set(key, value);
    }

    @Override
    public byte[] get(byte[] key)
    {
        return jedisClient.get(key);
    }

    @Override
    public void del(byte[] key)
    {
        jedisClient.del(key);
    }

	@Override
	public void setex(byte[] key, byte[] value, int seconds) 
	{
		jedisClient.set(key, value, seconds);
	}

	@Override
	public void hset(byte[] key, byte[] field, byte[] value) 
	{
		jedisClient.hset(key, field, value);
	}

	@Override
	public byte[] hget(byte[] key, byte[] field) 
	{
		return jedisClient.hget(key, field);
	}


	@Override
	public void hsetex(byte[] key, byte[] field, byte[] value, int seconds) {
		jedisClient.hset(key, field, value, seconds);
	}

	@Override
	public byte[] hgetex(byte[] key, byte[] field, int seconds) {
		return jedisClient.hget(key, field, seconds);
	}

	
}
