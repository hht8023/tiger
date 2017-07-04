package com.xyz.tiger.cache.redis.util.callback;

import redis.clients.jedis.Transaction;

public interface TransactionCallBack {

	void execute(Transaction t);

}
