package com.cloud.access;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.cloud.annotation.Api;
import com.cloud.annotation.ApiType;
import com.cloud.annotation.ServiceCategoryType;
import com.cloud.bean.ApiParam;
import com.cloud.bean.ParamDataType;
import com.cloud.bean.User;
import com.cloud.common.utils.StringUtil;
import com.cloud.configuration.ContextProperties;
import com.cloud.utils.PackageUtil;
import com.cloud.utils.TypeUtil;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月19日
 */
@Component
public class Access implements ApplicationRunner
{
    @Autowired
    private ContextProperties contextProperties;
    
    public static void main(String[] args)
    {
        User user = new User();
        user.setAge(110);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory(); 
        Validator validator = factory.getValidator(); 
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        violations.stream().forEach(violation -> {
            String pathStr = violation.getPropertyPath().toString();
            String msg = violation.getMessage();
            System.out.println("pathStr = " + pathStr + " : msg = " + msg);
        });
    
    }
    
    
    @Override
    public void run(ApplicationArguments args) throws Exception
    {
        String path = contextProperties.getPackagePath();
        System.err.println("path = " + path);
        
        List<String> list = PackageUtil.getClassName(path);
        list.stream().forEach(className -> {
            
            try
            {
                Class<?> clazz = Class.forName(className);
                Method[] methods = clazz.getDeclaredMethods();
                for(Method method : methods)
                {
                    Api apiAnno = method.getDeclaredAnnotation(Api.class);
                    if(apiAnno == null)
                    {
                        continue;
                    }
                    com.cloud.bean.Api api = constructApi(apiAnno, clazz, method);
                    System.err.println("******************");
                    System.err.println("******************API= " + api);
                    System.err.println("******************");
                }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
            
            
        });
        
    }
    
    
    public com.cloud.bean.Api constructApi(Api annoApi, Class<?> clazz, Method method)
    {
        
        com.cloud.bean.Api api = new com.cloud.bean.Api();
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
            
            api.setName(annoApi.name()); //接口中文名
            
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
            api.setServiceName(serviceInstanceName);
            
            /*//这里个人觉得可以不需要配置方法名。
            String serviceMethodName = annoApi.methodName();
            if(StringUtil.isEmpty(serviceMethodName))
            {
                serviceMethodName = methodName;
            }
            api.setMethodName(serviceMethodName);
            */
            
            String uri = annoApi.uri();
            if(StringUtil.isEmpty(uri))
            {
                uri = instanceName + ":" + methodName;
            }
            api.setUri(uri);
            
            ServiceCategoryType serviceCategoryType = annoApi.serviceCategory();
            api.setServiceCategory(serviceCategoryType);
            
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
            
            
            List<ApiParam> inputParams = new ArrayList<ApiParam>();
            
            Parameter[] parameters = method.getParameters();
            for(Parameter parameter : parameters)
            {
                String argName = parameter.getName();
                ApiParam apiParam = new ApiParam();
                apiParam.setApiCode(apiCode);  //接口编号
                apiParam.setParaCode(argName); //参数名
                Class<?> parameterType = parameter.getType();
                
                boolean isJavaClass = TypeUtil.isJavaClass(parameterType);
//                System.out.println("argName = " + argName + "  ; simpleName = " +parameterType.getSimpleName()+"========> type =" + parameterType.getName() + "   ======> isJavaClass = " + isJavaClass);
                ParamDataType type = null;
                if(!isJavaClass) //如果是自定义类型，将该参数的类型设置为bean
                {
                    type = ParamDataType.PARAM_BEAN;
                    apiParam.setBeanPath(parameterType.getName());
                }else
                {
                    String simpleName = parameterType.getSimpleName();
                    type = ParamDataType.fromValue(simpleName);
                }
                
                apiParam.setDataType(type); //参数数据类型
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
