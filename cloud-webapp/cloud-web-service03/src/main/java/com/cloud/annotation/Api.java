package com.cloud.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import com.cloud.bean.ApiParam;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Api
{
    /**
     * 接口编号
     * @return
     */
    String value() default "";
    
    /**
     * 接口别名
     * @return
     */
    String aliasCode() default "";
    
    /**
     * 接口中文名
     * @return
     */
    String name() default "";
    
    /**
     * 接口类型
     * @return
     */
    ApiType type() default ApiType.BEAN;
    
    /**
     * 超时时长 单位毫秒
     * @return
     */
    int timeout() default 3000;
    
    /**
     * 服务ID
     * @return
     */
    String serviceId() default "";
    
    /**
     * 业务类实例名
     * @return
     */
    String serviceName() default "";
    
    /**
     * 业务方法名
     * @return
     */
    String methodName() default "";
    
    
    /**是否可用
     * @return
     */
    boolean enable() default true;
    
    /**
     * 数据源
     * @return
     */
    String datasource() default "";
    
    /**
     * 请求地址信息
     * @return
     */
    String uri() default "";
    
    /**
     * 服务类型
     * @return
     */
    ServiceCategoryType serviceCategory() default ServiceCategoryType.IN;
    
}
