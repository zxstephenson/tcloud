/**
 * <p>文件名称: ServiceController.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.common.bean.RequestData;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月20日
 */
@RestController
public class ServiceController
{

    @RequestMapping("/service")
    public Object service(RequestData requestData){
        
        
        return null;
    }
    
    
    
}