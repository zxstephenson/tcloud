package com.cloud.tx.aop.service;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * Created by lorne on 2017/7/1.
 */
public interface AspectBeforeService {

    Object around(String groupId, ProceedingJoinPoint point, String mode) throws Throwable;
}
