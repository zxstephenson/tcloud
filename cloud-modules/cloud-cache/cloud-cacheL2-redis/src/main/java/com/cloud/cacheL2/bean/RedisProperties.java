package com.cloud.cacheL2.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月19日
 */
@Configuration
@ConfigurationProperties(prefix="cloud.cache.cacheL2")
public class RedisProperties
{

    private Integer maxIdle = 8;
    
    private Integer minIdle = 0;
    
    private Integer maxActive = 8;
    
    private Integer maxWait = 3000;
    
    private String nodes = "localhost:6379";
    
    private Integer connectionTimeout = 3000;
    
    private Integer socketTimeout = 3000;
    
    private Integer maxAttempts = 3;
    
    private String password = null;

    /**
     * @return the maxIdle
     */
    public Integer getMaxIdle()
    {
        return maxIdle;
    }

    /**
     * @param maxIdle the maxIdle to set
     */
    public void setMaxIdle(Integer maxIdle)
    {
        this.maxIdle = maxIdle;
    }

    /**
     * @return the minIdle
     */
    public Integer getMinIdle()
    {
        return minIdle;
    }

    /**
     * @param minIdle the minIdle to set
     */
    public void setMinIdle(Integer minIdle)
    {
        this.minIdle = minIdle;
    }

    /**
     * @return the maxActive
     */
    public Integer getMaxActive()
    {
        return maxActive;
    }

    /**
     * @param maxActive the maxActive to set
     */
    public void setMaxActive(Integer maxActive)
    {
        this.maxActive = maxActive;
    }

    /**
     * @return the maxWait
     */
    public Integer getMaxWait()
    {
        return maxWait;
    }

    /**
     * @param maxWait the maxWait to set
     */
    public void setMaxWait(Integer maxWait)
    {
        this.maxWait = maxWait;
    }

    /**
     * @return the nodes
     */
    public String getNodes()
    {
        return nodes;
    }

    /**
     * @param nodes the nodes to set
     */
    public void setNodes(String nodes)
    {
        this.nodes = nodes;
    }

    /**
     * @return the connectionTimeout
     */
    public Integer getConnectionTimeout()
    {
        return connectionTimeout;
    }

    /**
     * @param connectionTimeout the connectionTimeout to set
     */
    public void setConnectionTimeout(Integer connectionTimeout)
    {
        this.connectionTimeout = connectionTimeout;
    }

    /**
     * @return the socketTimeout
     */
    public Integer getSocketTimeout()
    {
        return socketTimeout;
    }

    /**
     * @param socketTimeout the socketTimeout to set
     */
    public void setSocketTimeout(Integer socketTimeout)
    {
        this.socketTimeout = socketTimeout;
    }

    /**
     * @return the maxAttempts
     */
    public Integer getMaxAttempts()
    {
        return maxAttempts;
    }

    /**
     * @param maxAttempts the maxAttempts to set
     */
    public void setMaxAttempts(Integer maxAttempts)
    {
        this.maxAttempts = maxAttempts;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

}
