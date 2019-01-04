/**
 * <p>文件名称: TxcRollbackService.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tx.db.datasource.relational.txc.rollback;

import com.cloud.tx.db.datasource.relational.txc.parser.CommitInfo;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public interface TxcRollbackService
{

    /**
     * 执行回滚
     * 
     * @param commitInfo
     */
    void rollback(CommitInfo commitInfo);
}
