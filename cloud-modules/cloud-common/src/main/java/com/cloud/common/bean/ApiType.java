package com.cloud.common.bean;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月19日
 */

public enum ApiType
{
    BUS("bus"),
    RIBBON("ribbon"),
    BEAN("bean"),
    UNDEFINED("undefined");
    
    ApiType(String value){
        
    }
    
    public static ApiType getApiType(String type)
    {
        switch(type.toLowerCase())
        {
            case "bus":
                return BUS;
            case "ribbon":
                return RIBBON;
            case "bean":
                return BEAN;
        }
        
        return UNDEFINED;
    }
}
