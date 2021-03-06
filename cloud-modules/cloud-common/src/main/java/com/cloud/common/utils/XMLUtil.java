package com.cloud.common.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年10月23日
 */

public class XMLUtil
{
    /**
     * 解析request流对象InputStream 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     */
    
    @SuppressWarnings("all") 
    public static Document doXMLParse(InputStream is)
            throws JDOMException, IOException
    {
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(is);
        return doc;
    }
     

    /**
     * 解析String类型的xml流对象InputStream
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     */
    @SuppressWarnings("all")
    public static Document doXMLParse(final String strxml)
            throws JDOMException
    {
        if (null == strxml || "".equals(strxml))
        {
            return null;
        }

        Map<String, String> m = new HashMap<String, String>();
        InputStream in = null;
        try
        {
            in = new ByteArrayInputStream(strxml.getBytes());
            SAXBuilder builder = new SAXBuilder();
            return builder.build(in);
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }  finally{
            if(in != null)
            {
                // 关闭流
                try
                {
                    in.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 获取子结点的xml
     */
    @SuppressWarnings("all")
    private static String getChildrenText(final List children)
    {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty())
        {
            Iterator it = children.iterator();
            while (it.hasNext())
            {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty())
                {
                    sb.append(XMLUtil.getChildrenText(list));
                }
                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    /**
     * 将请求参数转换为xml格式的string 
     * 
     */
    @SuppressWarnings("all")
    public static String getRequestXml(
            final SortedMap<String, String> parameters)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k)
                    || "sign".equalsIgnoreCase(k))
            {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else
            {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * main
     */
    public static void main(final String[] arg)
    {
        SortedMap<String, String> sm = new TreeMap<String, String>();
        sm.put("SUCCESS", "OK");
        String ss = getRequestXml(sm);
        System.out.println(ss);
    }

}
