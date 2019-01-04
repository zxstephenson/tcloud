/**
 * <p>文件名称: NettyServiceImpl.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.netty.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.cloud.tm.netty.service.IActionService;
import com.cloud.tm.netty.service.NettyService;

/**
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月5日
 */
@Service
public class NettyServiceImpl implements NettyService
{

    @Autowired
    private ApplicationContext spring;

    @Override
    public IActionService getActionService(String action)
    {
        return spring.getBean(action, IActionService.class);
    }
}
