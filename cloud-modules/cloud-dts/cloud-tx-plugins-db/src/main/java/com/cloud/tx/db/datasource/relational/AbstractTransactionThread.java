/**
 * <p>文件名称: AbstractTransactionThread.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tx.db.datasource.relational;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloud.tx.aop.bean.TxTransactionLocal;
import com.cloud.tx.framework.thread.HookRunnable;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public abstract class AbstractTransactionThread
{

    private volatile boolean hasStartTransaction = false;

    private static final Logger log = LoggerFactory.getLogger(AbstractTransactionThread.class);

    protected void startRunnable()
    {
        if (hasStartTransaction)
        {
            log.debug("start connection is wait ! ");
            return;
        }
        hasStartTransaction = true;
        Runnable runnable = new HookRunnable()
        {
            @Override
            public void run0()
            {
                TxTransactionLocal.setCurrent(null);
                try
                {
                    transaction();
                } catch (Exception e)
                {
                    log.error(e.getMessage());
                    try
                    {
                        rollbackConnection();
                    } catch (SQLException e1)
                    {
                        log.error(e1.getMessage());
                    }
                } finally
                {
                    try
                    {
                        closeConnection();
                    } catch (SQLException e)
                    {
                        log.error(e.getMessage());
                    }
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }

    protected abstract void transaction() throws SQLException;

    protected abstract void closeConnection() throws SQLException;

    protected abstract void rollbackConnection() throws SQLException;
}
