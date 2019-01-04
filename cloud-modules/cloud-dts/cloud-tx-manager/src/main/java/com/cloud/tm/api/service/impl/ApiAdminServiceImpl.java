/**
 * <p>文件名称: ApiAdminServiceImpl.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.api.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.core.exception.ServiceException;
import com.cloud.tm.api.service.ApiAdminService;
import com.cloud.tm.compensate.model.TxModel;
import com.cloud.tm.compensate.service.CompensateService;
import com.cloud.tm.manager.service.MicroService;
import com.cloud.tm.model.ModelName;
import com.cloud.tm.model.TxState;
import com.cloud.tm.redis.service.RedisServerService;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
@Service
public class ApiAdminServiceImpl implements ApiAdminService
{

    @Autowired
    private MicroService eurekaService;

    @Autowired
    private RedisServerService redisServerService;

    @Autowired
    private CompensateService compensateService;

    @Override
    public TxState getState()
    {
        return eurekaService.getState();
    }

    @Override
    public String loadNotifyJson()
    {
        return redisServerService.loadNotifyJson();
    }

    @Override
    public List<ModelName> modelList()
    {
        return compensateService.loadModelList();
    }

    @Override
    public List<String> modelTimes(String model)
    {
        return compensateService.loadCompensateTimes(model);
    }

    @Override
    public List<TxModel> modelInfos(String path)
    {
        return compensateService.loadCompensateByModelAndTime(path);
    }

    @Override
    public boolean compensate(String path) throws ServiceException
    {
        return compensateService.executeCompensate(path);
    }

    @Override
    public boolean delCompensate(String path)
    {
        return compensateService.delCompensate(path);
    }

    @Override
    public boolean hasCompensate()
    {
        return compensateService.hasCompensate();
    }
}
