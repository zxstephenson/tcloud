package com.cloud.access;

import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(RequestAspect.class);
    
    @Pointcut("execution(* com.cloud.service.*.*(..))")
    public void methodPointcut()
    {
    }
    
    @Around("methodPointcut()")
    public Object around(ProceedingJoinPoint jp) throws Throwable
    {
        logger.info("around invoked!!!!");
        Object[] objs = jp.getArgs();
        if(objs != null && objs.length>0)
        {
            Object object = objs[0];
            if(object instanceof RequestData)
            {
                RequestData requestData = (RequestData)object;
                //请求参数校验
                List<String> validateResults = ValidateUtil.validateObject(requestData);
                logger.info("===validateResults ===>" + JsonUtil.beanToJson(validateResults));
                //如果校验失败将返回校验失败信息
            }
        }
        logger.info("====>RequestData = " + JsonUtil.beanToJson(objs));
        Object obj = jp.proceed(jp.getArgs()); //调用目标方法
        return obj;
    }
    
}
