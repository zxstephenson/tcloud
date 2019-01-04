/**
 * <p>文件名称: LCNStartConnection.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tx.db.datasource.relational;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloud.tx.aop.bean.TxCompensateLocal;
import com.cloud.tx.aop.bean.TxTransactionLocal;
import com.cloud.tx.datasource.ICallClose;
import com.cloud.tx.datasource.ILCNResource;
import com.cloud.tx.framework.task.TaskGroup;
import com.cloud.tx.framework.task.TaskGroupManager;
import com.cloud.tx.framework.task.TaskState;
import com.cloud.tx.framework.task.TxTask;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class LCNStartConnection extends AbstractTransactionThread
        implements LCNConnection
{

    private static final Logger log = LoggerFactory.getLogger(LCNStartConnection.class);

    private Connection connection;

    private ICallClose<ILCNResource> subNowCount;

    private String groupId;

    private TxTask waitTask;

    private volatile int state = 1;

    private boolean isCompensate = false;

    private int startState = 0;

    private ThreadLocal<Boolean> isClose = new ThreadLocal<>();

    public LCNStartConnection(Connection connection,
            ICallClose<ILCNResource> subNowCount)
    {
        this.connection = connection;
        this.subNowCount = subNowCount;

        if (TxCompensateLocal.current() != null)
        {
            isCompensate = true;
            log.info("transaction is compensate-connection.");

            TxCompensateLocal txCompensateLocal = TxCompensateLocal.current();
            groupId = txCompensateLocal.getGroupId();

            TaskGroup taskGroup = TaskGroupManager.getInstance().createTask(groupId, txCompensateLocal.getType());
            waitTask = taskGroup.getCurrent();

            startState = txCompensateLocal.getStartState();
        } else
        {
            isCompensate = false;
            log.info("transaction is start-connection.");
            TxTransactionLocal txTransactionLocal = TxTransactionLocal.current();
            groupId = txTransactionLocal.getGroupId();

            TaskGroup taskGroup = TaskGroupManager.getInstance().createTask(groupId, txTransactionLocal.getType());
            waitTask = taskGroup.getCurrent();
        }

        log.info("lcn start connection init ok .");
    }

    @Override
    public TxTask getWaitTask()
    {
        return waitTask;
    }

    @Override
    public String getGroupId()
    {
        return groupId;
    }

    @Override
    public void commit() throws SQLException
    {

        log.info("commit label");

        state = 1;

        close();

        isClose.set(true);
    }

    @Override
    public void rollback() throws SQLException
    {

        log.info("rollback label");

        state = 0;

        close();

        isClose.set(true);
    }

    @Override
    public void close() throws SQLException
    {

        if (isClose.get() != null && isClose.get())
        {
            return;
        }

        if (connection == null || connection.isClosed())
        {
            return;
        }

        if (state == 0)
        {
            rollbackConnection();
            closeConnection();
            return;
        }

        if (state == 1)
        {
            TxTransactionLocal txTransactionLocal = TxTransactionLocal
                    .current();
            boolean isGroup = (txTransactionLocal != null)
                    ? txTransactionLocal.isHasIsGroup() : false;
            if (isGroup)
            {
                // 加入队列的连接，仅操作连接对象，不处理事务
                log.info("start connection hasGroup -> " + isGroup);

                return;
            }
            startRunnable();
        }
    }

    @Override
    protected void rollbackConnection() throws SQLException
    {
        connection.rollback();
    }

    public void transaction() throws SQLException
    {

        if (waitTask == null)
        {
            rollbackConnection();
            log.info(" start waitTask is null");
            return;
        }

        log.info(" start transaction is wait for TxManager notify, groupId : " + getGroupId());

        waitTask.awaitTask();

        int rs = waitTask.getState();

        try
        {
            if (rs == 1)
            {
                if (isCompensate)
                {
                    // 补偿时需要根据补偿数据决定提交还是回滚.
                    rs = startState;
                    if (rs == 1)
                    {
                        connection.commit();
                    } else
                    {
                        connection.rollback();
                    }
                } else
                {
                    throw new SQLException(this.getClass().getName() + "===> 默认提交异常");
//                    connection.commit();
                }
            } else
            {
                rollbackConnection();
            }
            log.info(" lcn start transaction over, res -> groupId:"
                    + getGroupId() + " and  state is "
                    + (rs == 1 ? "commit" : "rollback"));

        } catch (SQLException e)
        {

            e.printStackTrace();
            rollbackConnection();

            waitTask.setState(TaskState.connectionError.getCode());

            log.info(" lcn start transaction over,but connection is closed,  res -> groupId:" + getGroupId());
        }

        waitTask.remove();

    }

    protected void closeConnection() throws SQLException
    {

        subNowCount.close(this);

        connection.close();
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException
    {
        if (connection != null)
        {
            connection.setAutoCommit(false);
        }
    }

    /**
     * default
     */

    @Override
    public Statement createStatement() throws SQLException
    {
        return connection.createStatement();
    }

    @Override
    public PreparedStatement prepareStatement(String sql) throws SQLException
    {
        return connection.prepareStatement(sql);
    }

    @Override
    public CallableStatement prepareCall(String sql) throws SQLException
    {
        return connection.prepareCall(sql);
    }

    @Override
    public String nativeSQL(String sql) throws SQLException
    {
        return connection.nativeSQL(sql);
    }

    @Override
    public boolean getAutoCommit() throws SQLException
    {
        return connection.getAutoCommit();
    }

    @Override
    public boolean isClosed() throws SQLException
    {
        return connection.isClosed();
    }

    @Override
    public DatabaseMetaData getMetaData() throws SQLException
    {
        return connection.getMetaData();
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException
    {
        connection.setReadOnly(readOnly);
    }

    @Override
    public boolean isReadOnly() throws SQLException
    {
        return connection.isReadOnly();
    }

    @Override
    public void setCatalog(String catalog) throws SQLException
    {
        connection.setCatalog(catalog);
    }

    @Override
    public String getCatalog() throws SQLException
    {
        return connection.getCatalog();
    }

    @Override
    public void setTransactionIsolation(int level) throws SQLException
    {
        connection.setTransactionIsolation(level);
    }

    @Override
    public int getTransactionIsolation() throws SQLException
    {
        return connection.getTransactionIsolation();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException
    {
        return connection.getWarnings();
    }

    @Override
    public void clearWarnings() throws SQLException
    {
        connection.clearWarnings();
    }

    @Override
    public Statement createStatement(int resultSetType,
            int resultSetConcurrency) throws SQLException
    {
        return connection.createStatement(resultSetType, resultSetConcurrency);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType,
            int resultSetConcurrency) throws SQLException
    {
        return connection.prepareStatement(sql, resultSetType,
                resultSetConcurrency);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType,
            int resultSetConcurrency) throws SQLException
    {
        return connection.prepareCall(sql, resultSetType, resultSetConcurrency);
    }

    @Override
    public Map<String, Class<?>> getTypeMap() throws SQLException
    {
        return connection.getTypeMap();
    }

    @Override
    public void setTypeMap(Map<String, Class<?>> map) throws SQLException
    {
        connection.setTypeMap(map);
    }

    @Override
    public void setHoldability(int holdability) throws SQLException
    {
        connection.setHoldability(holdability);
    }

    @Override
    public int getHoldability() throws SQLException
    {
        return connection.getHoldability();
    }

    @Override
    public Savepoint setSavepoint() throws SQLException
    {
        return connection.setSavepoint();
    }

    @Override
    public Savepoint setSavepoint(String name) throws SQLException
    {
        return connection.setSavepoint(name);
    }

    @Override
    public void rollback(Savepoint savepoint) throws SQLException
    {
        connection.rollback(savepoint);
    }

    @Override
    public void releaseSavepoint(Savepoint savepoint) throws SQLException
    {
        connection.releaseSavepoint(savepoint);
    }

    @Override
    public Statement createStatement(int resultSetType,
            int resultSetConcurrency, int resultSetHoldability)
            throws SQLException
    {
        return connection.createStatement(resultSetType, resultSetConcurrency,
                resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int resultSetType,
            int resultSetConcurrency, int resultSetHoldability)
            throws SQLException
    {
        return connection.prepareStatement(sql, resultSetType,
                resultSetConcurrency, resultSetHoldability);
    }

    @Override
    public CallableStatement prepareCall(String sql, int resultSetType,
            int resultSetConcurrency, int resultSetHoldability)
            throws SQLException
    {
        return connection.prepareCall(sql, resultSetType, resultSetConcurrency,
                resultSetHoldability);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
            throws SQLException
    {
        return connection.prepareStatement(sql, autoGeneratedKeys);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, int[] columnIndexes)
            throws SQLException
    {
        return connection.prepareStatement(sql, columnIndexes);
    }

    @Override
    public PreparedStatement prepareStatement(String sql, String[] columnNames)
            throws SQLException
    {
        return connection.prepareStatement(sql, columnNames);
    }

    @Override
    public Clob createClob() throws SQLException
    {
        return connection.createClob();
    }

    @Override
    public Blob createBlob() throws SQLException
    {
        return connection.createBlob();
    }

    @Override
    public NClob createNClob() throws SQLException
    {
        return connection.createNClob();
    }

    @Override
    public SQLXML createSQLXML() throws SQLException
    {
        return connection.createSQLXML();
    }

    @Override
    public boolean isValid(int timeout) throws SQLException
    {
        return connection.isValid(timeout);
    }

    @Override
    public void setClientInfo(String name, String value)
            throws SQLClientInfoException
    {
        connection.setClientInfo(name, value);
    }

    @Override
    public void setClientInfo(Properties properties)
            throws SQLClientInfoException
    {
        connection.setClientInfo(properties);
    }

    @Override
    public String getClientInfo(String name) throws SQLException
    {
        return connection.getClientInfo(name);
    }

    @Override
    public Properties getClientInfo() throws SQLException
    {
        return connection.getClientInfo();
    }

    @Override
    public Array createArrayOf(String typeName, Object[] elements)
            throws SQLException
    {
        return connection.createArrayOf(typeName, elements);
    }

    @Override
    public Struct createStruct(String typeName, Object[] attributes)
            throws SQLException
    {
        return connection.createStruct(typeName, attributes);
    }

    @Override
    public void setSchema(String schema) throws SQLException
    {
        connection.setSchema(schema);
    }

    @Override
    public String getSchema() throws SQLException
    {
        return connection.getSchema();
    }

    @Override
    public void abort(Executor executor) throws SQLException
    {
        connection.abort(executor);
    }

    @Override
    public void setNetworkTimeout(Executor executor, int milliseconds)
            throws SQLException
    {
        connection.setNetworkTimeout(executor, milliseconds);
    }

    @Override
    public int getNetworkTimeout() throws SQLException
    {
        return connection.getNetworkTimeout();
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException
    {
        return connection.unwrap(iface);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException
    {
        return connection.isWrapperFor(iface);
    }
}
