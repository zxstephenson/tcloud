package com.cloud.gray.bean;

import com.cloud.gray.enumeration.KVRelation;
import com.cloud.gray.enumeration.RuleRelation;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年12月27日
 */

public class GrayRequestParamsRuleBean
{
    private String region;
    
    private String key;
    
    private String value;
    
    private KVRelation kvRelation;
    
    private RuleRelation nextRuleRelation;

    public String getRegion()
    {
        return region;
    }

    public void setRegion(String region)
    {
        this.region = region;
    }
    
    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public KVRelation getKvRelation()
    {
        return kvRelation;
    }

    public void setKvRelation(KVRelation kvRelation)
    {
        this.kvRelation = kvRelation;
    }

    public void setKvRelation(String kvRelation)
    {
        if("eq".equals(kvRelation))
        {
            this.kvRelation = KVRelation.EQ;
        }else  if("gt".equals(kvRelation))
        {
            this.kvRelation = KVRelation.GT;
        }else  if("lt".equals(kvRelation))
        {
            this.kvRelation = KVRelation.LT;
        }
    }
    public RuleRelation getNextRuleRelation()
    {
        return nextRuleRelation;
    }

    public void setNextRuleRelation(RuleRelation nextRuleRelation)
    {
        this.nextRuleRelation = nextRuleRelation;
    }

    public void setNextRuleRelation(String nextRuleRelation)
    {
        if("and".equalsIgnoreCase(nextRuleRelation))
        {
            this.nextRuleRelation = RuleRelation.AND;            
        }else if("or".equalsIgnoreCase(nextRuleRelation))
        {
            this.nextRuleRelation = RuleRelation.OR;            
        }
    }
}
