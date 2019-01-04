/**
 * <p>文件名称: ConditionUtils.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core.utils.task;

import org.apache.commons.lang.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class ConditionUtils
{

    private static ConditionUtils instance = null;

    private Map<String, Task> taskMap = new ConcurrentHashMap<String, Task>();

    private ConditionUtils()
    {

    }

    public static ConditionUtils getInstance()
    {
        if (instance == null)
        {
            synchronized (ConditionUtils.class)
            {
                if (instance == null)
                {
                    instance = new ConditionUtils();
                }
            }
        }
        return instance;
    }

    public Task createTask(String key)
    {
        Task task = new Task();
        task.setKey(key);
        taskMap.put(key, task);
        return task;
    }

    public Task getTask(String key)
    {
        return taskMap.get(key);
    }

    public void removeKey(String key)
    {
        if (StringUtils.isNotEmpty(key))
        {
            taskMap.remove(key);
        }
    }

    public boolean hasKey(String key)
    {
        return taskMap.containsKey(key);
    }
}
