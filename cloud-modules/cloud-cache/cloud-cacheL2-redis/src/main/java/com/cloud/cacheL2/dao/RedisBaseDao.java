/**
 * <p>文件名称: RedisBaseDao.java</p>
 * <p>项目描述: JROS 捷容平台 V3.1.0</p>
 * <p>公       司: 金证财富南京科技有限公司</p>
 * <p>版权所有: 版权所有(C)1998-2018</p>
 */
package com.cloud.cacheL2.dao;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 数据层基类 业务数据库未作操作时，继承此类后，利用公共类API操作省掉部分工作量
 * 
 * @author yyh
 * @version 3.1.0 2018年3月7日
 * @see
 * @since 3.1.0
 */
public class RedisBaseDao
{
    @Autowired
    protected JedisClusterDao daoJedis;

    public JedisClusterDao getDaoJedis()
    {
        return this.daoJedis;
    }
}
