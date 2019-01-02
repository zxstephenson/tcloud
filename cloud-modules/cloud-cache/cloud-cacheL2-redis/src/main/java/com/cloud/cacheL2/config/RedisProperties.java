/**
 * <p>文件名称: RedisConfig.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1.0</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
 package com.cloud.cacheL2.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2019年1月2日
 */
@Configuration
@ConfigurationProperties(prefix="tcloud.redis")
public class RedisProperties
{
    private Pool pool = new Pool();
    
    private List<String> hostAndPort = new ArrayList<String>();
    
    private Integer connectionTimeout = 3000;
    
    private Integer socketTimeout = 3000;
    
    private Integer maxAttempts = 3;
    
    private String password;
    
    
    public Pool getPool()
    {
        return pool;
    }

    public void setPool(Pool pool)
    {
        this.pool = pool;
    }

    public List<String> getHostAndPort()
    {
        return hostAndPort;
    }

    public void setHostAndPort(List<String> hostAndPort)
    {
        this.hostAndPort = hostAndPort;
    }

    public Integer getConnectionTimeout()
    {
        return connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout)
    {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getSocketTimeout()
    {
        return socketTimeout;
    }
    
    public void setSocketTimeout(Integer socketTimeout)
    {
        this.socketTimeout = socketTimeout;
    }

    public Integer getMaxAttempts()
    {
        return maxAttempts;
    }

    public void setMaxAttempts(Integer maxAttempts)
    {
        this.maxAttempts = maxAttempts;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public static class Pool{
        private int maxIdle = 8;
        
        private int minIdle = 0;
        
        private int maxActive = 8;
        
        private int maxWait = 3000;

        public int getMaxIdle()
        {
            return maxIdle;
        }

        public void setMaxIdle(int maxIdle)
        {
            this.maxIdle = maxIdle;
        }

        public int getMinIdle()
        {
            return minIdle;
        }

        public void setMinIdle(int minIdle)
        {
            this.minIdle = minIdle;
        }

        public int getMaxActive()
        {
            return maxActive;
        }

        public void setMaxActive(int maxActive)
        {
            this.maxActive = maxActive;
        }

        public int getMaxWait()
        {
            return maxWait;
        }

        public void setMaxWait(int maxWait)
        {
            this.maxWait = maxWait;
        }
        
    }
    
}
