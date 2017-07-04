package com.xyz.tiger.cache.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import com.xyz.tiger.cache.redis.util.RedisPool;
import com.xyz.tiger.cache.redis.util.callback.RedisCallback;
import com.xyz.tiger.cache.redis.util.callback.TransactionCallBack;

@Component
public class RedisDaoTemplate {

	@Autowired
	private RedisPool redisPool;


	public <T> T execute(RedisCallback<T> rc) throws Exception {
		Jedis jedis = null;
		try {
			// 用默认的db 0;
			jedis = redisPool.getJedis();
			return rc.doInRedis(jedis);
		}
		finally {
			redisPool.returnJedis(jedis);
		}
	}
	
	public void execute(TransactionCallBack rc) throws Exception {
		Jedis jedis = null;
		Transaction transaction;
		try {
			jedis = redisPool.getJedis();
			transaction = jedis.multi();
			rc.execute(transaction);
			transaction.exec();
		}
		finally {
			redisPool.returnJedis(jedis);
		}
	}
}
