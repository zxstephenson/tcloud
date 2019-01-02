package com.cloud.cacheL2.lock;

import java.util.Collections;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloud.cacheL2.dao.JedisCommands;
import com.cloud.cacheL2.exception.RedisDBException;
import com.cloud.context.ContextHolder;

/**
 * 
 * Redis实现分布式锁
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月15日
 */
public class DTSLock
{

    private static final Logger log = LoggerFactory.getLogger(DTSLock.class);

    private static final String LOCK_SUCCESS = "OK";

    private static final String SET_IF_NOT_EXIST = "NX";

    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static final Long RELEASE_SUCCESS = 1L;

    private String lockKey;

    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireTime = 60 * 1000;

    /**
     * 请求的唯一标识
     */
    private String requestId;

    private JedisCommands jedisCommands;

    public DTSLock(String lockKey)
    {
        this.lockKey = lockKey + "_lock";
        this.requestId = UUID.randomUUID().toString().replace("-", "");
    }

    public DTSLock(String lockKey, int expireTime)
    {
        this(lockKey);
        this.expireTime = expireTime;
    }

    private JedisCommands getJedis()
    {
        if (jedisCommands == null)
        {
            synchronized (DTSLock.class)
            {
                if (jedisCommands == null)
                {
                    jedisCommands = ContextHolder.getBean(JedisCommands.class);
                }
            }
        }
        return jedisCommands;
    }

    /**
     * 尝试获取分布式锁
     * 
     * @return 是否获取成功
     * @throws Exception
     */
    public boolean lock() throws RedisDBException
    {
        if(log.isDebugEnabled())
        {            
            log.debug("准备获取分布式锁, lockKey = {}; requestId = {}", this.lockKey, this.requestId);
        }
        String result = getJedis().set(this.lockKey, this.requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

        if (LOCK_SUCCESS.equals(result))
        {
            if(log.isDebugEnabled())
            {
                log.debug("获取分布式锁成功， lockKey = {}; requestId = {}", this.lockKey, this.requestId);
            }
            return true;
        }
        if(log.isDebugEnabled())
        {
            log.debug("获取分布式锁失败， lockKey = {}; requestId = {}", this.lockKey, this.requestId);
        }
        return false;
    }

    /**
     * 释放分布式锁
     * 
     * @return 是否释放成功
     * @throws Exception
     */
    public boolean unlock() throws RedisDBException
    {
        if(log.isDebugEnabled())
        {            
            log.debug("准备释放分布式锁, lockKey = {}; requestId = {}", this.lockKey, this.requestId);
        }
        //这里使用Lua脚本是为了实现原子性，redis会将这个lua语句当成是一条命令执行
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = getJedis().eval(script, Collections.singletonList(this.lockKey), Collections.singletonList(this.requestId));

        if (RELEASE_SUCCESS.equals(result))
        {
            if(log.isDebugEnabled())
            {            
                log.debug("释放分布式锁成功, lockKey = {}; requestId = {}", this.lockKey, this.requestId);
            }
            return true;
        }
        
        if(log.isDebugEnabled())
        {            
            log.debug("释放分布式锁失败, lockKey = {}; requestId = {}", this.lockKey, this.requestId);
        }
        return false;
    }

}