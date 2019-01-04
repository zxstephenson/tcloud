/**
 * <p>文件名称: LoadBalanceServiceImpl.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.manager.service.impl;

import com.cloud.tm.config.ConfigReader;
import com.cloud.tm.manager.service.LoadBalanceService;
import com.cloud.tm.model.LoadBalanceInfo;
import com.cloud.tm.redis.service.RedisServerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
@Service
public class LoadBalanceServiceImpl implements LoadBalanceService
{

    @Autowired
    private RedisServerService redisServerService;

    @Autowired
    private ConfigReader configReader;

    @Override
    public boolean put(LoadBalanceInfo loadBalanceInfo)
    {
        String groupName = getLoadBalanceGroupName(
                loadBalanceInfo.getGroupId());
        redisServerService.saveLoadBalance(groupName, loadBalanceInfo.getKey(),
                loadBalanceInfo.getData());
        return true;
    }

    @Override
    public LoadBalanceInfo get(String groupId, String key)
    {
        String groupName = getLoadBalanceGroupName(groupId);
        String bytes = redisServerService.getLoadBalance(groupName, key);
        if (bytes == null)
        {
            return null;
        }

        LoadBalanceInfo loadBalanceInfo = new LoadBalanceInfo();
        loadBalanceInfo.setGroupId(groupId);
        loadBalanceInfo.setKey(key);
        loadBalanceInfo.setData(bytes);
        return loadBalanceInfo;
    }

    @Override
    public boolean remove(String groupId)
    {
        redisServerService.deleteKey(getLoadBalanceGroupName(groupId));
        return true;
    }

    @Override
    public String getLoadBalanceGroupName(String groupId)
    {
        return configReader.getKeyPrefixLoadbalance() + groupId;
    }
}
