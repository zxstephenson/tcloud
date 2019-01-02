/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年12月5日
 */

package com.cloud.cacheL2.lock;

import com.cloud.cacheL2.exception.RedisDBException;

public class DistributeLockTest{
    public static void main(String[] args)
    {
        DTSLock redisLock = new DTSLock("redisLockKey");
        
        try
        {
            if(redisLock.lock()){
                //需要加锁处理的业务逻辑
            }
        } catch (RedisDBException e)
        {
            e.printStackTrace();
        }finally{
            try
            {
                redisLock.unlock();
            } catch (RedisDBException e)
            {
                e.printStackTrace();
            }
        }
    }
}