/**
 * <p>文件名称: CompensateDao.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.compensate.dao;

import java.util.List;

import com.cloud.tm.compensate.model.TransactionCompensateMsg;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public interface CompensateDao
{

    String saveCompensateMsg(TransactionCompensateMsg transactionCompensateMsg);

    List<String> loadCompensateKeys();

    List<String> loadCompensateTimes(String model);

    List<String> loadCompensateByModelAndTime(String path);

    String getCompensate(String key);

    String getCompensateByGroupId(String groupId);

    void deleteCompensateByPath(String path);

    void deleteCompensateByKey(String key);

    boolean hasCompensate();
}
