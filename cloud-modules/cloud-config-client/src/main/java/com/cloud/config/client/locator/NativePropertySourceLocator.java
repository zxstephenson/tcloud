package com.cloud.config.client.locator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.CompositePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.util.ClassUtils;
import org.springframework.util.StreamUtils;

import com.cloud.common.utils.JsonUtil;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年11月20日
 */
@Order(1)
public class NativePropertySourceLocator implements PropertySourceLocator
{

    private static final Logger logger = LoggerFactory.getLogger(NativePropertySourceLocator.class);
    
    @Autowired(required = false)
    private List<PropertySourceLocator> propertySourceLocators = new ArrayList<>();
    
    @Autowired(required = false)
    private ConfigClientProperties configClientProperties;
    
    @Override
    public PropertySource<?> locate(Environment environment)
    {
        boolean backUpIsSuccess = false;
        for(PropertySourceLocator locator : propertySourceLocators)
        {
            //从ConfigServer获取配置信息的PropertySourceLocator
            if(locator instanceof ConfigServicePropertySourceLocator)
            {
                backUpIsSuccess = backUpRemoteActivePropertiesFile(environment, locator);
            }
        }
        //如果这里将远程配置文件备份成功，则表示成功从configServer获取到了配置文件，因此直接返回。否则加载本地配置文件
        if(backUpIsSuccess)
        {
            return null;
        }else
        {
            //加载本地配置文件
            return loadNativePropertyFile(environment);  
        }
        
    }

    private String getBackUpFileName()
    {
        String fileName = "application.properties";
        if (null != configClientProperties)
        {
            String label = configClientProperties.getLabel();
            String name = configClientProperties.getName();
            String profile = configClientProperties.getProfile();
            fileName = name + "-" + profile + "-" + label + ".properties";
        }
        return fileName;
    }
    
    /**
     * 读取从configServer获取到的配置信息，将配置项信息存储到本地文件中。
     * @param environment
     * @param locator
     * @param outputFileName
     * @return
     */
    private boolean backUpRemoteActivePropertiesFile(Environment environment,
            PropertySourceLocator locator)
    {

        String outputFileName = getBackUpFileName();
        
        System.err.println(
                "##############ConfigServicePropertySourceLocator##############");
        
        CompositePropertySource source = (CompositePropertySource) locator.locate(environment);
        if (null != source)
        {
            StringBuilder propertiesBuilder = new StringBuilder();
            String[] properties = source.getPropertyNames();
            for (String propertyName : properties)
            {
                String value = String.valueOf(source.getProperty(propertyName));
                propertiesBuilder.append(propertyName + "=" + value + "\r\n");
            }

            //如果没有获取到配置信息，直接返回false，不用将配置信息备份到本地
            if(propertiesBuilder.length() == 0)
            {
                return false;
            }
            
            return copyStringToFile(propertiesBuilder.toString(), outputFileName);
        }

        return false;
    }

    /**
     * 将str中的内容拷贝到outputFileName对应的文件中
     * @param str
     * @param outputFileName
     * @return
     */
    private boolean copyStringToFile(String str, String outputFileName)
    {
        
        OutputStream os = null;
        boolean copyIsSuccess = true;
        try
        {
            File file = new File(outputFileName);
            
            if(file.exists())
            {
                file.delete();
            }
            
            os = new FileOutputStream(file);
            StreamUtils.copy(str, Charset.forName("UTF-8"), os);
        } catch (IOException e)
        {
            copyIsSuccess = false;
            e.printStackTrace();
        } finally
        {
            if (null != os)
            {
                try
                {
                    os.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return copyIsSuccess;
    }

    /**
     * 加载本地配置文件启动服务
     * @param environment
     * @param fileName
     * @return
     */
    private NativePropertySource loadNativePropertyFile(Environment environment)
    {
        String outputFileName = getBackUpFileName();
        //定义一个map来存储从本地文件读取的配置信息
        Map<String,String> properties = new HashMap<>();
        
        File file = new File(outputFileName);
        //如果本地文件不存在，则直接返回null
        if(!file.exists())
        {
            return null;
        }
        
        try
        {
            String json = StreamUtils.copyToString(new FileInputStream(file), Charset.forName("UTF-8"));
            String[] array = json.split("\r\n");
            for(String item : array){
                String key = item.split("=")[0];
                String value = item.split("=")[1];
                System.out.println(item.split("=")[0] + " : " + item.split("=")[1]);
                properties.put(key, value);
            }
            
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        
        NativePropertySource myPropertySource = new NativePropertySource("myPropertySource", properties);
        System.err.println("==============================");
        logger.info(JsonUtil.beanToJson(properties));
        System.err.println("==============================");
        return myPropertySource;
    }

}
