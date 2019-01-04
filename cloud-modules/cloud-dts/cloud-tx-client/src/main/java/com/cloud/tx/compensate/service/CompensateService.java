package com.cloud.tx.compensate.service;


import com.cloud.tx.compensate.model.CompensateInfo;
import com.cloud.tx.model.TransactionInvocation;

/**
 * create by lorne on 2017/11/11
 */
public interface CompensateService {


    void saveLocal(CompensateInfo compensateInfo);

    boolean invoke(TransactionInvocation invocation, String groupId, int startState);

}
