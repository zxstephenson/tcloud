package com.cloud.access.dispatcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloud.access.api.ApiService;
import com.cloud.common.access.DispatcherService;
import com.cloud.common.bean.Api;
import com.cloud.common.bean.ApiType;
import com.cloud.common.bean.RequestData;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月25日
 */
@Component
public class BeanDispatcherImpl implements DispatcherService
{
    @Autowired
    private ApiService apiService;
    
    @Override
    public Object dispatch(Object reqData)
    {
        if(reqData instanceof RequestData)
        {
            RequestData requestData = (RequestData)reqData;
            String apiCode = requestData.getHead().getCode();
            Api api = apiService.getApiByApiCode(apiCode);
            if(api != null)
            {
                ApiType apiType = api.getType();
                if(ApiType.BEAN == apiType)
                {
                    BeanClient.dispatch(requestData);
                }
            }
        }
        
        return null;
    }

}
