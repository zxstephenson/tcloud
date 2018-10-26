package com.cloud.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月19日
 */
@Configuration
@ConfigurationProperties(prefix="jisp.api")
public class ContextProperties
{
    private String packagePath;

    /**
     * @return the packagePath
     */
    public String getPackagePath()
    {
        return packagePath;
    }

    /**
     * @param packagePath the packagePath to set
     */
    public void setPackagePath(String packagePath)
    {
        this.packagePath = packagePath;
    }
    
}
