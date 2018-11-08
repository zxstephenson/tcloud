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
import com.cloud.common.utils.JsonUtil;
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
        
        try
        {
            String instanceName = api.getInstanceName();
            String methodName = api.getMethodName();
            Object instance = ContextHolder.getBean(instanceName);
            Object object = AopTargetUtils.getTarget(instance);
            List<ApiParam> listParams = api.getInputParams();//接口入参列表
            
            //获取元素据中定义的请求参数类型Class对象列表
            Class<?>[] parametersType = getParameterTypes(listParams);
            
            //从requestData中获取参数值
            Object[] paramValues = getParameterValues(requestData, methodName, object, parametersType);
            
            //调用目标方法
            Object resultObject = ReflectionUtil.invokeMethod(object, methodName, parametersType, paramValues);
            
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
            String methodName, Object object, Class<?>[] parametersType)
    {
        //获取接口的参数名
        List<String> listParameterName = ReflectionUtil.getParamterName(object.getClass(), methodName);
        if(listParameterName == null || listParameterName.isEmpty())
        {
            return null;
        }
        Object[] paramValues = new Object[listParameterName.size()]; 
        if(listParameterName != null && !listParameterName.isEmpty())
        {
            Map<String, Object> requestBody = requestData.getBody();
            for(int i=0; i<listParameterName.size(); i++)
            {
                Object value = requestBody.get(listParameterName.get(i));
                Class<?> clazz = parametersType[i];
                if(!TypeUtil.isJavaClass(clazz))//自定义类型
                {
                    paramValues[i] = JsonUtil.jsonToBean(JsonUtil.beanToJson(value), clazz);
                }else 
                {
                    paramValues[i] = value;
                }
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
