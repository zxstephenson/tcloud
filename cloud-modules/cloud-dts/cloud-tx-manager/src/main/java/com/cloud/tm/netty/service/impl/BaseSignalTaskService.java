/**
 * <p>文件名称: BaseSignalTaskService.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.netty.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.core.utils.task.ConditionUtils;
import com.cloud.core.utils.task.IBack;
import com.cloud.core.utils.task.Task;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class BaseSignalTaskService
{

    public String execute(String channelAddress, String key, JSONObject params)
    {
        String res = "";
        final String data = params.getString("d");
        Task task = ConditionUtils.getInstance().getTask(key);
        if (task != null)
        {
            task.setBack(new IBack()
            {
                @Override
                public Object doing(Object... objs) throws Throwable
                {
                    return data;
                }
            });
            task.signalTask();
        }
        return res;
    }
}
