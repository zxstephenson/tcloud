/**
 * <p>文件名称: AbstractRollback.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tx.db.datasource.relational.txc.rollback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloud.tx.db.datasource.relational.txc.parser.CommitInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public abstract class AbstractRollback
{
    private static final Logger log = LoggerFactory.getLogger(AbstractRollback.class);

    public void rollback(CommitInfo commitInfo, Connection connection)
            throws SQLException
    {

        // check
        boolean flag = canRollback(commitInfo, connection);

        // rollback
        if (flag)
        {
            log.info("rollback for sql:{}", commitInfo.getSql());
            List<PreparedStatement> preparedStatements = assembleRollbackSql(
                    commitInfo, connection);

            for (PreparedStatement preparedStatement : preparedStatements)
            {
                preparedStatement.execute();
            }
            log.info("rollback sql success");
        }
    }

    protected abstract List<PreparedStatement> assembleRollbackSql(
            CommitInfo commitInfo, Connection connection) throws SQLException;

    protected abstract boolean canRollback(CommitInfo commitInfo,
            Connection connection) throws SQLException;
}
