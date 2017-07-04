/**
* Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
*/
package com.xyz.tiger.running.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.xyz.tiger.base.common.exception.TigerException;
import com.xyz.tiger.base.configuration.ResultCodeConfiguration;
import com.xyz.tiger.base.util.ResponseUtils;
import com.xyz.tiger.base.util.log.LogUtil;
import com.xyz.tiger.base.util.spring.SpringContextHolder;
import com.xyz.tiger.running.security.LoginSecurity;

public class LoginInterceptor implements HandlerInterceptor {

	private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal StartTime");
	
	LoginSecurity loginSecurity = SpringContextHolder.getBean(LoginSecurity.class);
	ResultCodeConfiguration resultCodeConfiguration = SpringContextHolder.getBean(ResultCodeConfiguration.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		long beginTime = System.currentTimeMillis();// 1、开始时间
		startTimeThreadLocal.set(beginTime); // 线程绑定变量（该数据只有当前请求的线程可见）
		try {
			loginSecurity.checkIsLoginAndValidSgnature(request, response);
			return true;
		}catch (TigerException e) {
			ResponseUtils.renderJSON(response, e.getReturnRestult().toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
			ResponseUtils.renderJSON(response, resultCodeConfiguration.getErrorResultCode());
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		LogUtil.info("保存日志到日志系统");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		long beginTime = startTimeThreadLocal.get();
		long endTime = System.currentTimeMillis();
		long execTime = endTime - beginTime;
		// 大于5s,打印一下警告日志
		if (execTime > 200) {
			LogUtil.info(request.getRequestURI() + " 访问时间过长, 耗费:" + execTime + " ms");
		}
	}

}
