/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xyz.tiger.running.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xyz.tiger.base.common.constant.GlobalConstant;
import com.xyz.tiger.running.domain.User;
import com.xyz.tiger.running.service.ILoginService;

/**
 * @Description: 登录控制器
 * @author: Hanht
 * @since: jdk1.8
 * @time: 2017年6月29日
 */
@RestController
public class LoginController {

	@Autowired
	ILoginService loginService;
	
	@RequestMapping(value = "/login/{username}/{password}")
	public String login(@PathVariable String username, @PathVariable String password, 
			@RequestHeader(required = false, name = GlobalConstant.APPKEY) String appkey, 
			HttpServletResponse response) {
		// 验证用户名密码是否正确
		User user = this.loginService.validateUser(username, password);
		return loginService.getSuccessLoginResult(user, "8GWCwczIkWaMvOaYO1IqeXdYqN61vj4V", response);
	}
}
