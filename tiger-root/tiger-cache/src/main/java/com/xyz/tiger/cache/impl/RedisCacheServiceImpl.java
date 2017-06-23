/**
* Copyright © 1998-2017, enn Inc. All Rights Reserved.
*/
package com.xyz.tiger.cache.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.xyz.tiger.cache.CacheService;

@Service
public class RedisCacheServiceImpl<K,V> implements CacheService<K, V> {

	@Autowired
    RedisTemplate<K, V> redisTemplate;
    
    public void set(K key, V value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public V get(K key){
    	return redisTemplate.opsForValue().get(key);
    }
}
