/**
 * <p>文件名称: TxcLine.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tx.db.datasource.relational.txc.parser;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class TxcLine
{

    private List<TxcField> fields = new ArrayList();

    // DiffUtils 比对时忽略此字段
    @JsonIgnore
    private Object primaryKey;

    @JsonIgnore
    private Object primaryValue;

    public List<TxcField> getFields()
    {
        return fields;
    }

    public void setFields(List<TxcField> fields)
    {
        this.fields = fields;
    }

    public Object getPrimaryKey()
    {
        return primaryKey;
    }

    public void setPrimaryKey(Object primaryKey)
    {
        this.primaryKey = primaryKey;
    }

    public Object getPrimaryValue()
    {
        return primaryValue;
    }

    public void setPrimaryValue(Object primaryValue)
    {
        this.primaryValue = primaryValue;
    }
}