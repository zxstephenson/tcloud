/**
 * <p>文件名称: ApiTxManagerServiceImpl.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.api.service.impl;

import com.cloud.tm.api.service.ApiTxManagerService;
import com.cloud.tm.compensate.model.TransactionCompensateMsg;
import com.cloud.tm.compensate.service.CompensateService;
import com.cloud.tm.config.ConfigReader;
import com.cloud.tm.manager.service.MicroService;
import com.cloud.tm.manager.service.TxManagerSenderService;
import com.cloud.tm.manager.service.TxManagerService;
import com.cloud.tm.model.TxServer;
import com.cloud.tm.model.TxState;

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
public class ApiTxManagerServiceImpl implements ApiTxManagerService
{

    @Autowired
    private TxManagerService managerService;

    @Autowired
    private MicroService eurekaService;

    @Autowired
    private CompensateService compensateService;

    @Autowired
    private TxManagerSenderService txManagerSenderService;

    @Autowired
    private ConfigReader configReader;

    @Override
    public TxServer getServer()
    {
        return eurekaService.getServer();
    }

    @Override
    public int cleanNotifyTransaction(String groupId, String taskId)
    {
        return managerService.cleanNotifyTransaction(groupId, taskId);
    }

    @Override
    public boolean sendCompensateMsg(long currentTime, String groupId,
            String model, String address, String uniqueKey, String className,
            String methodStr, String data, int time, int startError)
    {
        TransactionCompensateMsg transactionCompensateMsg = new TransactionCompensateMsg(
                currentTime, groupId, model, address, uniqueKey, className,
                methodStr, data, time, 0, startError);
        return compensateService.saveCompensateMsg(transactionCompensateMsg);
    }

    @Override
    public String sendMsg(String model, String msg)
    {
        return txManagerSenderService.sendMsg(model, msg,
                configReader.getTransactionNettyDelayTime());
    }

    @Override
    public TxState getState()
    {
        return eurekaService.getState();
    }
}
