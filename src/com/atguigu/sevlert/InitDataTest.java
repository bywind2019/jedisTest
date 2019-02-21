package com.atguigu.sevlert;


import org.junit.Test;

import redis.clients.jedis.Jedis;

public class InitDataTest {
	
	//初始化库存的方法
	@Test
	public void test() {
		
		Jedis jedis = new Jedis("192.168.179.11",6379);
		
		System.out.println(jedis.ping());
		
		String productKey ="sk:"+1001+":product";
		
		String userKey = "sk:"+1001+":user";
		
		jedis.set(productKey, "8");
		
		jedis.del(userKey);
		
		String string = jedis.get(productKey);
		
		System.out.println(string);
		
		jedis.close();
	}
}
