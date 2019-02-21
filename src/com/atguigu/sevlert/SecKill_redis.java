package com.atguigu.sevlert;

import java.util.List;

import com.atguigu.utils.JedisPoolUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

public class SecKill_redis {
	
	public static boolean doSeckill(String uid,String prodid) {
		//synchronized (SecKill_redis.class) {
			
			//生成相关数据的key
			String productKey ="sk:"+prodid+":product";
			String userKey ="sk:"+prodid+":user";
			
			//使用连接池获取连接
			JedisPool jedisPool = JedisPoolUtil.getJedisPoolInstance();
			
			Jedis jedis = jedisPool.getResource();
			
			//判断用户是否已经秒杀成功
			if(jedis.sismember(userKey, uid)) {
				
				System.err.println(uid+"已经秒杀过!");
				
				jedis.close();
				
				return false;
			}	
			
			//判断库存的合法性
			jedis.watch(productKey);
			String product = jedis.get(productKey);
			
			if(product == null) {
				
				System.err.println(prodid+"尚未被商家上架!");
				
				jedis.close();
				
				return false;
				
			}else {
				
				int store = Integer.parseInt(product);
				
				if(store == 0) {
					
					System.err.println(prodid+"已经没有库存了!");
					
					jedis.close();
					
					return false;
				}
				
			}
			//具体的秒杀逻辑
			Transaction transaction = jedis.multi();
			
			transaction.decr(productKey);
			
			transaction.sadd(userKey, uid);
			
			List<Object> result = transaction.exec();
			
			if(result==null||result.size() < 2) {
				
				System.err.println("秒杀失败!");
				
				jedis.close();
				
				return false;
				
			}
			
			jedis.close();
			
			System.out.println(uid+"===>秒杀成功!");
			
			return true;
		}
	//	}

}
