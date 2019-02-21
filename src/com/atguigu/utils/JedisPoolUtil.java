package com.atguigu.utils;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolUtil {
	private static volatile JedisPool jedisPool = null;
 

	private JedisPoolUtil() {
	}

	public static JedisPool getJedisPoolInstance() {
		if (null == jedisPool) {
			synchronized (JedisPoolUtil.class) {
				if (null == jedisPool) {
					
					JedisPoolConfig poolConfig = new JedisPoolConfig();
					poolConfig.setMaxTotal(200);//最大连接数
					poolConfig.setMaxIdle(50);//最大空闲连接数
					//获取连接时的最大等待毫秒数,如果超时就抛异常,默认-1
					poolConfig.setMaxWaitMillis(100*1000);
					//连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
					poolConfig.setBlockWhenExhausted(true);
					//在获取连接的时候检查有效性, 默认false
					poolConfig.setTestOnBorrow(true);
				 
					jedisPool = new JedisPool(poolConfig, "192.168.179.11", 6379, 100000 );
			 
				}
			}
		}
		return jedisPool;
	}


}
