/**
 * <p>文件名称: TxcDBConnection.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tx.db.datasource.relational.txc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.cloud.tx.aop.bean.TxTransactionLocal;
import com.cloud.tx.datasource.service.DataSourceService;
import com.cloud.tx.db.datasource.relational.txc.parser.CommitInfo;
import com.cloud.tx.db.datasource.relational.txc.rollback.TxcRollbackService;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */

public class TxcDBConnection extends AbstractTxcConnection
{
    private static final Logger log = LoggerFactory.getLogger(TxcDBConnection.class);

    public TxcDBConnection(Connection connection,
            TxTransactionLocal txTransactionLocal,
            DataSourceService dataSourceService,
            TxcRollbackService txcRollbackService)
    {
        super(connection, txTransactionLocal, dataSourceService,
                txcRollbackService);
    }

    @Override
    public void transaction()
    {
        if (waitTask == null)
        {
            log.warn("waitTask is null");
            return;
        }

        // start 结束就是全部事务的结束表示,考虑start挂掉的情况
        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                log.warn("txc自动回滚->" + getGroupId());
                dataSourceService.schedule(getGroupId(), waitTask);
            }
        }, maxOutTime);

        log.info("waiting for TxManager notify, groupId {}, timeout {}",
                getGroupId(), maxOutTime);
        waitTask.awaitTask();

        timer.cancel();

        int rs = waitTask.getState();

        log.info("lcn txc transaction over, groupId {} and state is {}",
                getGroupId(), (rs == 1 ? "commit" : "rollback"));
        // 提交
        if (rs == 1)
        {
            // do nothing
        } else
        {
            try
            {
                rollbackConnection();
            } catch (Exception e)
            {
                log.error("rollback error", e);
            }
        }

        waitTask.remove();
    }

    @Override
    protected void closeConnection() throws SQLException
    {

        if (waitTask != null)
        {
            if (!waitTask.isRemove())
            {
                waitTask.remove();
            }
        }
    }

    @Override
    protected void rollbackConnection() throws SQLException
    {
        log.info("doTxcRollback kid:{},context:{}", waitTask.getKey(),
                JSON.toJSONString(txcRuntimeContext));
        List<CommitInfo> commitInfos = txcRuntimeContext.getInfo();

        // 逆序回滚
        for (int i = commitInfos.size() - 1; i >= 0; i--)
        {
            txcRollbackService.rollback(commitInfos.get(i));
        }
    }

}
