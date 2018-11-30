package com.cloud.shiro.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="tcloud.shiro")
public class ShiroProperties
{
    /**
     * 单位秒，默认不设置过期时间
     */
    private int sessionExpireTimeout = -1;

    /**
     * session超时时间，默认30分钟, 单位毫秒
     */
    private long globalSessionTimeout = 30 * 60 * 1000;
    
    /**
     * 最大密码输入错误的次数
     */
    private int maxRetryCount = 5;
    
    /**
     * 当输入密码错误达到上限时，限制验证的时间，默认为1天，单位为秒
     */
    private int limitRetryTimeout = 60 * 60 * 24;
    
    /**
     * @return the globalSessionTimeout
     */
    public long getGlobalSessionTimeout()
    {
        return globalSessionTimeout;
    }

    /**
     * @param globalSessionTimeout the globalSessionTimeout to set
     */
    public void setGlobalSessionTimeout(long globalSessionTimeout)
    {
        this.globalSessionTimeout = globalSessionTimeout;
    }

    /**
     * @return the limitRetryTimeout
     */
    public int getLimitRetryTimeout()
    {
        return limitRetryTimeout;
    }

    /**
     * @param limitRetryTimeout the limitRetryTimeout to set
     */
    public void setLimitRetryTimeout(int limitRetryTimeout)
    {
        this.limitRetryTimeout = limitRetryTimeout;
    }

    /**
     * @return the maxRetryCount
     */
    public int getMaxRetryCount()
    {
        return maxRetryCount;
    }

    /**
     * @param maxRetryCount the maxRetryCount to set
     */
    public void setMaxRetryCount(int maxRetryCount)
    {
        this.maxRetryCount = maxRetryCount;
    }

    /**
     * @return the sessionExpireTimeout
     */
    public int getSessionExpireTimeout()
    {
        return sessionExpireTimeout;
    }

    /**
     * @param sessionExpireTimeout the sessionExpireTimeout to set
     */
    public void setSessionExpireTimeout(int sessionExpireTimeout)
    {
        this.sessionExpireTimeout = sessionExpireTimeout;
    }

}
