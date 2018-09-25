package com.cloud.ribbon.config;

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
    
}
