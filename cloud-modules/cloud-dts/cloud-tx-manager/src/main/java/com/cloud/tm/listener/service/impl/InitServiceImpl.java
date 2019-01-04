/**
 * <p>文件名称: InitServiceImpl.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.listener.service.impl;

import com.cloud.tm.config.ConfigReader;
import com.cloud.tm.constants.Constants;
import com.cloud.tm.listener.service.InitService;
import com.cloud.tm.netty.service.NettyServerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
@Service
public class InitServiceImpl implements InitService
{

    @Autowired
    private NettyServerService nettyServerService;

    @Autowired
    private ConfigReader configReader;

    @Override
    public void start()
    {
        /** 加载本地服务信息 **/

        Constants.socketPort = configReader.getSocketPort();
        Constants.maxConnection = configReader.getSocketMaxConnection();
        nettyServerService.start();
    }

    @Override
    public void close()
    {
        nettyServerService.close();
    }
}
