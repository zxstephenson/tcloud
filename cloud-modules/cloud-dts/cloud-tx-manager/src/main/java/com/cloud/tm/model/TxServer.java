/**
 * <p>文件名称: TxServer.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.model;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class TxServer
{

    private String ip;
    private int port;
    private int heart;
    private int delay;
    private int compensateMaxWaitTime;

    public static TxServer format(TxState state)
    {
        TxServer txServer = new TxServer();
        txServer.setIp(state.getIp());
        txServer.setPort(state.getPort());
        txServer.setHeart(state.getTransactionNettyHeartTime());
        txServer.setDelay(state.getTransactionNettyDelayTime());
        txServer.setCompensateMaxWaitTime(state.getCompensateMaxWaitTime());
        return txServer;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public int getHeart()
    {
        return heart;
    }

    public void setHeart(int heart)
    {
        this.heart = heart;
    }

    public int getDelay()
    {
        return delay;
    }

    public void setDelay(int delay)
    {
        this.delay = delay;
    }

    public int getCompensateMaxWaitTime()
    {
        return compensateMaxWaitTime;
    }

    public void setCompensateMaxWaitTime(int compensateMaxWaitTime)
    {
        this.compensateMaxWaitTime = compensateMaxWaitTime;
    }

}
