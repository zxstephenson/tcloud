/**
 * <p>文件名称: TxManagerServiceImpl.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.manager.service.impl;

import com.cloud.tm.compensate.service.CompensateService;
import com.cloud.tm.config.ConfigReader;
import com.cloud.tm.constants.Constants;
import com.cloud.tm.manager.ModelInfoManager;
import com.cloud.tm.manager.service.LoadBalanceService;
import com.cloud.tm.manager.service.TxManagerSenderService;
import com.cloud.tm.manager.service.TxManagerService;
import com.cloud.tm.model.ModelInfo;
import com.cloud.tm.netty.model.TxGroup;
import com.cloud.tm.netty.model.TxInfo;
import com.cloud.tm.redis.service.RedisServerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月5日
 */
@Service
public class TxManagerServiceImpl implements TxManagerService
{

    @Autowired
    private ConfigReader configReader;

    @Autowired
    private RedisServerService redisServerService;

    @Autowired
    private TxManagerSenderService transactionConfirmService;

    @Autowired
    private LoadBalanceService loadBalanceService;

    @Autowired
    private CompensateService compensateService;

    private static final Logger log = LoggerFactory.getLogger(TxManagerServiceImpl.class);

    @Override
    public TxGroup createTransactionGroup(String groupId)
    {
        TxGroup txGroup = new TxGroup();
        if (compensateService.getCompensateByGroupId(groupId) != null)
        {
            txGroup.setIsCompensate(1);
        }

        txGroup.setStartTime(System.currentTimeMillis());
        txGroup.setGroupId(groupId);

        String key = configReader.getKeyPrefix() + groupId;
        redisServerService.saveTransaction(key, txGroup.toJsonString());

        return txGroup;
    }

    @Override
    public TxGroup addTransactionGroup(String groupId, String taskId,
            int isGroup, String channelAddress, String methodStr)
    {
        String key = getTxGroupKey(groupId);
        TxGroup txGroup = getTxGroup(groupId);
        if (txGroup == null)
        {
            return null;
        }
        TxInfo txInfo = new TxInfo();
        txInfo.setChannelAddress(channelAddress);
        txInfo.setKid(taskId);
        txInfo.setAddress(Constants.address);
        txInfo.setIsGroup(isGroup);
        txInfo.setMethodStr(methodStr);

        ModelInfo modelInfo = ModelInfoManager.getInstance()
                .getModelByChannelName(channelAddress);
        if (modelInfo != null)
        {
            txInfo.setUniqueKey(modelInfo.getUniqueKey());
            txInfo.setModelIpAddress(modelInfo.getIpAddress());
            txInfo.setModel(modelInfo.getModel());
        }

        txGroup.addTransactionInfo(txInfo);

        redisServerService.saveTransaction(key, txGroup.toJsonString());

        return txGroup;
    }

    @Override
    public boolean rollbackTransactionGroup(String groupId)
    {
        String key = getTxGroupKey(groupId);
        TxGroup txGroup = getTxGroup(groupId);
        if (txGroup == null)
        {
            return false;
        }
        txGroup.setRollback(1);
        redisServerService.saveTransaction(key, txGroup.toJsonString());
        return true;
    }

    @Override
    public int cleanNotifyTransaction(String groupId, String taskId)
    {
        int res = 0;
        log.info("start-cleanNotifyTransaction->groupId:" + groupId
                + ",taskId:" + taskId);
        String key = getTxGroupKey(groupId);
        TxGroup txGroup = getTxGroup(groupId);
        if (txGroup == null)
        {
            log.info("cleanNotifyTransaction - > txGroup is null ");
            return res;
        }

        if (txGroup.getHasOver() == 0)
        {

            // 整个事务回滚.
            txGroup.setRollback(1);
            redisServerService.saveTransaction(key, txGroup.toJsonString());

            log.info("cleanNotifyTransaction - > groupId " + groupId
                    + " not over,all transaction must rollback !");
            return 0;
        }

        if (txGroup.getRollback() == 1)
        {
            log.info("cleanNotifyTransaction - > groupId " + groupId
                    + " only rollback !");
            return 0;
        }

        // 更新数据
        boolean hasSet = false;
        for (TxInfo info : txGroup.getList())
        {
            if (info.getKid().equals(taskId))
            {
                if (info.getNotify() == 0 && info.getIsGroup() == 0)
                {
                    info.setNotify(1);
                    hasSet = true;
                    res = 1;

                    break;
                }
            }
        }

        // 判断是否都结束
        boolean isOver = true;
        for (TxInfo info : txGroup.getList())
        {
            if (info.getIsGroup() == 0 && info.getNotify() == 0)
            {
                isOver = false;
                break;
            }
        }

        if (isOver)
        {
            deleteTxGroup(txGroup);
        }

        // 有更新的数据，需要修改记录
        if (!isOver && hasSet)
        {
            redisServerService.saveTransaction(key, txGroup.toJsonString());
        }

        log.info("end-cleanNotifyTransaction->groupId:" + groupId
                + ",taskId:" + taskId + ",res(1:commit,0:rollback):" + res);
        return res;
    }

    @Override
    public int closeTransactionGroup(String groupId, int state)
    {
        String key = getTxGroupKey(groupId);
        TxGroup txGroup = getTxGroup(groupId);
        if (txGroup == null)
        {
            return 0;
        }
        txGroup.setState(state);
        txGroup.setHasOver(1);
        redisServerService.saveTransaction(key, txGroup.toJsonString());
        return transactionConfirmService.confirm(txGroup);
    }

    @Override
    public void dealTxGroup(TxGroup txGroup, boolean hasOk)
    {
        if (hasOk)
        {
            deleteTxGroup(txGroup);
        }
    }

    @Override
    public void deleteTxGroup(TxGroup txGroup)
    {
        String groupId = txGroup.getGroupId();

        String key = getTxGroupKey(groupId);
        redisServerService.deleteKey(key);

        loadBalanceService.remove(groupId);
    }

    @Override
    public TxGroup getTxGroup(String groupId)
    {
        String key = getTxGroupKey(groupId);
        return redisServerService.getTxGroupByKey(key);
    }

    @Override
    public String getTxGroupKey(String groupId)
    {
        return configReader.getKeyPrefix() + groupId;
    }
}