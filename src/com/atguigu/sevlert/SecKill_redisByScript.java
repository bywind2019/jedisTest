package com.atguigu.sevlert;

import com.atguigu.utils.JedisPoolUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class SecKill_redisByScript {
	
	static String secKillScript =
			"local userid=KEYS[1];\r\n" + 
			"local prodid=KEYS[2];\r\n" + 
			"local productKey='sk:'..prodid..\":product\";\r\n" + 
			"local usersKey='sk:'..prodid..\":user\";\r\n" + 
			"local userExists=redis.call(\"sismember\",usersKey,userid);\r\n" + 
			"if tonumber(userExists)==1 then \r\n" + 
			"   return 2;\r\n" + 
			"end\r\n" + 
			"local num= redis.call(\"get\" ,productKey);\r\n" + 
			"if tonumber(num)<=0 then \r\n" + 
			"   return 0;\r\n" + 
			"else \r\n" + 
			"   redis.call(\"decr\",productKey);\r\n" + 
			"   redis.call(\"sadd\",usersKey,userid);\r\n" + 
			"end\r\n" + 
			"return 1" ;
	
	public static boolean doSecKill(String uid,String prodid) {
		
		JedisPool jedisPool = JedisPoolUtil.getJedisPoolInstance();
		
		Jedis jedis = jedisPool.getResource();
		
		String sha1 = jedis.scriptLoad(secKillScript);
		
		Object result = jedis.evalsha(sha1, 2, uid,prodid);
		
		String reString = String.valueOf(result);
		
		if("0".equals(reString)) {
			System.err.println("已抢空!!");	
		}else if("1".equals(reString)){
			System.out.println("抢购成功!!!!");
		}else if("2".equals(reString)) {
			System.err.println("该用户已抢过!!");
		}else {
			System.err.println("抢购异常!!");
		}
		jedis.close();
		
		return true;
	}

}
