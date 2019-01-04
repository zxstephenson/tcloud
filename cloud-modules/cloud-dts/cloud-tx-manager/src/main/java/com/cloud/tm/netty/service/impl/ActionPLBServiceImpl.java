/**
 * <p>文件名称: ActionPLBServiceImpl.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.netty.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cloud.tm.manager.service.LoadBalanceService;
import com.cloud.tm.model.LoadBalanceInfo;
import com.cloud.tm.netty.service.IActionService;

/**
 * 添加负载模块信息 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
@Service(value = "plb")
public class ActionPLBServiceImpl implements IActionService
{

    @Autowired
    private LoadBalanceService loadBalanceService;

    @Override
    public String execute(String channelAddress, String key, JSONObject params)
    {

        String groupId = params.getString("g");
        String k = params.getString("k");
        String data = params.getString("d");

        LoadBalanceInfo loadBalanceInfo = new LoadBalanceInfo();
        loadBalanceInfo.setData(data);
        loadBalanceInfo.setKey(k);
        loadBalanceInfo.setGroupId(groupId);
        boolean ok = loadBalanceService.put(loadBalanceInfo);

        return ok ? "1" : "0";
    }
}
