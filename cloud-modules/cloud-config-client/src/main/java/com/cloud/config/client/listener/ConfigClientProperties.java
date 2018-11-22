package com.cloud.config.client.listener;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置中心客户端配置类
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月22日
 */
@Configuration
@ConfigurationProperties(prefix="tcloud.config.client")
public class ConfigClientProperties
{
    /**
     * 配置文件备份信息
     */
    private Backup backup = new Backup();
    
    /**
     * @return the backup
     */
    public Backup getBackup()
    {
        return backup;
    }

    /**
     * @param backup the backup to set
     */
    public void setBackup(Backup backup)
    {
        this.backup = backup;
    }

    public static class Backup{
        
        /**
         * 是否启用备份，默认为true
         */
        private boolean enable = true;
        
        /**
         * 文件备份路径，默认当前服务的classpath下
         */
        private String path = "";

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
         * @return the path
         */
        public String getPath()
        {
            return path;
        }

        /**
         * @param path the path to set
         */
        public void setPath(String path)
        {
            this.path = path;
        }
        
    }
}
