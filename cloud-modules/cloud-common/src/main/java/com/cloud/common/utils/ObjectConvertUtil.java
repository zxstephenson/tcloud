package com.cloud.common.utils;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年9月27日
 */

public class ObjectConvertUtil
{
    /**
     * 将map转换为一个javabean对象
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static <T> T mapToObject(Map<String, Object> map, Class<T> beanClass) 
            throws Exception {
        
        if (map == null){
            return null;
        }
        
        T obj = beanClass.newInstance();
        
        BeanUtils.populate(obj, map);
        return obj;
    }
    
    /**
     * 将一个javabean对象转换为一个map对象
     * @param obj
     * @return
     */
    public static Map<?, ?> objectToMap(Object obj) {
        if (obj == null){
            return null;
        }
        return new org.apache.commons.beanutils.BeanMap(obj);
    }

}
