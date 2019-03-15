/**
 * <p>文件名称: LCNDBConnection.java</p>
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
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloud.tx.aop.bean.TxTransactionLocal;
import com.cloud.tx.datasource.ICallClose;
import com.cloud.tx.datasource.ILCNResource;
import com.cloud.tx.datasource.service.DataSourceService;
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

public class LCNDBConnection extends AbstractTransactionThread
        implements LCNConnection
{

    private static final Logger log = LoggerFactory.getLogger(LCNDBConnection.class);

    private ThreadLocal<Boolean> isClose = new ThreadLocal<>();

    private volatile int state = 1;

    private Connection connection;

    private DataSourceService dataSourceService;

    private ICallClose<ILCNResource> runnable;

    private int maxOutTime;

    private String groupId;

    private TxTask waitTask;

    private boolean readOnly = false;

    public LCNDBConnection(Connection connection,
            DataSourceService dataSourceService,
            ICallClose<ILCNResource> runnable)
    {
        log.debug("init lcn connection ! ");
        this.connection = connection;
        this.runnable = runnable;
        this.dataSourceService = dataSourceService;
        TxTransactionLocal transactionLocal = TxTransactionLocal.current();
        groupId = transactionLocal.getGroupId();
        maxOutTime = transactionLocal.getMaxTimeOut();

        TaskGroup taskGroup = TaskGroupManager.getInstance().createTask(
                transactionLocal.getKid(), transactionLocal.getType());
        waitTask = taskGroup.getCurrent();
    }

    @Override
    public void commit() throws SQLException
    {

        log.debug("commit label");

        state = 1;

        close();

        isClose.set(true);

    }

    @Override
    public void rollback() throws SQLException
    {

        log.debug("rollback label");

        state = 0;

        close();

        isClose.set(true);
    }

    protected void closeConnection() throws SQLException
    {
        runnable.close(this);
        connection.close();
        log.debug("lcnConnection closed groupId:" + groupId);
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

        if (readOnly)
        {
            closeConnection();
            log.debug("now transaction is readOnly , groupId:" + groupId);
            return;
        }

        log.debug("now transaction state is " + state
                + ", (1:commit,0:rollback) groupId:" + groupId);

        if (state == 0)
        {
            rollbackConnection();
            closeConnection();

            log.debug("rollback transaction ,groupId:" + groupId);
        }
        if (state == 1)
        {
            TxTransactionLocal txTransactionLocal = TxTransactionLocal.current();
            boolean hasGroup = (txTransactionLocal != null) ? txTransactionLocal.isHasIsGroup() : false;
            if (hasGroup)
            {
                // 加入队列的连接，仅操作连接对象，不处理事务
                log.debug("connection hasGroup -> " + hasGroup);
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
            log.debug(" waitTask is null");
            return;
        }
        // start 结束就是全部事务的结束表示,考虑start挂掉的情况
        Timer timer = new Timer();
        log.debug(" maxOutTime : " + maxOutTime);
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                log.debug("auto execute ,groupId:" + getGroupId());
                dataSourceService.schedule(getGroupId(), waitTask);
            }
        }, maxOutTime);

        log.debug("transaction is wait for TxManager notify, groupId {}", getGroupId());

        waitTask.awaitTask();

        timer.cancel();

        int rs = waitTask.getState();

        try
        {
            if (rs == 1)
            {
                connection.commit();
            } else
            {
                rollbackConnection();
            }

            log.info("lcn transaction over, res -> groupId:" + getGroupId()
                    + " and  state is " + (rs == 1 ? "commit" : "rollback"));

        } catch (SQLException e)
        {
            log.info("lcn transaction over,but connection is closed, res -> groupId:" + getGroupId());

            waitTask.setState(TaskState.connectionError.getCode());
        }

        waitTask.remove();

    }

    public String getGroupId()
    {
        return groupId;
    }

    public TxTask getWaitTask()
    {
        return waitTask;
    }

    @Override
    public void setAutoCommit(boolean autoCommit) throws SQLException
    {
        if (connection != null)
        {
            connection.setAutoCommit(false);
        }
    }

    @Override
    public void setReadOnly(boolean readOnly) throws SQLException
    {

        if (readOnly)
        {
            this.readOnly = readOnly;
            log.debug("setReadOnly - >" + readOnly);
            connection.setReadOnly(readOnly);

            TxTransactionLocal txTransactionLocal = TxTransactionLocal
                    .current();
            txTransactionLocal.setReadOnly(readOnly);
        }
    }

    /***** default *******/

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