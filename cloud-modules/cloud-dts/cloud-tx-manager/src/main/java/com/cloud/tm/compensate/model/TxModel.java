/**
 * <p>文件名称: TxModel.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.compensate.model;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class TxModel
{

    private String time;

    private String className;

    private String method;

    private int executeTime;

    private String base64;

    private int state;

    private long order;

    private String key;

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public long getOrder()
    {
        return order;
    }

    public void setOrder(long order)
    {
        this.order = order;
    }

    public String getBase64()
    {
        return base64;
    }

    public void setBase64(String base64)
    {
        this.base64 = base64;
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public int getExecuteTime()
    {
        return executeTime;
    }

    public void setExecuteTime(int executeTime)
    {
        this.executeTime = executeTime;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public int getState()
    {
        return state;
    }

    public void setState(int state)
    {
        this.state = state;
    }
}
