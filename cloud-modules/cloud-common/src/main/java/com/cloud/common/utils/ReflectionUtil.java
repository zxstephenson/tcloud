package com.cloud.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

/**
 * 反射工具类
 * @author    zhangxin4
 * @version   3.1.0 2018年10月22日
 */

public class ReflectionUtil
{
    private static LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
    
    /** 
     * 循环向上转型, 获取Method对象 
     * @param object : 子类对象 
     * @param methodName : 父类中的方法名 
     * @param parameterTypes : 父类中的方法参数类型 
     * @return 父类中的方法对象 
     */
    public static Method getDeclaredMethod(Object object, String methodName, Class<?> ... parameterTypes)
    {
        Method method = null;
        
        for(Class<?> clazz = object.getClass() ; clazz != Object.class ; clazz = clazz.getSuperclass()) 
        {
            try
            {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
                //这里甚么都不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会进入
            }
        }
        
        return null;
    }  
      
    /** 
     * 直接调用对象方法
     * @param object : 子类对象 
     * @param methodName : 父类中的方法名 
     * @param parameterTypes : 父类中的方法参数类型 
     * @param parameters : 父类中的方法参数 
     * @return 父类中方法的执行结果 
     */  
    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes,  
            Object [] parameters) 
    {
        //根据 对象、方法名和对应的方法参数 通过取 Method 对象  
        Method method = null;
        if(parameterTypes == null)
        {
            method = getDeclaredMethod(object, methodName);   
        }else
        {
            method = getDeclaredMethod(object, methodName, parameterTypes);
        }
        
        //抑制Java对方法进行检查,主要是针对私有方法而言  
        method.setAccessible(true);
        
        try
        {
            if(null != method)
            {
                //调用object 的 method 所代表的方法，其方法的参数是 parameters
                if(parameters == null)
                {
                    return method.invoke(object);                    
                }else
                {
                    return method.invoke(object, parameters);
                }
            }
        } catch (IllegalArgumentException e) 
        {
            e.printStackTrace();
        } catch (IllegalAccessException e) 
        {
            e.printStackTrace();
        } catch (InvocationTargetException e) 
        {
            e.printStackTrace();
        }
        
        return null;  
    }  
    
    /**
     * 获取指定对象，指定方法的参数名
     * @param clazz
     * @param methodName
     * @return
     */
    public static List<String> getParamterName(Class<?> clazz, String methodName) 
    {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) 
        {
            if (methodName.equals(method.getName())) 
            {
                return getParamterName(method);
            }
        }
        return null;
    }
    
    /**
     * 获取指定对象，指定方法的参数名
     * @param clazz
     * @param method
     * @return
     */
    public static List<String> getParamterName(Method method) 
    {
        //获取到指定方法的所有参数名
        String[] params = u.getParameterNames(method);
        if(params == null)
        {
            return null;
        }
        return Arrays.asList(params);
    }
    
    
    /** 
     * 循环向上查找, 获取fieldName指定的Field对象，为找到返回null
     * @param object : 子类对象 
     * @param fieldName : 父类中     
     * @return Field对象
     */  
    public static Field getDeclaredField(Object object, String fieldName)
    {
        Class<?> clazz = object.getClass();
        return getDeclaredField(clazz, fieldName);
    }

    /**
     * 获取origClazz中fieldName指定的Field对象
     * @param origClazz
     * @param fieldName
     * @return
     */
    public static Field getDeclaredField(Class<?> origClazz, String fieldName)
    {
        Field field = null ;  
        
        for (Class<?> clazz = origClazz; clazz != Object.class; clazz = clazz.getSuperclass())
        {
            try
            {
                field = clazz.getDeclaredField(fieldName);
                if (field != null)
                {
                    return field;
                }
            } catch (NoSuchFieldException e)
            {
                //这里甚么都不能抛出去。  
                //如果这里的异常打印或者往外抛，则就不会进入                
            }
        }

        return field;
    }
    
    /** 
     * 直接设置对象属性值
     * @param object : 子类对象 
     * @param fieldName : 属性名
     * @param value : 将要设置的值 
     */  
      
    public static void setFieldValue(Object object, String fieldName, Object value)
    {  
        
        //根据 对象和属性名通过取 Field对象  
        Field field = getDeclaredField(object, fieldName) ;  
        if(field != null)
        {
          //抑制Java对其的检查  
            field.setAccessible(true) ;  
            
            try 
            {
                //将 object 中 field 所代表的值 设置为 value  
                field.set(object, value);  
            } catch (IllegalArgumentException e)
            {
                e.printStackTrace();  
            } catch (IllegalAccessException e)
            {
                e.printStackTrace();  
            }  
        }
    }  
      
    /** 
     * 直接读的属性值
     * @param object : 子类对象 
     * @param fieldName : 父类中     
     * @return : 父类中    
     */  
    public static Object getFieldValue(Object object, String fieldName)
    {
        //根据 对象和属性名通过取 Field对象  
        Field field = getDeclaredField(object, fieldName) ;  
        
        if(field != null)
        {
          //抑制Java对其的检查  
            field.setAccessible(true) ;  
            
            try 
            {
                //获的属性值  
                return field.get(object) ;  
            } catch(Exception e)
            {
                e.printStackTrace() ;  
            }
            
        }
        
        return null;  
    }  

    /**
     * 创建对象
     * @param className
     * @return
     */
    public static Object createInstance(String className){
        try
        {
            if(StringUtil.isEmpty(className))
            {
                return null;
            }
            
            Class<?> clazz = Class.forName(className);
            Object instance = clazz.newInstance();
            return instance;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
    
}
