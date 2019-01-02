package com.cloud.gray.lb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.cloud.common.utils.ReflectionUtil;
import com.cloud.gray.bean.GrayServiceInfo;
import com.cloud.gray.bean.GrayServicesRulesInfo;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;
import com.netflix.loadbalancer.ServerListFilter;
import com.netflix.loadbalancer.ServerListUpdater;
import com.netflix.loadbalancer.ZoneAwareLoadBalancer;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月24日
 */

public class GrayLoadBalancer extends ZoneAwareLoadBalancer<Server>
{
    @Autowired
    private GrayServicesRulesInfo grayInfo;

    public GrayLoadBalancer(IClientConfig clientConfig, IRule rule, IPing ping,
            ServerList<Server> serverList, ServerListFilter<Server> filter,
            ServerListUpdater serverListUpdater)
    {
        super(clientConfig, rule, ping, serverList, filter, serverListUpdater);
    }

    @Override
    public Server chooseServer(Object key)
    {
        // 利用Dynamic负载类中的方法，可以在运行时更新服务实例清单
        List<Server> serverList = new ArrayList<Server>();

        serverList = super.getServerListImpl().getUpdatedListOfServers();// 获取处理之后的serverlist
        serverList = super.getFilter().getFilteredListOfServers(serverList);// 获取过滤之后的serverlist
        if (serverList == null || serverList.isEmpty())
        {
            return null;
        }
        Server server = serverList.stream().findAny().get();
        String serviceId = server.getMetaInfo().getAppName();
        Map<String, GrayServiceInfo> map = grayInfo.getServiceGrayStrategyMap();

        GrayServiceInfo grayServiceInfo = map.get(serviceId.toLowerCase());
        if (grayServiceInfo == null)
        {
            grayServiceInfo = map.get(serviceId.toUpperCase());
        }

        if (grayServiceInfo == null || !grayServiceInfo.isGrayable())
        {
            return super.chooseServer(key);
        }
        
        String className = grayServiceInfo.getStrategyInfo().getStrategyImpClassName();
        Object instance = ReflectionUtil.createInstance(className);
        if (instance instanceof GrayRule)
        {
            GrayRule grayRule = (GrayRule) instance;
            Server s = grayRule.chooseServer(key, this, grayServiceInfo);
            if (s != null)
            {
                return s;
            }
        }

        return super.chooseServer(key);
    }
}
