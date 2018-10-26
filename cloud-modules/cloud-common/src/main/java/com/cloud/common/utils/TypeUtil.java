package com.cloud.common.utils;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月25日
 */

public class TypeUtil
{
    public static Class<?> getPrimitiveClass(String type){
        switch(type.toLowerCase())
        {
            case "int":
                return int.class;
            case "char":
                return char.class;
            case "byte":
                return byte.class;
            case "short":
                return short.class;
            case "float":
                return float.class;
            case "double":
                return double.class;
            case "long":
                return long.class;
            case "boolean":
                return boolean.class;
        }
        return null;
    }
    
    
    /**
     * 判断是否为包装类型（Integer/Long...等）
     * 
     * @param clazz
     * @return
     */
    public static boolean isWrapClass(Class<?> clazz)
    {
        try
        {
            return ((Class<?>) clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e)
        {
            return false;
        }
    }

    /**
     * 检查传入的class对象是否为java类型，
     * 
     * @param clazz
     * @return
     */
    public static boolean isJavaClass(Class<?> clazz)
    {
        return clazz != null && clazz.getClassLoader() == null;
    }
    
}
