/**
 * <p>文件名称: RedisConfiguration.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.cacheL2.config;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.cloud.cacheL2.dao.JedisClusterDao;
import com.cloud.cacheL2.dao.JedisCommands;
import com.cloud.cacheL2.dao.JedisDao;
import com.cloud.cacheL2.exception.RedisDBException;
import com.cloud.common.cache.exception.CacheException;
import com.cloud.common.constant.Constants;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月13日
 */
@Configuration
public class RedisConfiguration
{
    private final Logger log = LoggerFactory.getLogger(RedisConfiguration.class);
    
    @Autowired
    private RedisProperties redisProperties;
    
    @Bean
    public JedisCommands getJedisCommand() throws RedisDBException
    {
        if (redisProperties != null)
        {
            if (isCluster())
            {
                return new JedisClusterDao();
            } else
            {
                return new JedisDao();
            }
        }
        throw new RedisDBException("-", "未获取到RedisConfig实例对象。");
    }
    
    /**
     * 判断当前是集群还是单机
     * @return
     * @throws RedisDBException
     */
    private boolean isCluster() throws RedisDBException
    {
        List<String> nodes = redisProperties.getHostAndPort();
        if (null != nodes)
        {
            return nodes.size() > 1 ? true : false;
        } else
        {
            throw new RedisDBException(Constants.REDIS_CONFIG_ERROR_CODE,
                    String.format(Constants.REDIS_CONFIG_ERROR_MSG,
                            "tcloud.redis.hostAndPort"));
        }
    }
    
    @Bean
    public JedisPool getJedis() throws CacheException
    {
        if (redisProperties == null)
        {
            log.error("未正确初始化Redis配置信息。");
            return null;
        }
        log.debug("初始化JedisPool实例开始....");
        
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisProperties.getPool().getMaxIdle());
        jedisPoolConfig.setMinIdle(redisProperties.getPool().getMinIdle());
        jedisPoolConfig.setMaxTotal(redisProperties.getPool().getMaxActive());
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getPool().getMaxWait());
        // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        jedisPoolConfig.setTestOnBorrow(true);

        JedisPool jedisPool = null;
        List<String> nodes = redisProperties.getHostAndPort();
        if (nodes != null)
        {
            String node = nodes.get(0);
            String host = node.split("-")[0];
            Integer port = Integer.parseInt(node.split("-")[1]);
            String password = redisProperties.getPassword();
            if(StringUtils.isEmpty(password)){
                password = null;
            }
            jedisPool = new JedisPool(jedisPoolConfig, host, port,
                    redisProperties.getConnectionTimeout(),password);
        }

        if (jedisPool == null)
        {
            log.error("初始化JedisPool失败，无法连接到Redis，请确认正确配置了redis服务地址以及redis正常可用。");
            throw new CacheException("-",
                    "无法连接到Redis，请确认正确配置了redis服务地址以及redis正常可用。");
        }
        log.debug("初始化JedisPool实例完成....");
        
        return jedisPool;
    }
    
    /**
     * 集群版本
     * 
     * @return
     * @throws CacheException
     * @throws RedisDBException 
     */
    @Bean
    public JedisCluster getJedisCluster() throws CacheException, RedisDBException
    {
        
        if(!isCluster())
        {
            return null;
        }
        
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisProperties.getPool().getMaxIdle());
        jedisPoolConfig.setMinIdle(redisProperties.getPool().getMinIdle());
        jedisPoolConfig.setMaxTotal(redisProperties.getPool().getMaxActive());
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getPool().getMaxWait());
        jedisPoolConfig.setTestOnBorrow(true);
        
        Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
        List<String> urlList = redisProperties.getHostAndPort();
        for(String url : urlList)
        {
            HostAndPort node = new HostAndPort(url.split("-")[0], Integer.parseInt(url.split("-")[1]));
            nodes.add(node);
        }
        String password = redisProperties.getPassword();
        if(StringUtils.isEmpty(password)){
            password = null;
        }
    
        JedisCluster jedisCluster = new JedisCluster(nodes,
                redisProperties.getConnectionTimeout(),
                redisProperties.getSocketTimeout(),
                redisProperties.getMaxAttempts(), 
                password,
                jedisPoolConfig);
        
        log.info("初始化JedisCluster实例完成....");
        
        return jedisCluster;
    }
}
