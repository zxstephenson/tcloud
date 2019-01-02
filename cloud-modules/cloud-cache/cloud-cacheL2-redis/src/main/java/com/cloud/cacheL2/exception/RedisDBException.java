/**
 * <p>文件名称: RedisDBException.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1.0</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.cacheL2.exception;

import com.cloud.common.exception.BaseException;

/**
 * Redis异常类
 * 
 * @author yyh
 * @version 3.1.0 2018年7月17日
 * @see
 * @since 3.1.0
 */
public class RedisDBException extends BaseException
{

    /**
     * 
     */
    private static final long serialVersionUID = -1190808372444510164L;

    public RedisDBException(String errorCode){
        super(errorCode);
    }

    
    public RedisDBException(String errorCode, String msg){
        super(errorCode,msg);
    }
    
    public RedisDBException(String errorCode, Throwable throwable){
        super(errorCode,throwable);
    }
    
    public RedisDBException(String errorCode, String msg, Throwable throwable){
        super(msg+", errorCode:" + errorCode, throwable);
        this.errorCode = errorCode;
    }



}
