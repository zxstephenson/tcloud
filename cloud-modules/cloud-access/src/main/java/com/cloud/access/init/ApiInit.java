package com.cloud.access.init;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cloud.common.access.ApiMetaParse;
import com.cloud.common.annotation.DefineApi;
import com.cloud.common.bean.Api;
import com.cloud.common.bean.ApiParam;
import com.cloud.common.bean.ApiType;
import com.cloud.common.bean.ServiceInstanceInfo;
import com.cloud.common.cache.cacheL2.CacheL2DAO;
import com.cloud.common.constant.Constants;
import com.cloud.common.context.SystemStarted;
import com.cloud.common.utils.AopTargetUtils;
import com.cloud.common.utils.JsonUtil;
import com.cloud.common.utils.PackageUtil;
import com.cloud.common.utils.ReflectionUtil;
import com.cloud.common.utils.StringUtil;
import com.cloud.context.ContextHolder;
import com.cloud.context.configuration.ContextProperties;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月24日
 */
@Component("apiInit")
public class ApiInit implements SystemStarted
{
    private static final Logger logger = LoggerFactory.getLogger(ApiInit.class);
    
    @Autowired
    private ContextProperties contextProperties; 

    @Autowired
    private ServiceInstanceInfo serviceInstanceInfo;
    
    @Autowired
    private CacheL2DAO cacheL2Client;
    
    @Override
    public void run()
    {
        String apiMetaImpl = contextProperties.getApi().getApiMetaImpl();
        
        ApiMetaParse apiMetaParse = ContextHolder.getBean(apiMetaImpl, ApiMetaParse.class);
        //通过xml、数据库、调用远程接口获取接口元数据
        List<Api> apiList = apiMetaParse.parse();
        
        //获取通过@DefineApi注解定义的接口
        List<String> listPackage = contextProperties.getApi().getBasePackagePath();
        if(listPackage != null && !listPackage.isEmpty())
        {
            listPackage.parallelStream().forEach(path -> {
                
                try
                {
                    //通过配置的包路径，获取包路径下的所有的类的Class对象
                    List<String> listClass = PackageUtil.getClassName(path);
                    listClass.stream().forEach(className -> {
                    
                        try
                        {
                            Class<?> clazz = Class.forName(className);
                        
                            Method[] methods = clazz.getDeclaredMethods();
                            for(Method method : methods)
                            {
                                DefineApi apiAnno = method.getDeclaredAnnotation(DefineApi.class);
                                if(apiAnno == null)
                                {
                                    continue;
                                }
                                
                                //构造Api对象
                                Api api = constructApi(apiAnno, clazz, method);
                                apiList.add(api);
                            }
                        } catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    });
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
    
            });
        }
        String serviceName = serviceInstanceInfo.getServiceName();
        logger.info("apis = " + JsonUtil.beanToJson(apiList));
        
        /**
         * 将当前接口的元数据存储在redis中
         */
        cacheL2Client.hset(Constants.PREFIX + Constants.SERVICE_API, serviceName, apiList);
    }
    
    
    public Api constructApi(DefineApi annoApi, Class<?> clazz, Method method) throws Exception
    {
        
        Api api = new Api();
        if(annoApi != null)
        {
            String apiCode = annoApi.value(); //接口编号
            if(StringUtil.isEmpty(apiCode))
            {
                apiCode = method.getName(); //如果没有指定接口编号，则默认使用方法名作为接口编号
            }
            api.setCode(apiCode); //接口编号
            
            String aliasCode = annoApi.aliasCode();
            if(StringUtil.isEmpty(aliasCode))
            {
                aliasCode = apiCode;
            }
            api.setAliasCode(aliasCode); //接口别名
            
            api.setDesc(annoApi.desc()); //接口中文名
            
            ApiType apiType = annoApi.type();
            if(apiType == null)
            {
                apiType = ApiType.BEAN;
            }
            api.setType(apiType); //接口类型
            
            int timeout = annoApi.timeout(); //接口超时时间
            api.setTimeout(timeout);
            
            String instanceName = getInstanceName(clazz); //获取实例对象名
            String methodName = method.getName();

            String serviceInstanceName = annoApi.serviceName(); //业务类实例名
            if(StringUtil.isEmpty(serviceInstanceName))
            {
                serviceInstanceName = instanceName;
            }
            api.setInstanceName(serviceInstanceName);
            
            //不允许通过手动设置业务方法名，方法名自动通过Method对象获取
            api.setMethodName(methodName);
            
            String uri = annoApi.uri();
            if(StringUtil.isEmpty(uri))
            {
                uri = instanceName + ":" + methodName;
            }
            api.setUri(uri);
            
            String serviceId = annoApi.serviceId();
            if(StringUtil.isEmpty(serviceId))
            {
                if(apiType == ApiType.RIBBON)
                {
                    serviceId = uri;
                }
            }
            
            api.setServiceId(serviceId);
            
            boolean enable = annoApi.enable();
            api.setEnable(enable);  //接口是否可用，默认为true
            Object instance = ContextHolder.getBean(instanceName);
            Object object = AopTargetUtils.getTarget(instance);
            List<ApiParam> inputParams = new ArrayList<ApiParam>();
            
            //获取接口的参数名
            List<String> listParameters = ReflectionUtil.getParamterName(object.getClass(), methodName);
            if(listParameters == null || listParameters.isEmpty())
            {
                return api;
            }
            Parameter[] parameters = method.getParameters();
            for(int i=0; i<listParameters.size(); i++)
            {
                ApiParam apiParam = new ApiParam();
                apiParam.setCode(listParameters.get(i)); //参数名
                String parameterType = parameters[i].getType().getName();
                apiParam.setType(parameterType); //参数类型
                inputParams.add(apiParam);
            }
            
            api.setInputParams(inputParams);
        }
        
        return api;
    }
    
    
    private String getInstanceName(Class<?> clazz)
    {
        String instanceName = "";
        //默认从类的注解中获取当前类实例名
        Annotation[] annotations = clazz.getDeclaredAnnotations();
        for(Annotation anno : annotations)
        {
            if(anno.annotationType().isAssignableFrom(Component.class))
            {
                Component componentAnno = (Component)anno;
                instanceName = componentAnno.value();
                break;
            }else if(anno.annotationType().isAssignableFrom(Service.class))
            {
                Service serviceAnno = (Service) anno;
                instanceName = serviceAnno.value();
                break;
            }
        }
        if(StringUtil.isEmpty(instanceName))
        {
            instanceName = StringUtil.uncapitalize(clazz.getName());
        }
        
        return instanceName;
    }
}
