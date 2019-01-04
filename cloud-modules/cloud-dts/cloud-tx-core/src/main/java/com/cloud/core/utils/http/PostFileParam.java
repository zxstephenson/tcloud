/**
 * <p>文件名称: PostFileParam.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.core.utils.http;

import org.apache.http.entity.ContentType;

import java.io.File;
import java.io.InputStream;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class PostFileParam
{

    private String name;
    private Object value;
    private ContentType contentType;

    private String fileName;

    public String getFileName()
    {
        return fileName;
    }

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

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public ContentType getContentType()
    {
        return contentType;
    }

    public void setContentType(ContentType contentType)
    {
        this.contentType = contentType;
    }

    public PostFileParam(String name, File value)
    {
        this.name = name;
        this.value = value;
        this.contentType = contentType;
    }

    public PostFileParam(String name, File value, ContentType contentType)
    {
        this.name = name;
        this.value = value;
        this.contentType = contentType;
    }

    public PostFileParam(String name, String value)
    {
        this.name = name;
        this.value = value;
        this.contentType = contentType;
    }

    public PostFileParam(String name, String value, ContentType contentType)
    {
        this.name = name;
        this.value = value;
        this.contentType = contentType;
    }

    public PostFileParam(String name, String fileName, InputStream value,
            ContentType contentType)
    {
        this.fileName = fileName;
        this.name = name;
        this.value = value;
        this.contentType = contentType;
    }

    public PostFileParam(String name, String fileName, InputStream value)
    {
        this.fileName = fileName;
        this.name = name;
        this.value = value;
        this.contentType = contentType;
    }

}