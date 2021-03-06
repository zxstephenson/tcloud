/**
 * <p>文件名称: ActionATGServiceImpl.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.netty.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.tm.manager.service.TxManagerService;
import com.cloud.tm.netty.model.TxGroup;
import com.cloud.tm.netty.service.IActionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 添加事务组 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
@Service(value = "atg")
public class ActionATGServiceImpl implements IActionService
{

    @Autowired
    private TxManagerService txManagerService;

    @Override
    public String execute(String channelAddress, String key, JSONObject params)
    {
        String res = "";
        String groupId = params.getString("g");
        String taskId = params.getString("t");
        String methodStr = params.getString("ms");
        int isGroup = params.getInteger("s");

        TxGroup txGroup = txManagerService.addTransactionGroup(groupId, taskId,
                isGroup, channelAddress, methodStr);

        if (txGroup != null)
        {
            txGroup.setNowTime(System.currentTimeMillis());
            res = txGroup.toJsonString(false);
        } else
        {
            res = "";
        }
        return res;
    }
}
