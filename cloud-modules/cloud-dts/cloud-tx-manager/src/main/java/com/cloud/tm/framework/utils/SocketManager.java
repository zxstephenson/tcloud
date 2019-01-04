/**
 * <p>文件名称: SocketManager.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.framework.utils;

import io.netty.channel.Channel;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.cloud.tm.constants.Constants;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class SocketManager
{

    /**
     * 最大连接数
     */
    private int maxConnection = Constants.maxConnection;

    /**
     * 当前连接数
     */
    private int nowConnection;

    /**
     * 允许连接请求 true允许 false拒绝
     */
    private boolean allowConnection = true;

    private List<Channel> clients = null;

    private Map<String, String> lines = null;

    private static SocketManager manager = null;

    public static SocketManager getInstance()
    {
        if (manager == null)
        {
            synchronized (SocketManager.class)
            {
                if (manager == null)
                {
                    manager = new SocketManager();
                }
            }
        }
        return manager;
    }

    public Channel getChannelByModelName(String name)
    {
        for (Channel channel : clients)
        {
            String modelName = channel.remoteAddress().toString();

            if (modelName.equals(name))
            {
                return channel;
            }
        }
        return null;
    }

    private SocketManager()
    {
        clients = new CopyOnWriteArrayList<Channel>();
        lines = new ConcurrentHashMap<String, String>();
    }

    public void addClient(Channel client)
    {
        clients.add(client);
        nowConnection = clients.size();

        allowConnection = (maxConnection != nowConnection);
    }

    public void removeClient(Channel client)
    {
        clients.remove(client);
        nowConnection = clients.size();

        allowConnection = (maxConnection != nowConnection);
    }

    public int getMaxConnection()
    {
        return maxConnection;
    }

    public int getNowConnection()
    {
        return nowConnection;
    }

    public boolean isAllowConnection()
    {
        return allowConnection;
    }

    public void outLine(String modelName)
    {
        lines.remove(modelName);
    }

    public void onLine(String modelName, String uniqueKey)
    {
        lines.put(modelName, uniqueKey);
    }

    public Channel getChannelByUniqueKey(String uniqueKey)
    {
        for (Channel channel : clients)
        {
            String modelName = channel.remoteAddress().toString();
            String value = lines.get(modelName);
            if (uniqueKey.equals(value))
            {
                return channel;
            }
        }
        return null;
    }
}
