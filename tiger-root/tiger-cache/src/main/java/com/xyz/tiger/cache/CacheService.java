/**
 * Copyright © 1998-2017, enn Inc. All Rights Reserved.
 */
package com.xyz.tiger.cache;

/**
 * 缓存服务
 * 
 * @author Hanht
 * @date 2017年5月24日
 */
public interface CacheService<K, V> {

	void set(K key, V value);
	
	V get(K key);
}
