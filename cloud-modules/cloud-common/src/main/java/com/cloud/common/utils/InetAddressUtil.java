package com.cloud.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月24日
 */

public class InetAddressUtil
{
    public static InetAddress getLocalHostLANAddress() throws Exception
    {
        try
        {
            InetAddress candidateAddress = null;
            // 遍历所有的网络接口
            for (Enumeration<NetworkInterface> ifaces = NetworkInterface
                    .getNetworkInterfaces(); ifaces.hasMoreElements();)
            {
                NetworkInterface iface = (NetworkInterface) ifaces
                        .nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration<InetAddress> inetAddrs = iface
                        .getInetAddresses(); inetAddrs.hasMoreElements();)
                {
                    InetAddress inetAddr = (InetAddress) inetAddrs
                            .nextElement();
                    if (!inetAddr.isLoopbackAddress()) // 排除loopback类型地址
                    {
                        if (inetAddr.isSiteLocalAddress())
                        {
                            return inetAddr;
                        } else if (candidateAddress == null)
                        {
                            candidateAddress = inetAddr;
                        }
                    }
                }
            }
            if (candidateAddress != null)
            {
                return candidateAddress;
            }
            // 如果没有发现 non-loopback地址.只能用最次选的方案
            InetAddress jdkSuppliedAddress = InetAddress.getLocalHost();
            return jdkSuppliedAddress;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当前服务实例主机名
     * 
     * @return
     */
    public static String getHostName()
    {
        InetAddress inetAddress;
        try
        {
            inetAddress = getLocalHostLANAddress();
            if (inetAddress != null)
            {
                return inetAddress.getHostName();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return "";
    }


}
