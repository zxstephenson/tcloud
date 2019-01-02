package com.cloud.gray.lb;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.cloud.gray.bean.GrayServiceInfo;
import com.cloud.gray.bean.StrategyInfo;
import com.netflix.loadbalancer.Server;

/**
 * 按设置的灰度比例
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月25日
 */
public class GrayWeightRule implements GrayRule
{
    private Random random = new Random();

    @Override
    public Server chooseServer(Object key, GrayLoadBalancer grayLoadBalancer,
            GrayServiceInfo grayServiceInfo)
    {
        List<String> grayInstanceIdList = grayServiceInfo.getInstanceIdList();
        if (grayInstanceIdList == null || grayInstanceIdList.isEmpty())
        {
            return null;
        }
        List<Server> grayInstanceList = new ArrayList<>();
        List<Server> normalInstanceList = new ArrayList<>();
        // 获取全部server集合
        List<Server> allServerList = grayLoadBalancer.getAllServers();
        Server tempServer = null;
        for (Server s : allServerList)
        {
            String instanceId = s.getMetaInfo().getInstanceId();
            for (String grayInstanceId : grayInstanceIdList)
            {
                if (instanceId.equalsIgnoreCase(grayInstanceId))
                {
                    tempServer = s;
                }
            }
            if (tempServer != null)
            {
                grayInstanceList.add(s);
                tempServer = null;
            } else
            {
                normalInstanceList.add(s);
            }
        }

        Server server = null;

        while (server == null)
        {
            if (Thread.interrupted())
            {
                return null;
            }

            // 获取全部server集合
            List<Server> allList = grayLoadBalancer.getAllServers();

            int serverCount = allList.size();
            if (serverCount == 0)
            {
                return null;
            }
            
            int serverIndex = 0, n = 0;
            List<Double> instanceWeightList = calcInstanceWeight(allList, grayServiceInfo.getStrategyInfo());
            Double maxWeight = 100d;
            Double curWeight = random.nextDouble() * maxWeight;
            for (Double d : instanceWeightList)
            {
                if (d >= curWeight)
                {
                    serverIndex = n;
                    break;
                } else
                {
                    n++;
                }
            }

            if (serverIndex == 0)
            {
                int num = random.nextInt(grayInstanceList.size());
                server = grayInstanceList.get(num);
            } else
            {
                int num = random.nextInt(normalInstanceList.size());
                server = normalInstanceList.get(num);
            }

            if (server == null)
            {
                Thread.yield();
                continue;
            }

            if (server.isAlive())
            {
                return (server);
            }

            server = null;
        }

        return GrayRule.super.chooseServer(key, grayLoadBalancer, grayServiceInfo);
    }

    private List<Double> calcInstanceWeight(List<Server> allServerList, StrategyInfo strategyInfo)
    {
        List<Double> instanceWeightList = new ArrayList<>();
        String strategyDetail = strategyInfo.getStrategyDetail();
        Double grayWeight = Double.parseDouble(strategyDetail);
        instanceWeightList.add(grayWeight);
        instanceWeightList.add(100d);

        return instanceWeightList;
    }

}
