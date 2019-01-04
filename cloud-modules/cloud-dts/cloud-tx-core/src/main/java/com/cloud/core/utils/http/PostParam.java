/**
 * <p>文件名称: PostParam.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core.utils.http;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */

public class PostParam
{

    private String name;
    private String value;

    public String getName()
    {
        if (name == null)
            return "";
        else
            return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getValue()
    {
        if (value == null)
            return "";
        else
            return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public PostParam()
    {
    }

    public PostParam(String name, String value)
    {
        this.name = name;
        this.value = value;
    }

}