/**
 * <p>文件名称: TxcRuntimeContext.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.tx.db.datasource.relational.txc.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 〈一句话功能简述〉 〈功能详细描述〉
 * 
 * @author zhangxin4
 * @version 3.1.0 2018年12月11日
 */
public class TxcRuntimeContext
{
    private static final Logger log = LoggerFactory.getLogger(TxcRuntimeContext.class);

    /**
     * 事务组Id 对应于txc的 xid
     */
    public String groupId;
    /**
     * 分支事务Id lcn里叫 kid
     */
    public String branchId;
    /**
     * 提交信息
     */
    private List<CommitInfo> info = new ArrayList();

    public int status;
    /**
     * 分支所在IP
     */
    public String server;

    public List<CommitInfo> getInfo()
    {
        return info;
    }

    public void setInfo(List<CommitInfo> info)
    {
        this.info = info;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getBranchId()
    {
        return branchId;
    }

    public void setBranchId(String branchId)
    {
        this.branchId = branchId;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getServer()
    {
        return server;
    }

    public void setServer(String server)
    {
        this.server = server;
    }
}