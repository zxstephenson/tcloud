/**
 * <p>文件名称: ChannelSender.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.model;

import org.apache.commons.lang.StringUtils;

import com.cloud.core.utils.http.HttpUtils;
import com.cloud.core.utils.task.IBack;
import com.cloud.core.utils.task.Task;
import com.cloud.tm.framework.utils.SocketUtils;

import io.netty.channel.Channel;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class ChannelSender
{

    private Channel channel;

    private String address;

    private String modelName;

    public void setModelName(String modelName)
    {
        this.modelName = modelName;
    }

    public void setChannel(Channel channel)
    {
        this.channel = channel;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public void send(String msg)
    {
        if (channel != null)
        {
            SocketUtils.sendMsg(channel, msg);
        }

    }

    public void send(String msg, Task task)
    {
        if (channel != null)
        {
            SocketUtils.sendMsg(channel, msg);
        } else
        {
            String url = String.format("http://%s/tx/manager/sendMsg", address);
            final String res = HttpUtils.post(url,
                    "msg=" + msg + "&model=" + modelName);
            if (StringUtils.isNotEmpty(res))
            {
                if (task != null)
                {
                    task.setBack(new IBack()
                    {
                        @Override
                        public Object doing(Object... objs) throws Throwable
                        {
                            return res;
                        }
                    });
                    task.signalTask();
                }
            } else
            {
                if (task != null)
                {
                    task.setBack(new IBack()
                    {
                        @Override
                        public Object doing(Object... objs) throws Throwable
                        {
                            return "-2";
                        }
                    });
                    task.signalTask();
                }
            }
        }

    }
}
