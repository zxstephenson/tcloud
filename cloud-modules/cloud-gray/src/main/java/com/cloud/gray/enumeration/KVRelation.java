package com.cloud.gray.enumeration;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年12月27日
 */

public enum KVRelation
{
    GT("gt"),LT("lt"),EQ("eq");
    
    private String value;
    
    KVRelation(String value)
    {
        this.value = value;
    }
    
    public String getValue(){
        return this.value;
    }
    
}
