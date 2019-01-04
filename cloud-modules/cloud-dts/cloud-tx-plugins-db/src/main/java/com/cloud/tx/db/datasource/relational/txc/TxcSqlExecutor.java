/**
 * <p>文件名称: TxcSqlExecutor.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tx.db.datasource.relational.txc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.cloud.tx.db.datasource.relational.txc.parser.ResultConvertUtils;
import com.cloud.tx.db.datasource.relational.txc.parser.SQLType;
import com.cloud.tx.db.datasource.relational.txc.parser.TxcLine;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class TxcSqlExecutor
{

    public static List<TxcLine> executeQuery(String sql, Connection connection)
            throws SQLException
    {
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();
        return ResultConvertUtils.convertWithPrimary(resultSet, null,
                SQLType.SELECT);
    }

    public static List<TxcLine> executeQuery(
            PreparedStatement preparedStatement) throws SQLException
    {
        ResultSet resultSet = preparedStatement.executeQuery();
        return ResultConvertUtils.convertWithPrimary(resultSet, null,
                SQLType.SELECT);
    }

    public static void execute(String sql, Connection connection)
            throws SQLException
    {
        connection.prepareStatement(sql).execute();
    }
}
