/**
 * <p>文件名称: TxcField.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tx.db.datasource.relational.txc.parser;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class TxcField
{
    private String name;

    // DiffUtils 比对时忽略此字段
    @JsonIgnore
    private int type;
    private Object value;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public String getSqlName()
    {
        return "`" + name + "`";
    }

    @Override
    public String toString()
    {
        return String.format("[%s,%s]",
                new Object[] { this.name, String.valueOf(this.value) });
    }
}