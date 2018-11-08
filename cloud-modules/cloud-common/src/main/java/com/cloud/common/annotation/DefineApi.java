package com.cloud.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cloud.common.bean.ApiType;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月25日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DefineApi
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
    String desc() default "";
    
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
     * 是否可用
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
}
