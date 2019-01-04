/**
 * <p>文件名称: ConfigReader.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
@Component
public class ConfigReader
{

    @Value("${tm.socket.port}")
    private int socketPort;

    @Value("${tm.socket.maxconnection}")
    private int socketMaxConnection;

    @Value("${tm.transaction.netty.hearttime}")
    private int transactionNettyHeartTime;

    @Value("${tm.transaction.netty.delaytime}")
    private int transactionNettyDelayTime;

    @Value("${tm.redis.savemaxtime}")
    private int redisSaveMaxTime;

    @Value("${tm.compensate.notifyUrl}")
    private String compensateNotifyUrl;

    @Value("${tm.compensate.auto}")
    private boolean isCompensateAuto;

    @Value("${tm.compensate.tryTime}")
    private int compensateTryTime;

    @Value("${tm.compensate.maxWaitTime}")
    private int compensateMaxWaitTime;

    /**
     * 事务默认数据的位置，有最大时间
     */
    private final String key_prefix = "tx:manager:default:";
    /**
     * 负载均衡模块存储信息
     */
    private final String key_prefix_loadbalance = "tx:manager:loadbalance:";

    /**
     * 补偿事务永久存储数据
     */
    private final String key_prefix_compensate = "tx:manager:compensate:";

    public String getKeyPrefixLoadbalance()
    {
        return key_prefix_loadbalance;
    }

    public String getCompensateNotifyUrl()
    {
        return compensateNotifyUrl;
    }

    public String getKeyPrefix()
    {
        return key_prefix;
    }

    public String getKeyPrefixCompensate()
    {
        return key_prefix_compensate;
    }

    public int getSocketPort()
    {
        return socketPort;
    }

    public int getSocketMaxConnection()
    {
        return socketMaxConnection;
    }

    public int getTransactionNettyHeartTime()
    {
        return transactionNettyHeartTime;
    }

    public int getRedisSaveMaxTime()
    {
        return redisSaveMaxTime;
    }

    public int getTransactionNettyDelayTime()
    {
        return transactionNettyDelayTime;
    }

    public boolean isCompensateAuto()
    {
        return isCompensateAuto;
    }

    public int getCompensateTryTime()
    {
        return compensateTryTime;
    }

    public int getCompensateMaxWaitTime()
    {
        return compensateMaxWaitTime;
    }

}
