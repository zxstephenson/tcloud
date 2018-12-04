package com.controller;

import com.lock.RedisLock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class MyThread implements Runnable {

	@Override
	public void run() {
		JedisPool jedisPool = ContextHolder.getApplicationContext().getBean(JedisPool.class);
		
		RedisLock lock = new RedisLock("spring:metadata", 10000, 20000);
		try {
			if(lock.lock()) 
			{
				Jedis jedis = jedisPool.getResource();
				//需要加锁的代码
				for(int i=0; i<10; i++){
					if(null != jedisPool)
					{
						jedis.set("meta_" 
					+ Thread.currentThread().getName() +"_" + i, String.valueOf(i));						
					}
				}
				jedis.close();
			}
		
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}finally {
		    //为了让分布式锁的算法更稳键些，持有锁的客户端在解锁之前应该再检查一次自己的锁是否已经超时，再去做DEL操作，因为可能客户端因为某个耗时的操作而挂起，
			//操作完的时候锁因为超时已经被别人获得，这时就不必解锁了。 ————这里没有做
		    lock.unlock();
		}
	}

}
