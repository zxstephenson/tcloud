package com.cloud.ribbon.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月21日
 */
@Configuration
@ConfigurationProperties(prefix="tcloud.ribbon")
public class RibbonProperties
{
    /**
     * 连接超时时间，单位毫秒
     */
    private int connectionTimeout = 1000;
    
    /**
     * 读取返回数据超时时间，单位毫秒
     */
    private int readTimeout = 2000;
    
    /**
     * 指定最大总连接数
     */
    private int maxConnTotal = 100;
    
    /**
     * 指定每个实例的最大连接数
     */
    private int maxConnPerRoute = 50;
    
    
    private Retry retry = new Retry(); 

    /**
     * @return the connectionTimeout
     */
    public int getConnectionTimeout()
    {
        return connectionTimeout;
    }

    /**
     * @param connectionTimeout the connectionTimeout to set
     */
    public void setConnectionTimeout(int connectionTimeout)
    {
        this.connectionTimeout = connectionTimeout;
    }

    /**
     * @return the readTimeout
     */
    public int getReadTimeout()
    {
        return readTimeout;
    }

    /**
     * @param readTimeout the readTimeout to set
     */
    public void setReadTimeout(int readTimeout)
    {
        this.readTimeout = readTimeout;
    }

    /**
     * @return the maxConnTotal
     */
    public int getMaxConnTotal()
    {
        return maxConnTotal;
    }

    /**
     * @param maxConnTotal the maxConnTotal to set
     */
    public void setMaxConnTotal(int maxConnTotal)
    {
        this.maxConnTotal = maxConnTotal;
    }

    /**
     * @return the maxConnPerRoute
     */
    public int getMaxConnPerRoute()
    {
        return maxConnPerRoute;
    }

    /**
     * @param maxConnPerRoute the maxConnPerRoute to set
     */
    public void setMaxConnPerRoute(int maxConnPerRoute)
    {
        this.maxConnPerRoute = maxConnPerRoute;
    }
    
    /**
     * @return the retry
     */
    public Retry getRetry()
    {
        return retry;
    }

    /**
     * @param retry the retry to set
     */
    public void setRetry(Retry retry)
    {
        this.retry = retry;
    }

    public static class Retry{
        private boolean enable = true;
        
        private int maxAttempts = 0;

        private List<String> retryableExceptions = new ArrayList<String>();
        /**
         * @return the enable
         */
        public boolean isEnable()
        {
            return enable;
        }

        /**
         * @param enable the enable to set
         */
        public void setEnable(boolean enable)
        {
            this.enable = enable;
        }

        /**
         * @return the maxAttempts
         */
        public int getMaxAttempts()
        {
            return maxAttempts;
        }

        /**
         * @param maxAttempts the maxAttempts to set
         */
        public void setMaxAttempts(int maxAttempts)
        {
            this.maxAttempts = maxAttempts;
        }

        /**
         * @return the retryableExceptions
         */
        public List<String> getRetryableExceptions()
        {
            return retryableExceptions;
        }

        /**
         * @param retryableExceptions the retryableExceptions to set
         */
        public void setRetryableExceptions(List<String> retryableExceptions)
        {
            this.retryableExceptions = retryableExceptions;
        }
        
    }
    
}
