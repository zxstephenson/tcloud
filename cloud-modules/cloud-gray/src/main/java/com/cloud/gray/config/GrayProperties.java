package com.cloud.gray.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月26日
 */
@Configuration
@ConfigurationProperties(prefix = "jisp.gray")
public class GrayProperties
{
    /**
     * 是否灰度
     */
    private boolean grayable = false;

    /**
     * 灰度规则
     */
    private String grayStrategyClassName;
    
    /**
     * 规则的详细信息
     */
    private String grayStrategyDetail;

    public boolean isGrayable()
    {
        return grayable;
    }

    public void setGrayable(boolean grayable)
    {
        this.grayable = grayable;
    }

    public String getGrayStrategyClassName()
    {
        return grayStrategyClassName;
    }

    public void setGrayStrategyClassName(String grayStrategyClassName)
    {
        this.grayStrategyClassName = grayStrategyClassName;
    }

    public String getGrayStrategyDetail()
    {
        return grayStrategyDetail;
    }

    public void setGrayStrategyDetail(String grayStrategyDetail)
    {
        this.grayStrategyDetail = grayStrategyDetail;
    }

}
