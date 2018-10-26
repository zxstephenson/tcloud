package com.cloud.utils;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月19日
 */
/**
 * 判断输入参数的类型
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年3月23日
 */
public class TypeUtil
{

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

