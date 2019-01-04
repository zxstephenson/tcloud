/**
 * <p>文件名称: ConfigHelper.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core.utils.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * 配置文件读取工具类助手 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class ConfigHelper
{

    private PropertiesConfiguration propertiesConfiguration = null;

    public String getStringValue(String key)
    {
        return propertiesConfiguration.getString(key);
    }

    public String[] getStringArrayValue(String key)
    {
        return propertiesConfiguration.getStringArray(key);
    }

    public void setProperty(String key, Object val)
    {
        propertiesConfiguration.setProperty(key, val);
        try
        {
            propertiesConfiguration.save();
        } catch (ConfigurationException e)
        {
            e.printStackTrace();
        }
    }

    public int getIntValue(String key)
    {
        return propertiesConfiguration.getInt(key);
    }

    public float getFloatValue(String key)
    {
        return propertiesConfiguration.getFloat(key);
    }

    public ConfigHelper(String propertyPath)
    {
        try
        {
            propertiesConfiguration = new PropertiesConfiguration(propertyPath);
        } catch (Exception e)
        {
            throw new RuntimeException(
                    "Please configure check  file: " + propertyPath);
        }
    }

}
