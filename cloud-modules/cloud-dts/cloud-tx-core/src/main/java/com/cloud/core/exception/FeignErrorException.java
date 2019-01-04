/**
 * <p>文件名称: FeignErrorException.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core.exception;

/**
 * feign 拦截异常 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class FeignErrorException extends LEException
{

    public FeignErrorException()
    {
    }

    public FeignErrorException(String message)
    {
        super(message);
    }

    public FeignErrorException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public FeignErrorException(Throwable cause)
    {
        super(cause);
    }

    public FeignErrorException(String message, Throwable cause,
            boolean enableSuppression, boolean writableStackTrace)
    {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
