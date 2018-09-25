package com.cloud.common.search.exception;

import com.cloud.common.exception.BaseException;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月21日
 */

public class SearchException extends BaseException
{

    private static final long serialVersionUID = 5472796551840648127L;

    public SearchException(){
        super();
    }
    
    public SearchException(String code, String msg){
        super(code, msg);
    }
    
}
