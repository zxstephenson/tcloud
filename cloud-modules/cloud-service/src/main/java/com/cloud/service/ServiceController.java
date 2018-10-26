package com.cloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.common.access.DispatcherService;
import com.cloud.common.bean.RequestData;
import com.cloud.common.utils.JsonUtil;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月20日
 */
@RestController
public class ServiceController
{
    
    @Autowired
    private DispatcherService dispatcherService;

    @RequestMapping(value="/service", method=RequestMethod.POST)
    public Object service(@RequestBody RequestData requestData){
        
        System.err.println("service invoked!!!!" + JsonUtil.beanToJson(requestData));
        
        dispatcherService.dispatch(requestData);
        
        return null;
    }
    
    
    
}
