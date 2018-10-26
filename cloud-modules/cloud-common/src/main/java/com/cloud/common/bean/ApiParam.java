/**
 * <p>文件名称: ApiParam.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.common.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 参数定义 参数定义
 * 
 * @author zhangxin
 * @version 3.1.0 2018年10月23日
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
    private String id;

    /**
     * 接口编号
     */
    private String apiCode;

    /**
     * 参数名
     */
    private String code;

    /**
     * 参数中文名
     */
    private String desc;

    /**
     * 参数类型
     */
    private String type;

    /**
     * 限定数值的最大值
     */
    private int max = Integer.MAX_VALUE;
    
    /**
     * 限定数值的最小值
     */
    private int min = Integer.MIN_VALUE;
    
    /**
     * 用于检查数组、List的长度
     */
    private int size;
    
    /**
     * 用于检查String的长度
     */
    private int length;
    
    /**
     * 是否可以为空,默认为true
     */
    private boolean nullable = true;
    
    /**
     * 设置BigDecimal类型的最大取值
     */
    private BigDecimal decimalMax = new BigDecimal(Long.MAX_VALUE);
    
    /**
     * 设置BigDecimal类型的最小取值
     */
    private BigDecimal decimalMin = new BigDecimal(Long.MIN_VALUE);

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
     * @return the apiCode
     */
    public String getApiCode()
    {
        return apiCode;
    }

    /**
     * @param apiCode the apiCode to set
     */
    public void setApiCode(String apiCode)
    {
        this.apiCode = apiCode;
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
    public String getType()
    {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * @return the max
     */
    public int getMax()
    {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max)
    {
        this.max = max;
    }

    /**
     * @return the min
     */
    public int getMin()
    {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min)
    {
        this.min = min;
    }

    /**
     * @return the size
     */
    public int getSize()
    {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size)
    {
        this.size = size;
    }

    /**
     * @return the length
     */
    public int getLength()
    {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length)
    {
        this.length = length;
    }

    /**
     * @return the nullable
     */
    public boolean isNullable()
    {
        return nullable;
    }

    /**
     * @param nullable the nullable to set
     */
    public void setNullable(boolean nullable)
    {
        this.nullable = nullable;
    }

    /**
     * @return the decimalMax
     */
    public BigDecimal getDecimalMax()
    {
        return decimalMax;
    }

    /**
     * @param decimalMax the decimalMax to set
     */
    public void setDecimalMax(BigDecimal decimalMax)
    {
        this.decimalMax = decimalMax;
    }

    /**
     * @return the decimalMin
     */
    public BigDecimal getDecimalMin()
    {
        return decimalMin;
    }

    /**
     * @param decimalMin the decimalMin to set
     */
    public void setDecimalMin(BigDecimal decimalMin)
    {
        this.decimalMin = decimalMin;
    }

}
