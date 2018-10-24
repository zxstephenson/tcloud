/**
 * <p>文件名称: ServiceInstanceInfo.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.common.bean;

import org.springframework.stereotype.Component;

/**
 * 定义当前服务实例的初始化信息
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年6月5日
 */
@Component
public class ServiceInstanceInfo
{
    /**
     * 当前服务名
     */
    private String serviceName = "service";

    /**
     * 服务端口号
     */
    private String serverPort = "8080";

    /**
     * 服务实例的IP地址
     */
    private String ipAddress = "127.0.0.1";

    /**
     * 服务实例的主机名
     */
    private String hostname = "localhost";

    /**
     * 当前服务实例的进程号
     */
    private String processId = "";

    public String getProcessId()
    {
        return processId;
    }

    public void setProcessId(String processId)
    {
        this.processId = processId;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    public String getServerPort()
    {
        return serverPort;
    }

    public void setServerPort(String serverPort)
    {
        this.serverPort = serverPort;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    public String getHostname()
    {
        return hostname;
    }

    public void setHostname(String hostname)
    {
        this.hostname = hostname;
    }

    @Override
    public String toString()
    {
        return "ServiceInstanceInfo [serviceName=" + serviceName
                + ", serverPort=" + serverPort + ", ipAddress=" + ipAddress
                + ", hostname=" + hostname + ", processId=" + processId + "]";
    }
}
