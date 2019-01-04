/**
 * <p>文件名称: ApiAdminService.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.api.service;

import java.util.List;

import com.cloud.core.exception.ServiceException;
import com.cloud.tm.compensate.model.TxModel;
import com.cloud.tm.model.ModelName;
import com.cloud.tm.model.TxState;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public interface ApiAdminService
{

    TxState getState();

    String loadNotifyJson();

    List<ModelName> modelList();

    List<String> modelTimes(String model);

    List<TxModel> modelInfos(String path);

    boolean compensate(String path) throws ServiceException;

    boolean hasCompensate();

    boolean delCompensate(String path);

}
