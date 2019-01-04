/**
 * <p>文件名称: MapModel.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core.model;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class MapModel implements Serializable
{

    public Map<String, Object> toMap()
    {
        Class clazz = getClass();
        Map<String, Object> maps = new HashMap<String, Object>();
        PropertyDescriptor[] propertyDescriptors = null;
        try
        {
            propertyDescriptors = propertyDescriptors(clazz);
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors)
            {
                Method gmethod = propertyDescriptor.getReadMethod();
                Method smethod = propertyDescriptor.getWriteMethod();
                if (null != gmethod && smethod != null)
                {
                    try
                    {
                        maps.put(propertyDescriptor.getName(),
                                propertyDescriptor.getReadMethod()
                                        .invoke(this));
                    } catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            return maps;
        } catch (SQLException e)
        {
            return null;
        }
    }

    private PropertyDescriptor[] propertyDescriptors(Class<?> c)
            throws SQLException
    {
        BeanInfo beanInfo = null;
        try
        {
            beanInfo = Introspector.getBeanInfo(c);
        } catch (IntrospectionException var4)
        {
            throw new SQLException(
                    "Bean introspection failed: " + var4.getMessage());
        }
        return beanInfo.getPropertyDescriptors();
    }
}
