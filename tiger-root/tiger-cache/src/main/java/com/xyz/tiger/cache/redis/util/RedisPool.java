/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xyz.tiger.cache.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.xyz.tiger.cache.redis.config.RedisConfiguration;

@Component
public class RedisPool implements InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(RedisPool.class);
			
	@Autowired
	private RedisConfiguration redisConfiguration;

	private JedisPool jedisPool;

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public Jedis getJedis() throws Exception {
		if (jedisPool != null)
			return jedisPool.getResource();
		throw new Exception("获取redis连接池失败，jedisPool为空");
	}

	public void returnJedis(Jedis jedis) {
		if (jedis != null)
			jedis.close();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			JedisPoolConfig poolConfig = new JedisPoolConfig();
			poolConfig.setTestOnBorrow(redisConfiguration.getTestonborrow().equals("true") ? true : false);
			poolConfig.setTestWhileIdle(redisConfiguration.getTestwhileidle().equals("true") ? true : false);
			poolConfig.setMaxIdle(Integer.parseInt(redisConfiguration.getMaxidle()));
			poolConfig.setMaxTotal(Integer.parseInt(redisConfiguration.getMaxtotal()));
			poolConfig.setMinIdle(Integer.parseInt(redisConfiguration.getMinidle()));
			poolConfig.setMaxWaitMillis(Integer.parseInt(redisConfiguration.getMaxwaitmillis()));
			jedisPool = new JedisPool(poolConfig, redisConfiguration.getHost(), Integer.parseInt(redisConfiguration.getPort()),
					Integer.parseInt(redisConfiguration.getTimeout()));
		} catch (Exception e) {
			logger.error("初始化redis连接池失败！");
		}
	}
}
