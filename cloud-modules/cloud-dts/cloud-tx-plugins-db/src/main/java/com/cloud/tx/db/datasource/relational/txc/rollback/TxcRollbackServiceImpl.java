/**
 * <p>文件名称: TxcRollbackServiceImpl.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tx.db.datasource.relational.txc.rollback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloud.tx.db.datasource.relational.txc.parser.CommitInfo;
import com.cloud.tx.db.datasource.relational.txc.parser.SQLType;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
@Component
public class TxcRollbackServiceImpl implements TxcRollbackService
{

    private static final Logger log = LoggerFactory.getLogger(TxcRollbackServiceImpl.class);

    @Autowired
    private TxcRollbackDataSource rollbackDataSource;

    @Override
    public void rollback(CommitInfo commitInfo)
    {
        // 每次需要新获取一个连接
        Connection connection = null;
        try
        {
            connection = rollbackDataSource
                    .getConnectionByDbName(commitInfo.getSchemaName());
            if (commitInfo.getSqlType() == SQLType.UPDATE)
            {
                UpdateRollback.getInstance().rollback(commitInfo, connection);
            }

            if (commitInfo.getSqlType() == SQLType.INSERT)
            {
                InsertRollback.getInstance().rollback(commitInfo, connection);
            }

            if (commitInfo.getSqlType() == SQLType.DELETE)
            {
                DeleteRollback.getInstance().rollback(commitInfo, connection);
            }
        } catch (Exception e)
        {
            log.error("rollback error, sql:{}", commitInfo.getSql(), e);
        } finally
        {
            try
            {
                if (connection != null)
                {
                    connection.close();
                }
            } catch (SQLException e)
            {
                log.error("close error", e);
            }
        }

    }
}
