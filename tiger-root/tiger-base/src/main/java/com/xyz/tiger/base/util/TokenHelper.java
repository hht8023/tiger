package com.xyz.tiger.base.util;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.xyz.tiger.base.common.constant.GlobalConstant;
import com.xyz.tiger.base.common.exception.TigerException;
import com.xyz.tiger.base.common.persistence.ApiUserToken;
import com.xyz.tiger.base.configuration.ResultCodeConfiguration;
import com.xyz.tiger.cache.redis.util.RedisDaoTemplate;
import com.xyz.tiger.cache.redis.util.callback.RedisCallback;

/**
 * @Description: 生成手机端token工具类
 * @author: Hanht
 * @since: jdk1.8
 * @time: 2017年6月29日
 */
@Component
public class TokenHelper {

	@Autowired
	private RedisDaoTemplate redisDaoTemplate;
	@Autowired
	private ResultCodeConfiguration resultCodeConfiguration;

	private final int TIME_TO_LIVE = 30 * 24 * 60 * 60;// 30天

	public ApiUserToken createToken(Object userID, String appkey) throws Exception {
		return createToken(userID, appkey, TIME_TO_LIVE);
	}

	public ApiUserToken createToken(Object userID, String appkey, long days) throws Exception {
		ApiUserToken apiUserToken = new ApiUserToken();
		apiUserToken.setAppkey(appkey);
		apiUserToken.setToken(ApiTools.getRandomUUID());
		apiUserToken.setCreateTime(new Date());
		apiUserToken.setLastVisitTime(apiUserToken.getCreateTime());
		apiUserToken.setUserId(userID);
		apiUserToken.setDelayTime(days / 24 * 60 * 60 * 1000);
		// 保存token 到缓存
		saveTokeToRedis(apiUserToken, userID);
		return apiUserToken;
	}

	/**
	 * 保存token 到redis
	 * 
	 * @param apiUserToken
	 * @param userID
	 * @throws Exception
	 */
	private void saveTokeToRedis(ApiUserToken apiUserToken, Object userID) throws Exception {
		redisDaoTemplate.execute(new RedisCallback<String>() {
			public String doInRedis(Jedis jedis) throws Exception {
				String key = GlobalConstant.USER_TOKEN + userID;
				jedis.setex(key, TIME_TO_LIVE, JSON.toJSONString(apiUserToken));
				return null;
			}
		});
	}

	/**
	 * 获取登陆token
	 * 
	 * @param userID
	 * @return ApiUserToken
	 * @throws Exception
	 */
	public ApiUserToken getUserToken(Long userID) throws Exception {
		return redisDaoTemplate.execute(new RedisCallback<ApiUserToken>() {
			public ApiUserToken doInRedis(Jedis jedis) throws Exception {
				String key = GlobalConstant.USER_TOKEN + userID;
				String userTokenJson = jedis.get(key);
				if (!StringUtils.isEmpty(userTokenJson)) {
					return JSON.parseObject(userTokenJson, ApiUserToken.class);
				}
				throw new TigerException(resultCodeConfiguration.getTokenErrorCode(), resultCodeConfiguration.getTokenErrorCodeMsg());
			}
		});
	}
}
