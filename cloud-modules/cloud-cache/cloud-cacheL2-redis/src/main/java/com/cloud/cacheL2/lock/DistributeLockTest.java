/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author    zhangxin4
 * @version   3.1.0 2018年12月5日
 */

package com.cloud.cacheL2.lock;

public class DistributeLockTest{
    public static void main(String[] args)
    {
        RedisLock redisLock = new RedisLock("redisLockKey");
        
        try
        {
            if(redisLock.lock()){
                //需要加锁处理的业务逻辑
            }
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }finally{
            redisLock.unlock();
        }
    }
}