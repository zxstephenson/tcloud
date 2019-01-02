package com.cloud.gray.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月24日
 */

public class GrayServicesRulesInfo implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -1018559480979343774L;
    
    private Map<String, GrayServiceInfo> serviceGrayStrategyMap = new HashMap<String, GrayServiceInfo>();

    public Map<String, GrayServiceInfo> getServiceGrayStrategyMap()
    {
        return serviceGrayStrategyMap;
    }

    public void setServiceGrayStrategyMap(Map<String, GrayServiceInfo> serviceGrayStrategyMap)
    {
        this.serviceGrayStrategyMap = serviceGrayStrategyMap;
    }

}
