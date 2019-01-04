/**
 * <p>文件名称: CompensateService.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.compensate.service;

import java.util.List;

import com.cloud.core.exception.ServiceException;
import com.cloud.tm.compensate.model.TransactionCompensateMsg;
import com.cloud.tm.compensate.model.TxModel;
import com.cloud.tm.model.ModelName;
import com.cloud.tm.netty.model.TxGroup;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public interface CompensateService
{

    boolean saveCompensateMsg(
            TransactionCompensateMsg transactionCompensateMsg);

    List<ModelName> loadModelList();

    List<String> loadCompensateTimes(String model);

    List<TxModel> loadCompensateByModelAndTime(String path);

    void autoCompensate(String compensateKey,
            TransactionCompensateMsg transactionCompensateMsg);

    boolean executeCompensate(String key) throws ServiceException;

    void reloadCompensate(TxGroup txGroup);

    boolean hasCompensate();

    boolean delCompensate(String path);

    TxGroup getCompensateByGroupId(String groupId);
}
