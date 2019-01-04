/**
 * <p>文件名称: ActionUMIServiceImpl.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.netty.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.tm.framework.utils.SocketManager;
import com.cloud.tm.manager.ModelInfoManager;
import com.cloud.tm.model.ModelInfo;
import com.cloud.tm.netty.service.IActionService;

import org.springframework.stereotype.Service;

/**
 * 上传模块信息 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
@Service(value = "umi")
public class ActionUMIServiceImpl implements IActionService
{

    @Override
    public String execute(String channelAddress, String key, JSONObject params)
    {
        String res = "1";

        String uniqueKey = params.getString("u");
        String ipAddress = params.getString("i");
        String model = params.getString("m");

        ModelInfo modelInfo = new ModelInfo();
        modelInfo.setChannelName(channelAddress);
        modelInfo.setIpAddress(ipAddress);
        modelInfo.setModel(model);
        modelInfo.setUniqueKey(uniqueKey);

        ModelInfoManager.getInstance().addModelInfo(modelInfo);

        SocketManager.getInstance().onLine(channelAddress, uniqueKey);

        return res;
    }
}
