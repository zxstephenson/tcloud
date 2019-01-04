/**
 * <p>文件名称: AdminController.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.core.exception.ServiceException;
import com.cloud.tm.api.service.ApiAdminService;
import com.cloud.tm.api.service.ApiModelService;
import com.cloud.tm.compensate.model.TxModel;
import com.cloud.tm.model.ModelInfo;
import com.cloud.tm.model.ModelName;
import com.cloud.tm.model.TxState;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
@RestController
@RequestMapping("/admin")
public class AdminController
{

    @Autowired
    private ApiAdminService apiAdminService;

    @Autowired
    private ApiModelService apiModelService;

    @RequestMapping(value = "/onlines", method = RequestMethod.GET)
    public List<ModelInfo> onlines()
    {
        return apiModelService.onlines();
    }

    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public TxState setting()
    {
        return apiAdminService.getState();
    }

    @RequestMapping(value = "/json", method = RequestMethod.GET)
    public String json()
    {
        return apiAdminService.loadNotifyJson();
    }

    @RequestMapping(value = "/modelList", method = RequestMethod.GET)
    public List<ModelName> modelList()
    {
        return apiAdminService.modelList();
    }

    @RequestMapping(value = "/modelTimes", method = RequestMethod.GET)
    public List<String> modelTimes(@RequestParam("model") String model)
    {
        return apiAdminService.modelTimes(model);
    }

    @RequestMapping(value = "/modelInfos", method = RequestMethod.GET)
    public List<TxModel> modelInfos(@RequestParam("path") String path)
    {
        return apiAdminService.modelInfos(path);
    }

    @RequestMapping(value = "/compensate", method = RequestMethod.GET)
    public boolean compensate(@RequestParam("path") String path)
            throws ServiceException
    {
        return apiAdminService.compensate(path);
    }

    @RequestMapping(value = "/delCompensate", method = RequestMethod.GET)
    public boolean delCompensate(@RequestParam("path") String path)
            throws ServiceException
    {
        return apiAdminService.delCompensate(path);
    }

    @RequestMapping(value = "/hasCompensate", method = RequestMethod.GET)
    public boolean hasCompensate() throws ServiceException
    {
        return apiAdminService.hasCompensate();
    }
}
