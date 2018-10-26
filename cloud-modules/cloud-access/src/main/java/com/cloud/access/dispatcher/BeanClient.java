package com.cloud.access.dispatcher;

import java.util.List;
import java.util.Map;

import com.cloud.access.api.ApiService;
import com.cloud.common.bean.Api;
import com.cloud.common.bean.ApiParam;
import com.cloud.common.bean.RequestData;
import com.cloud.common.bean.ResponseData;
import com.cloud.common.constant.Constants;
import com.cloud.common.utils.AopTargetUtils;
import com.cloud.common.utils.ReflectionUtil;
import com.cloud.common.utils.TypeUtil;
import com.cloud.context.ContextHolder;

/**
 * 请求分发，根据请求的接口信息，将请求分发到对应的实例方法中
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月25日
 */

public class BeanClient
{
    private static ApiService apiService;
    
    /**
     * 获取apiService实例对象
     * @return
     */
    public static ApiService getApiService()
    {
        if(apiService == null)
        {
            synchronized(BeanClient.class)
            {
                if(apiService == null)
                {
                    apiService = ContextHolder.getBean(ApiService.class);               
                }
            }
        }
        
        return apiService;
    }
    
    /**
     * 请求分发
     * @param requestData
     * @return
     */
    public static ResponseData dispatch(RequestData requestData)
    {
        String apiCode = requestData.getHead().getCode();
        
        ApiService apiService = getApiService();
        Api api = apiService.getApiByApiCode(apiCode); //根据接口编号获取对应的Api对象
        
        List<ApiParam> listParams = api.getInputParams();//接口入参列表
        
        /**
         *  获取元素据定义的请求参数类型Class对象列表
         */
        Class<?>[] parameterTypes = getParameterTypes(listParams);
        
        try
        {
            String instanceName = api.getInstanceName();
            String methodName = api.getMethodName();
            Object instance = ContextHolder.getBean(instanceName);
            Object object = AopTargetUtils.getTarget(instance);
            
            Object[] paramValues = getParameterValues(requestData, methodName, object);
            
            //调用目标方法
            Object resultObject = ReflectionUtil.invokeMethod(object, methodName, parameterTypes, paramValues);
            
            if(resultObject != null)
            {
                ResponseData responseData = new ResponseData();
                responseData.setBody(resultObject);
                return responseData;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            return new ResponseData(Constants.ERROR, e.getMessage());
        }
        
        return null;
    }

    /**
     * 为接口的请求参数设置，并返回所有参数组成的Object[]对象
     * @param requestData
     * @param methodName
     * @param object
     * @return
     */
    private static Object[] getParameterValues(RequestData requestData,
            String methodName, Object object)
    {
        //获取接口的参数名
        List<String> listParameters = ReflectionUtil.getParamterName(object.getClass(), methodName);
        if(listParameters == null || listParameters.isEmpty())
        {
            return null;
        }
        Object[] paramValues = new Object[listParameters.size()]; 
        if(listParameters != null && !listParameters.isEmpty())
        {
            Map<String, Object> requestBody = requestData.getBody();
            for(int i=0; i<listParameters.size(); i++)
            {
                Object value = requestBody.get(listParameters.get(i));
                paramValues[i] = value;
            }
        }
        return paramValues;
    }

    /**
     * 根据接口元数据定义获取接口参数类型
     * @param listParams
     * @return
     */
    private static Class<?>[] getParameterTypes(List<ApiParam> listParams)
    {
        if(listParams == null || listParams.isEmpty())
        {
            return null;
        }
        
        Class<?>[]  parameterTypes = new Class<?>[listParams.size()];
        for(int i=0; i<listParams.size(); i++)
        {
            try
            {
                String typeStr = listParams.get(i).getType();
                Class<?> parameterType = TypeUtil.getPrimitiveClass(typeStr);
                if(parameterType == null)
                {
                    parameterType = Class.forName(typeStr);
                }
                parameterTypes[i] = parameterType;
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return parameterTypes;
    }
}
