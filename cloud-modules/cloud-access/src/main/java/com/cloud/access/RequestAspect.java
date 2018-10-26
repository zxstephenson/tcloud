package com.cloud.access;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.cloud.common.bean.RequestData;
import com.cloud.common.utils.JsonUtil;
import com.cloud.common.utils.ValidateUtil;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月25日
 */
@Aspect
@Component
public class RequestAspect
{

    @Pointcut("execution(* com.cloud.service.*.*(..))")
    public void methodPointcut()
    {
    }
    
    @Around("methodPointcut()")
    public void around(ProceedingJoinPoint jp) throws Throwable
    {
        System.err.println("around invoked!!!!");
        Object[] objs = jp.getArgs();
        if(objs != null && objs.length>0)
        {
            Object object = objs[0];
            if(object instanceof RequestData)
            {
                RequestData requestData = (RequestData)object;
                List<String> validateResults = ValidateUtil.validateObject(requestData);
                System.out.println("===validateResults ===>" + JsonUtil.beanToJson(validateResults));
            }
        }
        System.out.println("====>RequestData = " + JsonUtil.beanToJson(objs));
        jp.proceed(jp.getArgs()); //调用目标方法
    }
    
}
