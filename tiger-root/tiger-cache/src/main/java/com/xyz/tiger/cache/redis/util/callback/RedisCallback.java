package com.xyz.tiger.cache.redis.util.callback;

import redis.clients.jedis.Jedis;

public interface RedisCallback<T> {
	
	T doInRedis(Jedis jedis) throws Exception;
	
}
