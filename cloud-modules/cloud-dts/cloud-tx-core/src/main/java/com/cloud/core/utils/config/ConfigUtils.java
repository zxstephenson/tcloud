/**
 * <p>文件名称: ConfigUtils.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core.utils.config;

/**
 * 配置文件工具类 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class ConfigUtils
{

    public static String getString(String filePath, String key)
    {
        ConfigHelper helper = new ConfigHelper(filePath);
        return helper.getStringValue(key);
    }

    public static int getInt(String filePath, String key)
    {
        ConfigHelper helper = new ConfigHelper(filePath);
        return helper.getIntValue(key);
    }

    public static void setProperty(String filePath, String key, Object val)
    {
        ConfigHelper helper = new ConfigHelper(filePath);
        helper.setProperty(key, val);
    }

    public static String[] getStringArrayValue(String filePath, String key)
    {
        ConfigHelper helper = new ConfigHelper(filePath);
        return helper.getStringArrayValue(key);
    }

}
