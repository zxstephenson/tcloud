/**
 * <p>文件名称: TxManagerController.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.tm.api.service.ApiTxManagerService;
import com.cloud.tm.model.TxServer;
import com.cloud.tm.model.TxState;

/**
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
@RestController
@RequestMapping("/tx/manager")
public class TxManagerController
{

    @Autowired
    private ApiTxManagerService apiTxManagerService;

    @RequestMapping("/getServer")
    public TxServer getServer()
    {
        return apiTxManagerService.getServer();
    }

    @RequestMapping("/cleanNotifyTransaction")
    public int cleanNotifyTransaction(@RequestParam("groupId") String groupId,
            @RequestParam("taskId") String taskId)
    {
        return apiTxManagerService.cleanNotifyTransaction(groupId, taskId);
    }

    @RequestMapping("/sendMsg")
    public String sendMsg(@RequestParam("msg") String msg,
            @RequestParam("model") String model)
    {
        return apiTxManagerService.sendMsg(model, msg);
    }

    @RequestMapping(value="/sendCompensateMsg")
    public boolean sendCompensateMsg(@RequestParam("model") String model,
            @RequestParam("uniqueKey") String uniqueKey,
            @RequestParam("currentTime") long currentTime,
            @RequestParam("groupId") String groupId,
            @RequestParam("className") String className,
            @RequestParam("time") int time, @RequestParam("data") String data,
            @RequestParam("methodStr") String methodStr,
            @RequestParam("address") String address,
            @RequestParam("startError") int startError)
    {
        return apiTxManagerService.sendCompensateMsg(currentTime, groupId,
                model, address, uniqueKey, className, methodStr, data, time,
                startError);
    }

    @RequestMapping("/state")
    public TxState state()
    {
        return apiTxManagerService.getState();
    }
}
