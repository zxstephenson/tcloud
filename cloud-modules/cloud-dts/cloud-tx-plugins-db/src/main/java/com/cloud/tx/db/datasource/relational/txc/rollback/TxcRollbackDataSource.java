/**
 * <p>文件名称: TxcRollbackDataSource.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tx.db.datasource.relational.txc.rollback;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
@Component
public class TxcRollbackDataSource
{

    private Map<String, DataSource> dataSourceMap = new HashMap<>();

    public void setDataSourceMap(Map<String, DataSource> dataSourceMap)
    {
        this.dataSourceMap = dataSourceMap;
    }

    public Connection getConnectionByDbName(String dbName) throws SQLException
    {

        DataSource dataSource = dataSourceMap.get(dbName);
        if (dataSource == null)
        {
            throw new SQLException("datasource do not exist, name: " + dbName);
        }

        return dataSourceMap.get(dbName).getConnection();
    }
}
