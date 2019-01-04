/**
 * <p>文件名称: ExecutePaser.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tx.db.datasource.relational.txc.parser;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlDeleteStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlUpdateStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.cloud.tx.db.datasource.relational.txc.TableMetaInfo;
import com.cloud.tx.db.datasource.relational.txc.TableMetaUtils;
import com.cloud.tx.db.datasource.relational.txc.TxcStatement;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class ExecutePaser
{

    private static final Logger log = LoggerFactory.getLogger(ExecutePaser.class);

    public static SQLType parse(TxcStatement txcStatement)
    {
        long start = System.currentTimeMillis();
        SQLType sqlType = SQLType.SELECT;
        try
        {
            TxcRuntimeContext txcRuntimeContext = txcStatement
                    .getTxcDBConnection().getTxcRuntimeContext();

            // 解析sql
            String sql = txcStatement.getSql();
            SQLStatement sqlParseStatement = new MySqlStatementParser(sql)
                    .parseStatement();

            CommitInfo commitInfo = null;
            if (sqlParseStatement instanceof MySqlUpdateStatement)
            {
                commitInfo = UpdateParser.getInstance().parse(txcStatement);
                txcRuntimeContext.getInfo().add(commitInfo);
                sqlType = SQLType.UPDATE;
            } else if (sqlParseStatement instanceof MySqlInsertStatement)
            {
                commitInfo = InsertParser.getInstance().parse(txcStatement);
                txcRuntimeContext.getInfo().add(commitInfo);
                sqlType = SQLType.INSERT;
            } else if (sqlParseStatement instanceof MySqlDeleteStatement)
            {
                commitInfo = DeleteParser.getInstance().parse(txcStatement);
                txcRuntimeContext.getInfo().add(commitInfo);
                sqlType = SQLType.DELETE;
            }

            if (commitInfo != null && commitInfo.getSchemaName() == null)
            {
                String dbName = TableMetaUtils.getDbNameFromUrl(
                        txcStatement.getConnection().getMetaData().getURL());
                commitInfo.setSchemaName(dbName);
            }

        } catch (Exception e)
        {
            log.error("parse sql error", e);
        } finally
        {
            long cost = System.currentTimeMillis() - start;
            if (sqlType != SQLType.SELECT || cost > 50)
            {
                log.info("解析 sql:{}, cost:{}ms", txcStatement.getSql(),
                        cost);
            }
        }
        return sqlType;
    }

    public static void after(TxcStatement txcStatement, SQLType sqlType)
    {

        try
        {
            if (sqlType == SQLType.INSERT)
            {
                TxcRuntimeContext txcRuntimeContext = txcStatement
                        .getTxcDBConnection().getTxcRuntimeContext();
                List<CommitInfo> commitInfos = txcRuntimeContext.getInfo();
                if (commitInfos.size() == 0)
                {
                    return;
                }

                CommitInfo commitInfo = commitInfos.get(commitInfos.size() - 1);

                List<TxcLine> line = commitInfo.getPresentValue().getLine();
                if (line.size() > 1)
                {
                    log.error("不支持多条插入sql");
                    return;
                }

                TxcLine txcLine = line.get(0);

                setPrimaryValue(txcStatement, commitInfo, txcLine);

            }
        } catch (SQLException e)
        {
            log.error("execute parser after error", e);
        }
    }

    private static void setPrimaryValue(TxcStatement txcStatement,
            CommitInfo commitInfo, TxcLine txcLine) throws SQLException
    {
        TableMetaInfo tableMetaInfo = TableMetaUtils.getTableMetaInfo(
                txcStatement.getConnection(),
                commitInfo.getPresentValue().getTableName());
        String autoIncrementPrimaryKey = tableMetaInfo
                .getAutoIncrementPrimaryKey();
        if (StringUtils.isBlank(autoIncrementPrimaryKey))
        {
            String primaryKeyName = tableMetaInfo.getPrimaryKeyName();
            txcLine.setPrimaryKey(primaryKeyName);

            for (TxcField txcField : txcLine.getFields())
            {
                if (txcField.getName().equals(primaryKeyName))
                {
                    txcLine.setPrimaryValue(txcField.getValue());
                    return;
                }
            }

        } else
        {
            txcLine.setPrimaryKey(autoIncrementPrimaryKey);
            ResultSet resultSet = txcStatement.getConnection()
                    .prepareStatement("select last_insert_id() as id")
                    .executeQuery();
            while (resultSet.next())
            {
                txcLine.setPrimaryValue(resultSet.getObject("id"));
            }
        }
    }
}
