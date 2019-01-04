/**
 * <p>文件名称: RedisServerService.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.redis.service;

import java.util.List;

import com.cloud.tm.netty.model.TxGroup;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public interface RedisServerService
{

    String loadNotifyJson();

    void saveTransaction(String key, String json);

    TxGroup getTxGroupByKey(String key);

    void saveCompensateMsg(String name, String json);

    List<String> getKeys(String key);

    List<String> getValuesByKeys(List<String> keys);

    String getValueByKey(String key);

    void deleteKey(String key);

    void saveLoadBalance(String groupName, String key, String data);

    String getLoadBalance(String groupName, String key);

}
