/**
 * <p>文件名称: CompensateDaoImpl.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.compensate.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.cloud.core.utils.DateUtil;
import com.cloud.tm.compensate.dao.CompensateDao;
import com.cloud.tm.compensate.model.TransactionCompensateMsg;
import com.cloud.tm.config.ConfigReader;
import com.cloud.tm.redis.service.RedisServerService;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
@Service
public class CompensateDaoImpl implements CompensateDao
{

    @Autowired
    private RedisServerService redisServerService;

    @Autowired
    private ConfigReader configReader;

    @Override
    public String saveCompensateMsg(
            TransactionCompensateMsg transactionCompensateMsg)
    {

        String name = String.format("%s%s:%s:%s.json",
                configReader.getKeyPrefixCompensate(),
                transactionCompensateMsg.getModel(),
                DateUtil.getCurrentDateFormat(),
                transactionCompensateMsg.getGroupId());

        String json = JSON.toJSONString(transactionCompensateMsg);

        redisServerService.saveCompensateMsg(name, json);

        return name;
    }

    @Override
    public List<String> loadCompensateKeys()
    {
        String key = configReader.getKeyPrefixCompensate() + "*";
        return redisServerService.getKeys(key);
    }

    @Override
    public boolean hasCompensate()
    {
        String key = configReader.getKeyPrefixCompensate() + "*";
        List<String> keys = redisServerService.getKeys(key);
        return keys != null && keys.size() > 0;
    }

    @Override
    public List<String> loadCompensateTimes(String model)
    {
        String key = configReader.getKeyPrefixCompensate() + model + ":*";
        List<String> keys = redisServerService.getKeys(key);
        List<String> times = new ArrayList<String>();
        for (String k : keys)
        {
            if (k.length() > 36)
            {
                String time = k.substring(k.length() - 24, k.length() - 14);
                if (!times.contains(time))
                {
                    times.add(time);
                }
            }
        }
        return times;
    }

    @Override
    public List<String> loadCompensateByModelAndTime(String path)
    {
        String key = String.format("%s%s*",
                configReader.getKeyPrefixCompensate(), path);
        List<String> keys = redisServerService.getKeys(key);
        List<String> values = redisServerService.getValuesByKeys(keys);
        return values;
    }

    @Override
    public String getCompensate(String path)
    {
        String key = String.format("%s%s.json",
                configReader.getKeyPrefixCompensate(), path);
        return redisServerService.getValueByKey(key);
    }

    @Override
    public void deleteCompensateByPath(String path)
    {
        String key = String.format("%s%s.json",
                configReader.getKeyPrefixCompensate(), path);
        redisServerService.deleteKey(key);
    }

    @Override
    public void deleteCompensateByKey(String key)
    {
        redisServerService.deleteKey(key);
    }

    @Override
    public String getCompensateByGroupId(String groupId)
    {
        String key = String.format("%s*%s.json",
                configReader.getKeyPrefixCompensate(), groupId);
        List<String> keys = redisServerService.getKeys(key);
        if (keys != null && keys.size() == 1)
        {
            String k = keys.get(0);
            return redisServerService.getValueByKey(k);
        }
        return null;
    }
}
