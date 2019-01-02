package com.cloud.gray.lb;

import java.util.ArrayList;
import java.util.List;

import com.cloud.gray.bean.GrayServiceInfo;
import com.netflix.loadbalancer.Server;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月25日
 */

public interface GrayRule
{

    default public Server chooseServer(Object key,
            GrayLoadBalancer grayLoadBalancer, GrayServiceInfo grayServiceInfo)
    {
        List<Server> serverList = new ArrayList<Server>();
        serverList = grayLoadBalancer.getServerListImpl()
                .getUpdatedListOfServers();// 获取处理之后的serverlist
        serverList = grayLoadBalancer.getFilter()
                .getFilteredListOfServers(serverList);// 获取过滤之后的serverlist

        for (Server server : serverList)
        {
            System.err.println(this.getClass() + "===============>"
                    + server.getHostPort());
        }

        if (serverList != null && serverList.size() > 0)
        {
            return serverList.get(0);
        }

        return null;
    }

}
