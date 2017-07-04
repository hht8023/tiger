/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xyz.tiger.base.common.constant;

/**
 * 全局常量类
 * @author Hanht
 * @since jdk1.8 
 * 2017年6月29日
 */
public class GlobalConstant {

	public static final String USER_TONKEN = "X-Emp-Token";
	public static final String TIMESTAMP = "X-Emp-Timestamp";
	public static final String SIGN = "X-Emp-Signature";
	public static final String USE_ID = "X-Emp-U";
	public static final String APPKEY = "X-Emp-AppKey";
	public static final String DEFAULT_CHARSET = "UTF-8";
	public static final String JSON_CONTENT_TYPE_VALUE = "application/json;charset=UTF-8";
	public static final String XML_CONTENT_TYPE_VALUE = "application/xml;charset=UTF-8";
	public static final String TEXT_CONTENT_TYPE_VALUE = "application/text;charset=UTF-8";
	
	public static final String USER_TOKEN = "user_token_";
	public static final String BLACK_IP = "blackip_hset";
	public static final String USER_NAME = "userinfo_loginname_";
	public static final String LOGIN_ERROR_LIMIT = "loginerror_limit_";
	
	public static final int LOGIN_ERROR_MAXNUMBER = 3;
	
	
}
