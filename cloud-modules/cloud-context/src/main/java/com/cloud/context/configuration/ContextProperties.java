package com.cloud.context.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年10月23日
 */
@Configuration
@ConfigurationProperties("tcloud.context")
public class ContextProperties
{
    private Api api = new Api();
    
    private List<String> systemInit = new ArrayList<>();
    
    /**
     * @return the api
     */
    public Api getApi()
    {
        return api;
    }

    /**
     * @param api the api to set
     */
    public void setApi(Api api)
    {
        this.api = api;
    }

    /**
     * @return the systemInit
     */
    public List<String> getSystemInit()
    {
        return systemInit;
    }

    /**
     * @param systemInit the systemInit to set
     */
    public void setSystemInit(List<String> systemInit)
    {
        this.systemInit = systemInit;
    }



    public static class Api{
        
        private String location = "";
        
        private String apiMetaImpl = "apiMetaXmlParseImpl";

        /**
         * @return the location
         */
        public String getLocation()
        {
            return location;
        }

        /**
         * @param location the location to set
         */
        public void setLocation(String location)
        {
            this.location = location;
        }

        /**
         * @return the apiMetaImpl
         */
        public String getApiMetaImpl()
        {
            return apiMetaImpl;
        }

        /**
         * @param apiMetaImpl the apiMetaImpl to set
         */
        public void setApiMetaImpl(String apiMetaImpl)
        {
            this.apiMetaImpl = apiMetaImpl;
        }

        @Override
        public String toString()
        {
            return "Api [location=" + location + ", parseApiMeta="
                    + apiMetaImpl + "]";
        } 
        
    }

    @Override
    public String toString()
    {
        return "ContextProperties [api=" + api + "]";
    }
    
    
}