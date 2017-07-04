/**
* Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
*/
package com.xyz.tiger.running.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;

import com.xyz.tiger.base.common.constant.GlobalConstant;
import com.xyz.tiger.base.common.exception.TigerException;
import com.xyz.tiger.base.common.persistence.ApiUserToken;
import com.xyz.tiger.base.common.persistence.HttpHeaderParam;
import com.xyz.tiger.base.configuration.ResultCodeConfiguration;
import com.xyz.tiger.base.util.ApiTools;
import com.xyz.tiger.base.util.MD5Util;
import com.xyz.tiger.base.util.TokenHelper;
import com.xyz.tiger.cache.redis.util.RedisDaoTemplate;
import com.xyz.tiger.cache.redis.util.callback.RedisCallback;

/**
 * @Description: 登录安全认证
 * @author: Hanht
 * @since: jdk1.8
 * @time: 2017年6月29日
 */
@Service
public class LoginSecurity {
	
	@Autowired
    private TokenHelper tokenHelper;
	@Autowired
    private ResultCodeConfiguration resultCodeConfiguration;
	@Autowired
    private RedisDaoTemplate redisDaoTemplate;
	
	/**
     * 检验用户是否登陆:
     * step1. 是否传递用户id属性。
     * step2. 根据传过来的用户id 去查询用户的token 有过期时间 
     * 		     如果不存在表示用户没有登陆
     * 		     存在的话（存在不一定代表是你本人登陆，举个例子A id=1 登陆了。这时候如果B知道你的id，就可以模拟A登陆，可以直接操作A的消息）就继续校验签名。
     * step3. 校验签名里面是MD5（token+时间+接口参数）
     * step4. 校验时间戳、判断时间间隔
     *	
     * @param request
     * @throws Exception
     */
    public void checkIsLoginAndValidSgnature(HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.validUserId(request);
    	HttpHeaderParam httpHeaderParam = HttpHeaderParam.parseRequestHeader(request);
    	ApiUserToken apiUserToken = tokenHelper.getUserToken(httpHeaderParam.getUserId());
        validSignature(httpHeaderParam, apiUserToken.getToken());
        validTimeStamp(httpHeaderParam);
        setVal(response, httpHeaderParam.getAppkey(), apiUserToken.getToken());
    }
    
    /**
     * 接口签名参数校验
     * APPKEY+TIMESTAMP+USERTOKEN
     *
     * @param httpHeaderParam
     * @param token
     * @throws Exception
     */
    private void validSignature(HttpHeaderParam httpHeaderParam, String token) throws Exception {
        if (!MD5Util.md5Encode(httpHeaderParam.getAppkey() + httpHeaderParam.getTimeStamp() + token).equals(httpHeaderParam.getSign()))
            throw new TigerException(resultCodeConfiguration.getSignatureErrorCode(), resultCodeConfiguration.getSignatureErrorCodeMsg());
    }
    
    /**
     * 检验时间戳间隔
     *
     * @param httpHeaderParam
     */
    private void validTimeStamp(HttpHeaderParam httpHeaderParam) throws Exception {
    	if((ApiTools.getTimeStamp() - Long.parseLong(httpHeaderParam.getTimeStamp()))/60 > 5){
    		throw new TigerException(resultCodeConfiguration.getTimeStampErrorCode(), resultCodeConfiguration.getTimeStampErrorCodeMsg());
    	}
    }
    
    /**
     * 校验是否登录
     * @param request
     * @throws TigerException
     */
    private void validUserId(HttpServletRequest request) throws TigerException{
		if(StringUtils.isEmpty(request.getHeader(GlobalConstant.USE_ID))){
			throw new TigerException(resultCodeConfiguration.getTokenErrorCode(), resultCodeConfiguration.getTokenErrorCodeMsg());
		}
    }
    
    /**
     * 检查登陆错误次数
     *
     * @param userInfo
     * @throws Exception
     */
    public void checkLoginErrorTimes(String userName) throws Exception {
        redisDaoTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(Jedis jedis) throws Exception {
                String key = GlobalConstant.LOGIN_ERROR_LIMIT + userName;
                String times = jedis.get(key);
                if (times != null) {
                    if (Integer.parseInt(times) >= GlobalConstant.LOGIN_ERROR_MAXNUMBER) {
                        throw new TigerException(resultCodeConfiguration.getLoginErrorLimit(), resultCodeConfiguration.getLoginErrorLimitMsg());
                    }
                }
                return null;
            }
        });
    }
    
    public void incrLoginErrorTimes(String userName) throws Exception {
    	redisDaoTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(Jedis jedis) throws Exception {
                String key = GlobalConstant.LOGIN_ERROR_LIMIT + userName;
                String times = jedis.get(key);
                if (times == null) {
                    jedis.setex(key, 60 * 30, "1");
                } else {
                    jedis.incr(key);
                }
                return null;
            }
        });
    }
    
    private void setVal(HttpServletResponse response, String appKey, String token) throws Exception{
    	String timeStamp = String.valueOf(ApiTools.getTimeStamp());
        response.setHeader(GlobalConstant.TIMESTAMP, String.valueOf(ApiTools.getTimeStamp()));
		response.setHeader(GlobalConstant.SIGN, MD5Util.md5Encode(appKey + timeStamp + token));
    }
}
