package com.cloud.common.cache.exception;

import com.cloud.common.exception.BaseException;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月19日
 */

public class CacheException extends BaseException
{
    private static final long serialVersionUID = -5314856701445174638L;

    public CacheException(){
        super();
    }
    
    public CacheException(String code, String msg){
        super(code, msg);
    }
    
}
