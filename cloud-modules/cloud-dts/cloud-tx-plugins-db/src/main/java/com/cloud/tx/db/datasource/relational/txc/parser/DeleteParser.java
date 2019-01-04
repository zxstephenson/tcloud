/**
 * <p>文件名称: DeleteParser.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tx.db.datasource.relational.txc.parser;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlDeleteStatement;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;

import java.util.List;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class DeleteParser extends AbstractParser<MySqlDeleteStatement>
{
    private static DeleteParser instance = null;

    public static DeleteParser getInstance()
    {
        if (instance == null)
        {
            synchronized (DeleteParser.class)
            {
                if (instance == null)
                {
                    instance = new DeleteParser();
                }
            }
        }
        return instance;
    }

    @Override
    protected List<Object> getWhereParams(List<Object> sqlParamsList,
            MySqlDeleteStatement parseSqlStatement)
    {
        if (CollectionUtils.isNotEmpty(sqlParamsList))
        {
            return sqlParamsList;
        }
        return ListUtils.EMPTY_LIST;
    }

    @Override
    protected String getWhere(MySqlDeleteStatement parseSqlStatement)
    {
        return SqlUtils.toSQLString(parseSqlStatement.getWhere());
    }

    @Override
    public TxcTable getPresentValue(List<Object> sqlParamsList,
            MySqlDeleteStatement parseSqlStatement)
    {
        return null;
    }

    @Override
    public SQLType getSqlType()
    {
        return SQLType.DELETE;
    }

    @Override
    protected String selectSql(MySqlDeleteStatement mySqlUpdateStatement,
            String primaryKeyName)
    {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SELECT * ");

        //
        stringBuffer.append(" from ")
                .append(mySqlUpdateStatement.getTableName().getSimpleName())
                .append(" where ");
        //
        stringBuffer
                .append(SqlUtils.toSQLString(mySqlUpdateStatement.getWhere()));
        return stringBuffer.toString();
    }

    @Override
    protected String getTableName(MySqlDeleteStatement parseSqlStatement)
    {
        return parseSqlStatement.getTableName().getSimpleName();
    }

}
