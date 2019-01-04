/**
 * <p>文件名称: ActionTServiceImpl.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.netty.service.impl;

import org.springframework.stereotype.Service;

import com.cloud.tm.netty.service.IActionService;

/**
 * 通知事务回调 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
@Service(value = "t")
public class ActionTServiceImpl extends BaseSignalTaskService
        implements IActionService
{

}
