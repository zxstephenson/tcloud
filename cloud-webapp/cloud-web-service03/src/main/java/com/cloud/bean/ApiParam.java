/**
 * <p>文件名称: ApiParam.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.bean;

import java.io.Serializable;

/**
 * 参数定义 参数定义
 * 
 * @author Ren_C
 * @version 3.1.0 2018年02月12日
 */
public class ApiParam implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = -2228675566773369589L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 服务编号
     */
    private String appId;

    /**
     * 接口编号
     */
    private String apiCode;

    /**
     * 参数类型(in/out)
     */
    private String paraType;

    /**
     * 参数名
     */
    private String paraCode;

    /**
     * 参数中文名
     */
    private String paraName;

    /**
     * 参数数据类型
     */
    private ParamDataType dataType;

    /**
     * 参数宽度，非数字可用
     */
    private Integer width;

    /**
     * 参数精度，小数点前，数字可用
     */
    private Integer prec;

    /**
     * 参数小数精度，小数后，数字可用
     */
    private Integer scale;

    /**
     * 可否为空
     */
    private Boolean nullable;

    /**
     * 不可为空时，对就的默认值
     */
    private Object defVal;

    /**
     * 扩展检查类注入spring的bean name
     */
    private String checkBean;

    /**
     * 翻译字典读取方式
     */
    private TranslateRefType refType;

    /**
     * 翻译时对应的code
     */
    private String refCode;

    /**
     * 节点分发计算字段
     */
    private String nodeField;
    
    
    private String beanPath;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getAppId()
    {
        return appId;
    }

    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public String getApiCode()
    {
        return apiCode;
    }

    public void setApiCode(String apiCode)
    {
        this.apiCode = apiCode;
    }

    public String getParaType()
    {
        return paraType;
    }

    public void setParaType(String paraType)
    {
        this.paraType = paraType;
    }

    public String getParaCode()
    {
        return paraCode;
    }

    public void setParaCode(String paraCode)
    {
        this.paraCode = paraCode;
    }

    public String getParaName()
    {
        return paraName;
    }

    public void setParaName(String paraName)
    {
        this.paraName = paraName;
    }

    public ParamDataType getDataType()
    {
        return dataType;
    }

    public void setDataType(ParamDataType dataType)
    {
        this.dataType = dataType;
    }

    public Integer getWidth()
    {
        return width;
    }

    public void setWidth(Integer width)
    {
        this.width = width;
    }

    public Integer getPrec()
    {
        return prec;
    }

    public void setPrec(Integer prec)
    {
        this.prec = prec;
    }

    public Integer getScale()
    {
        return scale;
    }

    public void setScale(Integer scale)
    {
        this.scale = scale;
    }

    public Boolean getNullable()
    {
        return nullable;
    }

    public void setNullable(Boolean nullable)
    {
        this.nullable = nullable;
    }

    public Object getDefVal()
    {
        return defVal;
    }

    public void setDefVal(Object defVal)
    {
        this.defVal = defVal;
    }

    public String getCheckBean()
    {
        return checkBean;
    }

    public void setCheckBean(String checkBean)
    {
        this.checkBean = checkBean;
    }

    public TranslateRefType getRefType()
    {
        return refType;
    }

    public void setRefType(TranslateRefType refType)
    {
        this.refType = refType;
    }

    public String getRefCode()
    {
        return refCode;
    }

    public void setRefCode(String refCode)
    {
        this.refCode = refCode;
    }

    public String getNodeField()
    {
        return nodeField;
    }

    public void setNodeField(String nodeField)
    {
        this.nodeField = nodeField;
    }
    
    /**
     * @return the beanPath
     */
    public String getBeanPath()
    {
        return beanPath;
    }

    /**
     * @param beanPath the beanPath to set
     */
    public void setBeanPath(String beanPath)
    {
        this.beanPath = beanPath;
    }

    @Override
    public String toString()
    {
        return "ApiParam [id=" + id + ", beanPath=" +beanPath+", appId=" + appId + ", apiCode="
                + apiCode + ", paraType=" + paraType + ", paraCode=" + paraCode
                + ", paraName=" + paraName + ", dataType=" + dataType
                + ", width=" + width + ", prec=" + prec + ", scale=" + scale
                + ", nullable=" + nullable + ", defVal=" + defVal
                + ", checkBean=" + checkBean + ", refType=" + refType
                + ", refCode=" + refCode + ", nodeField=" + nodeField + "]";
    }
     
}
