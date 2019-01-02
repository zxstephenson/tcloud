package com.cloud.gray.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月24日
 */

public class GrayServiceInfo implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 4638921615621444479L;

    /**
     * 灰度服务服务名
     */
    private String serviceId;

    /**
     * 是否为灰度服务，当该服务中只要存在一个灰度实例，那么该服务就会被定义为灰度服务，该属性值为true 否则，该属性值为false，不为灰度服务
     */
    private boolean grayable;

    /**
     * 为实例绑定的灰度策略信息
     */
    private StrategyInfo strategyInfo;
    
    /**
     * 灰度实例列表
     */
    private List<String> instanceIdList = new ArrayList<String>();

    public String getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(String serviceId)
    {
        this.serviceId = serviceId;
    }

    public boolean isGrayable()
    {
        return grayable;
    }

    public void setGrayable(boolean grayable)
    {
        this.grayable = grayable;
    }

    public StrategyInfo getStrategyInfo()
    {
        return strategyInfo;
    }

    public void setStrategyInfo(StrategyInfo strategyInfo)
    {
        this.strategyInfo = strategyInfo;
    }

    public List<String> getInstanceIdList()
    {
        return instanceIdList;
    }

    public void setInstanceIdList(List<String> instanceIdList)
    {
        this.instanceIdList = instanceIdList;
    }

}
