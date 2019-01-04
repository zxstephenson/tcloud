/**
 * <p>文件名称: MicroService.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.manager.service;

import com.cloud.tm.model.TxServer;
import com.cloud.tm.model.TxState;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public interface MicroService
{

    String tmKey = "tx-manager";

    TxServer getServer();

    TxState getState();
}
