/**
 * <p>文件名称: LCNTransactionDataSource.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tx.db.datasource.relational;

import java.sql.Connection;
import java.sql.SQLException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cloud.tx.aop.bean.TxCompensateLocal;
import com.cloud.tx.aop.bean.TxTransactionLocal;
import com.cloud.tx.datasource.AbstractResourceProxy;
import com.cloud.tx.datasource.ILCNConnection;
import com.cloud.tx.db.datasource.relational.txc.TxcDBConnection;
import com.cloud.tx.db.datasource.relational.txc.rollback.TxcRollbackService;

/**
 * 关系型数据库代理连接池对象 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */

@Component
public class LCNTransactionDataSource
        extends AbstractResourceProxy<Connection, LCNDBConnection>
        implements ILCNConnection
{

    private static final Logger log = LoggerFactory.getLogger(LCNTransactionDataSource.class);

    @Autowired
    TxcRollbackService txcRollbackService;

    @Override
    protected Connection createLcnConnection(Connection connection,
            TxTransactionLocal txTransactionLocal)
    {
        nowCount++;
        if (txTransactionLocal.isHasStart())
        {
            LCNStartConnection lcnStartConnection = new LCNStartConnection(connection, subNowCount);
            log.debug("get new start connection - > " + txTransactionLocal.getGroupId());
            pools.put(txTransactionLocal.getGroupId(), lcnStartConnection);
            txTransactionLocal.setHasConnection(true);
            return lcnStartConnection;
        } else
        {
            LCNDBConnection lcn = new LCNDBConnection(connection, dataSourceService, subNowCount);
            log.debug("get new connection ->" + txTransactionLocal.getGroupId());
            pools.put(txTransactionLocal.getGroupId(), lcn);
            txTransactionLocal.setHasConnection(true);
            return lcn;
        }
    }

    @Override
    protected Connection createTxcConnection(Connection connection,
            TxTransactionLocal txTransactionLocal)
    {
        Connection txc = new TxcDBConnection(connection, txTransactionLocal,
                dataSourceService, txcRollbackService);
        log.info(
                "get new txc connection ->" + txTransactionLocal.getGroupId());
        return txc;
    }

    @Override
    protected void initDbType()
    {
        TxTransactionLocal txTransactionLocal = TxTransactionLocal.current();
        if (txTransactionLocal != null)
        {
            // 设置db类型
            txTransactionLocal.setType("datasource");
        }
        TxCompensateLocal txCompensateLocal = TxCompensateLocal.current();
        if (txCompensateLocal != null)
        {
            // 设置db类型
            txCompensateLocal.setType("datasource");
        }
    }

    @Override
    public Connection getConnection(ProceedingJoinPoint point) throws Throwable
    {
        // 说明有db操作.
        hasTransaction = true;

        initDbType();

        Connection connection = (Connection) loadConnection();
        if (connection == null)
        {
            connection = initLCNConnection((Connection) point.proceed());
            if (connection == null)
            {
                throw new SQLException("connection was overload");
            }
            return connection;
        } else
        {
            return connection;
        }
    }
}
