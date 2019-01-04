/**
 * <p>文件名称: JsonUtils.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class JsonUtils
{

    public static JSONObject getJSONObject(String json)
    {
        return JSONObject.parseObject(json);
    }

    public static int getInt(String json, String key, int defaultVal)
    {
        JSONObject jsonObject = JSONObject.parseObject(json);
        try
        {
            if (jsonObject == null)
            {
                return defaultVal;
            }
            if (jsonObject.containsKey(key))
            {
                return jsonObject.getInteger(key);
            } else
            {
                return defaultVal;
            }
        } catch (Exception e)
        {
            return defaultVal;
        }
    }

    public static Long getLong(String json, String key, Long defaultVal)
    {
        JSONObject jsonObject = JSONObject.parseObject(json);
        try
        {
            if (jsonObject == null)
            {
                return defaultVal;
            }
            if (jsonObject.containsKey(key))
            {
                return jsonObject.getLong(key);
            } else
            {
                return defaultVal;
            }
        } catch (Exception e)
        {
            return defaultVal;
        }
    }

    public static double getDouble(String json, String key, double defaultVal)
    {
        JSONObject jsonObject = JSONObject.parseObject(json);
        try
        {
            if (jsonObject == null)
            {
                return defaultVal;
            }
            if (jsonObject.containsKey(key))
            {
                return jsonObject.getDouble(key);
            } else
            {
                return defaultVal;
            }
        } catch (Exception e)
        {
            return defaultVal;
        }
    }

    public static String getString(String json, String key, String defaultVal)
    {
        JSONObject jsonObject = JSONObject.parseObject(json);
        try
        {
            if (jsonObject == null)
            {
                return defaultVal;
            }
            if (jsonObject.containsKey(key))
            {
                return jsonObject.getString(key);
            } else
            {
                return defaultVal;
            }
        } catch (Exception e)
        {
            return defaultVal;
        }
    }

    public static Integer getInteger(String json, String key,
            Integer defaultVal)
    {
        return getInt(json, key, defaultVal);
    }

}
