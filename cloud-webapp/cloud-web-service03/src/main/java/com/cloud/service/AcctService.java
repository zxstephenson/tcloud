package com.cloud.service;

import org.springframework.stereotype.Service;

import com.cloud.common.annotation.DefineApi;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月19日
 */
@Service("acctService")
public class AcctService
{

    @DefineApi
    public boolean createAcct(){
        
        return true;
    }
    
    public void showAcct(){
        
    }
    
}
