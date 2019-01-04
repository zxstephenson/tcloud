/**
 * <p>文件名称: InsertParser.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tx.db.datasource.relational.txc.parser;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.statement.SQLInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import org.apache.commons.collections.CollectionUtils;

import java.sql.Connection;
import java.util.List;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class InsertParser extends AbstractParser<MySqlInsertStatement>
{

    private static InsertParser instance = null;

    public static InsertParser getInstance()
    {
        if (instance == null)
        {
            synchronized (InsertParser.class)
            {
                if (instance == null)
                {
                    instance = new InsertParser();
                }
            }
        }
        return instance;
    }

    @Override
    protected List<Object> getWhereParams(List<Object> sqlParamsList,
            MySqlInsertStatement parseSqlStatement)
    {
        return null;
    }

    @Override
    protected String getWhere(MySqlInsertStatement parseSqlStatement)
    {
        return null;
    }

    @Override
    public TxcTable getPresentValue(List<Object> sqlParamsList,
            MySqlInsertStatement parseSqlStatement)
    {

        TxcTable txcTable = new TxcTable();
        txcTable.setTableName(parseSqlStatement.getTableName().getSimpleName());
        List<TxcLine> line = txcTable.getLine();

        List<SQLInsertStatement.ValuesClause> valuesList = parseSqlStatement
                .getValuesList();
        List<SQLExpr> columns = parseSqlStatement.getColumns();

        for (SQLInsertStatement.ValuesClause valuesClause : valuesList)
        {
            List<SQLExpr> values = valuesClause.getValues();
            TxcLine txcLine = new TxcLine();
            for (int i = 0; i < columns.size(); i++)
            {
                TxcField txcField = new TxcField();
                txcField.setName(SqlUtils.toSQLString(columns.get(i))
                        .replace("\'", "").replace("`", "").trim());
                if (CollectionUtils.isNotEmpty(sqlParamsList))
                {
                    txcField.setValue(sqlParamsList.get(i));
                } else
                {
                    txcField.setValue(SqlUtils.toSQLString(values.get(i)));
                }
                txcLine.getFields().add(txcField);
            }
            line.add(txcLine);
        }

        return txcTable;
    }

    @Override
    public TxcTable getOriginValue(List<Object> whereParamsList,
            MySqlInsertStatement parseSqlStatement, Connection connection)
    {
        return null;
    }

    @Override
    public SQLType getSqlType()
    {
        return SQLType.INSERT;
    }

    @Override
    protected String selectSql(MySqlInsertStatement parseSqlStatement,
            String primaryKeyName)
    {
        throw new RuntimeException("不支持的类型");
    }

    @Override
    protected String getTableName(MySqlInsertStatement parseSqlStatement)
    {
        return parseSqlStatement.getTableName().getSimpleName();
    }
}
