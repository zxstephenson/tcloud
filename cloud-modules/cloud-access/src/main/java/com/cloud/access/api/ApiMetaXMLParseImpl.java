package com.cloud.access.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.cloud.access.RequestAspect;
import com.cloud.common.access.ApiMetaParse;
import com.cloud.common.bean.Api;
import com.cloud.common.bean.ApiParam;
import com.cloud.common.bean.ApiType;
import com.cloud.common.utils.ReflectionUtil;
import com.cloud.common.utils.StringUtil;
import com.cloud.common.utils.XMLUtil;
import com.cloud.context.configuration.ContextProperties;

/**
 * 通过xml文件的方式解析接口元数据
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月23日
 */
@Component("apiMetaXmlParseImpl")
public class ApiMetaXMLParseImpl implements ApiMetaParse
{
    private static final Logger logger = LoggerFactory.getLogger(ApiMetaXMLParseImpl.class);
    @Autowired
    private ContextProperties contextProperties;

    @Override
    public List<Api> parse()
    {
        String xmlPath = contextProperties.getApi().getMetaLocation();
        if(StringUtil.isEmpty(xmlPath))
        {
            return new ArrayList<Api>();
        }
        
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
                String type = item.getAttributeValue("type");
                ApiType apiType = ApiType.getApiType(type);
                if(apiType != ApiType.UNDEFINED)
                {
                    //type是一个枚举类型，需单独处理，然后将type属性从元素中删除
                    api.setType(apiType);
                    item.removeAttribute("type");
                    
                    //设置接口信息
                    setObjectAttr(item.getAttributes(), api);
                    
                    if(apiType == ApiType.BEAN && StringUtil.isNotEmpty(api.getUri()))
                    {
                        String uri = api.getUri();
                        if(uri.contains(":"))
                        {
                            api.setInstanceName(uri.split(":")[0]);
                            api.setMethodName(uri.split(":")[1]);
                        }
                    }
                    
                    //设置输入参数
                    Element inputsElement = item.getChild("inputs");
                    //参数信息
                    List<Element> inputs = inputsElement.getChildren("input");
                    List<ApiParam> inputParams = new ArrayList<>();
                    inputs.stream().forEach(input -> {
                        ApiParam apiParam = new ApiParam();
                        apiParam.setApiCode(api.getCode());
                        setObjectAttr(input.getAttributes(), apiParam);
                        inputParams.add(apiParam);
                    });
                    api.setInputParams(inputParams);
                    listApi.add(api);
                }
            });
            
            logger.info("*************************");
            logger.info("*************************listApi = " + listApi);
            logger.info("*************************");
            return listApi;
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
