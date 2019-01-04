/**
 * <p>文件名称: ApplicationStartListener.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.listener;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.cloud.tm.constants.Constants;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
@Component
public class ApplicationStartListener
        implements ApplicationListener<EmbeddedServletContainerInitializedEvent>
{

    @Override
    public void onApplicationEvent(
            EmbeddedServletContainerInitializedEvent event)
    {
        int serverPort = event.getEmbeddedServletContainer().getPort();
        String ip = getIp();
        Constants.address = ip + ":" + serverPort;
    }

    private String getIp()
    {
        String host = null;
        try
        {
            host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        return host;
    }
}
