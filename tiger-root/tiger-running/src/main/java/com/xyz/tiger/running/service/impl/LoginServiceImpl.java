/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xyz.tiger.running.service.impl;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xyz.tiger.base.common.constant.GlobalConstant;
import com.xyz.tiger.base.common.exception.TigerException;
import com.xyz.tiger.base.common.persistence.ApiUserToken;
import com.xyz.tiger.base.configuration.ResultCodeConfiguration;
import com.xyz.tiger.base.util.ApiTools;
import com.xyz.tiger.base.util.DesUtil;
import com.xyz.tiger.base.util.MD5Util;
import com.xyz.tiger.base.util.TokenHelper;
import com.xyz.tiger.dao.base.sql.conditions.Cnds;
import com.xyz.tiger.dao.base.sql.where.C;
import com.xyz.tiger.running.domain.User;
import com.xyz.tiger.running.security.LoginSecurity;
import com.xyz.tiger.running.service.ILoginService;
import com.xyz.tiger.running.service.IUserService;

@Service
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private LoginSecurity loginSecurity;
	@Autowired
	private IUserService userService;
	@Autowired
	private ResultCodeConfiguration resultCodeConfiguration;
	@Autowired
	private TokenHelper tokenHelper;

	@Override
	public User validateUser(String userName, String pwd) {
		try {
			loginSecurity.checkLoginErrorTimes(userName);
			// 数据验证用户名密码是否正确 可以考虑缓存数据库存储验证
			User user = userService.selectOne(Cnds.me(User.class).where("userName", C.EQ, userName));
			if (user == null) {
				// 登陆名不存在异常！！！
				throw new TigerException(resultCodeConfiguration.getLoginNameWrong(), resultCodeConfiguration.getLoginNameWrongMsg());
			}
			if (user.getPassword().equals(MD5Util.md5Encode(pwd))) {
				return user;
			} else {
				loginSecurity.incrLoginErrorTimes(userName);
				throw new TigerException(resultCodeConfiguration.getPasswordWrong(), resultCodeConfiguration.getPasswordWrongMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String getSuccessLoginResult(User user, String appkey, HttpServletResponse response) {
		try {
			ApiUserToken token = tokenHelper.createToken(user.getId(), appkey);
			// token加密后返回
			String timeStamp = String.valueOf(ApiTools.getTimeStamp());
			response.setHeader(GlobalConstant.TIMESTAMP, timeStamp);
			response.setHeader(GlobalConstant.SIGN, MD5Util.md5Encode(appkey + timeStamp + token.getToken()));
			response.setHeader(GlobalConstant.USE_ID, DesUtil.encrypt(token.getUserId() + "", appkey));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultCodeConfiguration.getSuccessResultCode();
	}

}
