/**
 * <p>文件名称: TxcTable.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tx.db.datasource.relational.txc.parser;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class TxcTable
{

    public String schemaName;
    public String tableName;
    public String alias;

    private List<TxcLine> line = new ArrayList();

    @Override
    public String toString()
    {
        StringBuilder localStringBuilder = new StringBuilder();
        for (int i = 0; i < this.line.size(); i++)
        {

            for (TxcField field : this.line.get(i).getFields())
            {

                switch (field.getType())
                {
                case -15:
                case -9:
                case -6:
                case -5:
                case 1:
                case 2:
                case 4:
                case 12:
                case 2003:
                    localStringBuilder.append(field.getValue()).append(',');
                default:
                }
            }
        }
        return localStringBuilder.toString();
    }

    public String getSchemaName()
    {
        return schemaName;
    }

    public void setSchemaName(String schemaName)
    {
        this.schemaName = schemaName;
    }

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getAlias()
    {
        return alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public List<TxcLine> getLine()
    {
        return line;
    }

    public void setLine(List<TxcLine> line)
    {
        this.line = line;
    }
}
