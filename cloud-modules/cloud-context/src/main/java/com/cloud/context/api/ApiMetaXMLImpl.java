package com.cloud.context.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.cloud.common.access.ApiMetaParse;
import com.cloud.common.bean.Api;
import com.cloud.common.bean.ApiParam;
import com.cloud.common.utils.ReflectionUtil;
import com.cloud.common.utils.XMLUtil;
import com.cloud.context.configuration.ContextProperties;

/**
 * 通过xml文件的方式解析接口元数据
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月23日
 */
@Component("apiMetaXmlImpl")
public class ApiMetaXMLImpl implements ApiMetaParse
{
    @Autowired
    private ContextProperties contextProperties;

    @Override
    public List<Api> parse()
    {
        String xmlPath = contextProperties.getApi().getLocation();
        String path = ClassUtils.getDefaultClassLoader().getResource(xmlPath).getPath();
        InputStream is = null;
        try
        {
            is = new FileInputStream(path);
            Document document = XMLUtil.doXMLParse(is);
            Element root = document.getRootElement();
            
            List<Api> listApi = new ArrayList<Api>();
            List<Element> interfaces = root.getChildren("interface");
            interfaces.stream().forEach(item -> {
                Api api = new Api();
                //设置接口信息
                setObjectAttr(item.getAttributes(), api);
                //设置输入参数
                Element inputsElement = item.getChild("inputs");
                //参数信息
                List<Element> inputs = inputsElement.getChildren("input");
                List<ApiParam> inputParams = new ArrayList<>();
                inputs.stream().forEach(input -> {
                    ApiParam apiParam = new ApiParam();
                    setObjectAttr(input.getAttributes(), apiParam);
                    inputParams.add(apiParam);
                });
                api.setInputParams(inputParams);
                listApi.add(api);
            });
            
            System.err.println("*************************");
            System.err.println("*************************listApi = " + listApi);
            System.err.println("*************************");
            
        } catch (Exception e)
        {
            e.printStackTrace();
        }finally{
            if(is != null){
                try
                {
                    is.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
            

        return null;
    }

    public void setObjectAttr(List<Attribute> attrs, Object obj)
    {
        attrs.stream().forEach(attr -> {
            String attrName = attr.getName();
            String attrValue = attr.getValue();
            ReflectionUtil.setFieldValue(obj, attrName, attrValue);
        });
    }
    
    
}
