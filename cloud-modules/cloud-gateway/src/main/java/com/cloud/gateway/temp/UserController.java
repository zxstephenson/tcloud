package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lock.RedisLock;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
public class UserController {

	@Autowired
	private JedisPool jedisPool;
	
	@RequestMapping("/test")
	public String test(){
		MyThread myThread = new MyThread();
		
		for(int i=0; i<5; i++)
		{
			Thread thread = new Thread(myThread);
			thread.start();
		}
		
		return "aaaa";
	}
	
	@RequestMapping("/reduce")
	public String reduce(){
		RedisLock lock = new RedisLock("spring:metadata", 10000, 20000);
		try {
			if(lock.lock()){
				Jedis jedis = jedisPool.getResource();
				String age = jedis.get("age");
				if(Integer.valueOf(age)>0){
					jedis.decrBy("age", 1);					
				}
				jedis.close();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{
			lock.unlock();
		}
		
		return "success";
	}
	
}
