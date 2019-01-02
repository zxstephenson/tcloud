package com.cloud.gray.bean;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月24日
 */

public class StrategyInfo implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 3214286362545685810L;

    /**
     * 灰度策略的编号
     */
    private String strategyId;

    /**
     * 灰度策略的名称
     */
    private String strategyName;

    /**
     * 灰度策略详情
     */
    private String strategyDetail;

    /**
     * 灰度策略的描述
     */
    private String strategyDesc;

    /**
     * 灰度策略对应的实现类classname（包名+类名）
     */
    private String strategyImpClassName;

    public String getStrategyId()
    {
        return strategyId;
    }

    public void setStrategyId(String strategyId)
    {
        this.strategyId = strategyId;
    }

    public String getStrategyName()
    {
        return strategyName;
    }

    public void setStrategyName(String strategyName)
    {
        this.strategyName = strategyName;
    }

    public String getStrategyDesc()
    {
        return strategyDesc;
    }

    public void setStrategyDesc(String strategyDesc)
    {
        this.strategyDesc = strategyDesc;
    }

    public String getStrategyImpClassName()
    {
        return strategyImpClassName;
    }

    public void setStrategyImpClassName(String strategyImpClassName)
    {
        this.strategyImpClassName = strategyImpClassName;
    }

    public String getStrategyDetail()
    {
        return strategyDetail;
    }

    public void setStrategyDetail(String strategyDetail)
    {
        this.strategyDetail = strategyDetail;
    }

}
