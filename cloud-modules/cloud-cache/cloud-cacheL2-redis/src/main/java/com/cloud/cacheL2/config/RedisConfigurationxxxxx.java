/**
 * <p>文件名称: RedisConfiguration.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.cacheL2.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.cloud.cacheL2.bean.RedisProperties;
import com.cloud.cacheL2.dao.impl.RedisDaoImplCluster;
import com.cloud.cacheL2.dao.impl.RedisDaoImplSingle;
import com.cloud.common.cache.cacheL2.CacheL2Dao;
import com.cloud.common.cache.exception.CacheException;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月19日
 */
public class RedisConfigurationxxxxx 
{
    private static final Logger logger = LoggerFactory.getLogger(RedisConfigurationxxxxx.class);
    
    private RedisProperties redisProperties;
    
    @Bean
    public CacheL2Dao gerRedisDao(){
        
        if(isCluster()){
            return new RedisDaoImplCluster();
        }else{
            return new RedisDaoImplSingle();
        }
    }
    
    @Bean
    public JedisCluster getJedisCluster(){
        if (redisProperties == null)
        {
            return null;
        }
        
        if(!isCluster())
        {
            return null;
        }
        
        logger.info("初始化JedisCluster实例开始....");
        
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
        jedisPoolConfig.setMinIdle(redisProperties.getMinIdle());
        jedisPoolConfig.setMaxTotal(redisProperties.getMaxActive());
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getMaxWait());
        jedisPoolConfig.setTestOnBorrow(true);

        String hosts = redisProperties.getNodes();
        List<String> hostList = new ArrayList<>();
        if (StringUtils.isEmpty(hosts))
        {
            hostList = new ArrayList<>(Arrays.asList(hosts.split(",")));
        }

        Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
        for (String url : hostList)
        {
            String host = url.split(":")[0];
            int port = Integer.parseInt(url.split(":")[1]);
            HostAndPort node = new HostAndPort(host, port);
            nodes.add(node);
        }
        JedisCluster jedisCluster = new JedisCluster(nodes,
                redisProperties.getConnectionTimeout(),
                redisProperties.getSocketTimeout(),
                redisProperties.getMaxAttempts(), redisProperties.getPassword(),
                jedisPoolConfig);
        
        logger.info("初始化JedisCluster实例完成....");
        return jedisCluster;
    }
    
    @Bean
    public JedisPool getJedisPool() throws CacheException{


        if (redisProperties == null)
        {
            logger.error("未正确初始化Redis配置信息。");
            return null;
        }
        
        if(isCluster()){
            return null;
        }
        
        logger.info("初始化JedisPool实例开始....");
        
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
        jedisPoolConfig.setMinIdle(redisProperties.getMinIdle());
        jedisPoolConfig.setMaxTotal(redisProperties.getMaxActive());
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getMaxWait());
        // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        jedisPoolConfig.setTestOnBorrow(true);

        String nodes = redisProperties.getNodes();
        List<String> nodeList = new ArrayList<>();
        if (!StringUtils.isEmpty(nodes))
        {
            nodeList = new ArrayList<>(Arrays.asList(nodes.split(",")));
        }

        if(nodeList.size() == 0){
            logger.error("未获取到redis节点配置信息，请查看配置cloud.cache.cacheL2.nodes是否正确配置！");
            return null;
        }
        String[] nodeInfo = nodeList.get(0).split(":");
        String host = nodeInfo[0];
        int port = Integer.parseInt(nodeInfo[1]);
        JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port,
                redisProperties.getConnectionTimeout(),
                redisProperties.getPassword());

        /*if (jedisPool == null)
        {
            logger.error("初始化JedisPool失败，无法连接到Redis，请确认正确配置了redis服务地址以及redis正常可用。");
            throw new CacheException("-",
                    "无法连接到Redis，请确认正确配置了redis服务地址以及redis正常可用。");
        }*/
        jedisPool.getResource();
        logger.info("初始化JedisPool实例完成....");
        return jedisPool;
    
    }
    
    
    /**
     * 判断当前redis是否为集群，如果节点数超过1个，则认为是集群，如果节点数为1，则默认为单机模式
     * @return
     */
    private boolean isCluster(){
        String hosts = redisProperties.getNodes();
        List<String> hostList = new ArrayList<>();
        if (StringUtils.isEmpty(hosts))
        {
            hostList = new ArrayList<>(Arrays.asList(hosts.split(",")));
            if(hostList.size() > 1){
                return true;
            }
        }
        
        return false;
    }
    
}
