package com.cloud.tx.aop.service;

import org.aspectj.lang.ProceedingJoinPoint;

import com.cloud.tx.aop.bean.TxTransactionInfo;


/**
 * Created by lorne on 2017/6/8.
 */
public interface TransactionServer {


    // void execute();

    Object execute(ProceedingJoinPoint point, TxTransactionInfo info) throws Throwable;

}
