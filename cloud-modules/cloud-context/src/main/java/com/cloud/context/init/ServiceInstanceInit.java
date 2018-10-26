/**
 * <p>文件名称: ServiceInstanceInit.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.context.init;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cloud.common.bean.ServiceInstanceInfo;
import com.cloud.common.context.SystemStarted;
import com.cloud.common.utils.InetAddressUtil;
import com.cloud.common.utils.StringUtil;

/**
 * 初始化服务实例的基础信息
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年6月5日
 */
@Component("serviceInstanceInit")
public class ServiceInstanceInit implements SystemStarted
{
    private static final Logger log = LoggerFactory
            .getLogger(ServiceInstanceInit.class);

    @Autowired
    private ServiceInstanceInfo instanceInfo;

    @Value("${spring.application.name:localhost}")
    private String serviceName;
    
    @Value("${server.port}")
    private String serverPort;
    
    @Value("${server.hostname:localhost}")
    private String hostname;
    
    @Value("${server.ipAddress:127.0.0.1}")
    private String ipAddress;
    
    @Override
    public void run()
    {
        log.debug("初始化当前服务实例的基础信息开始！");

        InetAddress inetAddress;
        try
        {
            inetAddress = InetAddressUtil.getLocalHostLANAddress();
            instanceInfo.setServiceName(serviceName); // 服务名
            instanceInfo.setServerPort(serverPort); // 端口号

            if (StringUtil.isEmpty(hostname))
            {
                hostname = inetAddress.getHostName(); // 如果没有配置主机名，则默认通过inetAddress对象获取
            }
            if (StringUtil.isEmpty(ipAddress))
            {
                ipAddress = inetAddress.getHostAddress();// 如果没有配置IP地址，则默认通过inetAddress对象获取
            }

            instanceInfo.setHostname(hostname); // 主机名
            instanceInfo.setIpAddress(ipAddress);// ip地址
            instanceInfo.setProcessId(getProcessId());// 当前服务进程ID
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        log.debug("初始化当前服务实例的基础信息完成！");
    
    }
    
    /**
     * 获取当前服务进程ID数据
     * 
     * @return 返回当前服务实例进程编号
     */
    private String getProcessId()
    {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        return pid;
    }
}
