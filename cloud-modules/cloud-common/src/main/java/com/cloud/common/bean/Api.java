/**
 * <p>文件名称: Api.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.common.bean;

import java.io.Serializable;
import java.util.List;

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
    private String id;

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
    private String desc;

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
    private String instanceName;

    /**
     * 业务类方法名
     */
    private String methodName;

    /**
     * 是否可用
     */
    private Boolean enable = true;

    /**
     * 请求地址信息
     */
    private String uri;

    /**
     * 入参列表
     */
    private List<ApiParam> inputParams;

    /**
     * @return the id
     */
    public String getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * @return the code
     */
    public String getCode()
    {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code)
    {
        this.code = code;
    }

    /**
     * @return the aliasCode
     */
    public String getAliasCode()
    {
        return aliasCode;
    }

    /**
     * @param aliasCode the aliasCode to set
     */
    public void setAliasCode(String aliasCode)
    {
        this.aliasCode = aliasCode;
    }

    /**
     * @return the desc
     */
    public String getDesc()
    {
        return desc;
    }

    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    /**
     * @return the type
     */
    public ApiType getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(ApiType type)
    {
        this.type = type;
    }

    /**
     * @return the timeout
     */
    public Integer getTimeout()
    {
        return timeout;
    }

    /**
     * @param timeout the timeout to set
     */
    public void setTimeout(Integer timeout)
    {
        this.timeout = timeout;
    }

    /**
     * @return the serviceId
     */
    public String getServiceId()
    {
        return serviceId;
    }

    /**
     * @param serviceId the serviceId to set
     */
    public void setServiceId(String serviceId)
    {
        this.serviceId = serviceId;
    }

    /**
     * @return the instanceName
     */
    public String getInstanceName()
    {
        return instanceName;
    }

    /**
     * @param instanceName the instanceName to set
     */
    public void setInstanceName(String instanceName)
    {
        this.instanceName = instanceName;
    }

    /**
     * @return the methodName
     */
    public String getMethodName()
    {
        return methodName;
    }

    /**
     * @param methodName the methodName to set
     */
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }

    /**
     * @return the enable
     */
    public Boolean getEnable()
    {
        return enable;
    }

    /**
     * @param enable the enable to set
     */
    public void setEnable(Boolean enable)
    {
        this.enable = enable;
    }

    /**
     * @return the uri
     */
    public String getUri()
    {
        return uri;
    }

    /**
     * @param uri the uri to set
     */
    public void setUri(String uri)
    {
        this.uri = uri;
    }

    /**
     * @return the inputParams
     */
    public List<ApiParam> getInputParams()
    {
        return inputParams;
    }

    /**
     * @param inputParams the inputParams to set
     */
    public void setInputParams(List<ApiParam> inputParams)
    {
        this.inputParams = inputParams;
    }

}
