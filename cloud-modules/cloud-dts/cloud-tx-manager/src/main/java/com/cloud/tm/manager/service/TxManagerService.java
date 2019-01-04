/**
 * <p>文件名称: TxManagerService.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tm.manager.service;

import com.cloud.tm.netty.model.TxGroup;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */

public interface TxManagerService
{

    /**
     * 创建事物组
     *
     * @param groupId
     *            补偿事务组id
     */
    TxGroup createTransactionGroup(String groupId);

    /**
     * 添加事务组子对象
     *
     * @return
     */

    TxGroup addTransactionGroup(String groupId, String taskId, int isGroup,
            String channelAddress, String methodStr);

    /**
     * 关闭事务组
     * 
     * @param groupId
     *            事务组id
     * @param state
     *            事务状态
     * @return 0 事务存在补偿 1 事务正常 -1 事务强制回滚
     */
    int closeTransactionGroup(String groupId, int state);

    void dealTxGroup(TxGroup txGroup, boolean hasOk);

    /**
     * 删除事务组
     * 
     * @param txGroup
     *            事务组
     */
    void deleteTxGroup(TxGroup txGroup);

    /**
     * 获取事务组信息
     * 
     * @param groupId
     *            事务组id
     * @return 事务组
     */
    TxGroup getTxGroup(String groupId);

    /**
     * 获取事务组的key
     * 
     * @param groupId
     *            事务组id
     * @return key
     */
    String getTxGroupKey(String groupId);

    /**
     * 检查事务组数据
     * 
     * @param groupId
     *            事务组id
     * @param taskId
     *            任务id
     * @return 本次请求的是否提交 1提交 0回滚
     */
    int cleanNotifyTransaction(String groupId, String taskId);

    /**
     * 设置强制回滚事务
     * 
     * @param groupId
     *            事务组id
     * @return true 成功 false 失败
     */
    boolean rollbackTransactionGroup(String groupId);
}
