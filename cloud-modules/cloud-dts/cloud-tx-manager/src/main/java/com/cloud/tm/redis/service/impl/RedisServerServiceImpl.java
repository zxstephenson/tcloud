/**
 * <p>文件名称: RedisServerServiceImpl.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.redis.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.tm.config.ConfigReader;
import com.cloud.tm.netty.model.TxGroup;
import com.cloud.tm.redis.service.RedisServerService;

/**
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月5日
 */
@Service
public class RedisServerServiceImpl implements RedisServerService
{

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ConfigReader configReader;

    public String loadNotifyJson()
    {
        Set<String> keys = redisTemplate.keys(configReader.getKeyPrefixCompensate() + "*");
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        JSONArray jsonArray = new JSONArray();
        for (String key : keys)
        {
            String json = value.get(key);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("key", key);
            jsonObject.put("value", JSONObject.parse(json));
            jsonArray.add(jsonObject);
        }
        return jsonArray.toJSONString();
    }

    @Override
    public void saveTransaction(String key, String json)
    {
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        value.set(key, json, configReader.getRedisSaveMaxTime(), TimeUnit.SECONDS);
    }

    @Override
    public TxGroup getTxGroupByKey(String key)
    {
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        String json = value.get(key);
        if (StringUtils.isEmpty(json))
        {
            return null;
        }
        return TxGroup.parser(json);
    }

    @Override
    public void saveCompensateMsg(String name, String json)
    {
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        value.set(name, json);
    }

    @Override
    public List<String> getKeys(String key)
    {
        Set<String> keys = redisTemplate.keys(key);
        List<String> list = new ArrayList<String>();
        for (String k : keys)
        {
            list.add(k);
        }
        return list;
    }

    @Override
    public List<String> getValuesByKeys(List<String> keys)
    {
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        List<String> list = new ArrayList<>();
        for (String key : keys)
        {
            String json = value.get(key);
            list.add(json);
        }
        return list;
    }

    @Override
    public String getValueByKey(String key)
    {
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        return value.get(key);
    }

    @Override
    public void deleteKey(String key)
    {
        redisTemplate.delete(key);
    }

    @Override
    public void saveLoadBalance(String groupName, String key, String data)
    {
        HashOperations<String, String, String> value = redisTemplate.opsForHash();
        value.put(groupName, key, data);
    }

    @Override
    public String getLoadBalance(String groupName, String key)
    {
        HashOperations<String, String, String> value = redisTemplate.opsForHash();
        return value.get(groupName, key);
    }
}
