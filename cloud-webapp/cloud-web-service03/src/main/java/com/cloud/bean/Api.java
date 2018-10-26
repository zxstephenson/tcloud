/**
 * <p>文件名称: Api.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.bean;

import java.io.Serializable;
import java.util.List;

import com.cloud.annotation.ApiType;
import com.cloud.annotation.ServiceCategoryType;

/**
 * 接口定义 接口定义
 * 
 * @author Ren_C
 * @version 3.1.0 2018年02月12日
 */
public class Api implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -8710552811637842137L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 接口编号
     */
    private String code;

    /**
     * 接口别名
     */
    private String aliasCode;

    /**
     * 接口中文名
     */
    private String name;

    /**
     * 接口类型
     */
    private ApiType type;

    /**
     * 超时时长
     */
    private Integer timeout;

    /**
     * 服务ID
     */
    private String serviceId;

    /**
     * 业务类实例名
     */
    private String serviceName;

    /**
     * 业务类方法名
     */
    private String methodName;

    /**
     * 是否可用
     */
    private Boolean enable = true;

    /**
     * 数据源
     */
    private String datasource;

    /**
     * 请求地址信息
     */
    private String uri;

    /**
     * 服务类型
     */
    private ServiceCategoryType serviceCategory;

    /**
     * 入参列表
     */
    private List<ApiParam> inputParams;
    /**
     * 出参列表
     */
//    private List<ApiParam> outputParams;

    public Api()
    {
    }

    public Api(String code)
    {
        this.code = code;
    }

    public Integer getTimeout()
    {
        return timeout;
    }

    public void setTimeout(Integer timeout)
    {
        this.timeout = timeout;
    }

    public String getServiceId()
    {
        return serviceId;
    }

    public void setServiceId(String serviceId)
    {
        this.serviceId = serviceId;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getAliasCode()
    {
        return aliasCode;
    }

    public void setAliasCode(String aliasCode)
    {
        this.aliasCode = aliasCode;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Boolean getEnable()
    {
        return enable;
    }

    public void setEnable(Boolean enable)
    {
        this.enable = enable;
    }

    public List<ApiParam> getInputParams()
    {
        return inputParams;
    }

    public void setInputParams(List<ApiParam> inputParams)
    {
        this.inputParams = inputParams;
    }
    /*
    public List<ApiParam> getOutputParams()
    {
        return outputParams;
    }

    public void setOutputParams(List<ApiParam> outputParams)
    {
        this.outputParams = outputParams;
    }*/

    public ApiType getType()
    {
        return type;
    }

    public void setType(ApiType type)
    {
        this.type = type;
    }

    public String getServiceName()
    {
        return serviceName;
    }

    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }

    public String getMethodName()
    {
        return methodName;
    }

    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }

    public String getDatasource()
    {
        return datasource;
    }

    public void setDatasource(String datasource)
    {
        this.datasource = datasource;
    }

    public String getUri()
    {
        return uri;
    }

    public void setUri(String uri)
    {
        this.uri = uri;
    }

    public ServiceCategoryType getServiceCategory()
    {
        return serviceCategory;
    }

    public void setServiceCategory(ServiceCategoryType serviceCategory)
    {
        this.serviceCategory = serviceCategory;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return "Api [id=" + id + ", code=" + code + ", aliasCode=" + aliasCode
                + ", name=" + name + ", type=" + type + ", timeout=" + timeout
                + ", serviceId=" + serviceId + ", serviceName=" + serviceName
                + ", methodName=" + methodName + ", enable=" + enable
                + ", datasource=" + datasource + ", uri=" + uri
                + ", serviceCategory=" + serviceCategory + ", inputParams="
                + inputParams/* + ", outputParams=" + outputParams*/ + "]";
    }

}
