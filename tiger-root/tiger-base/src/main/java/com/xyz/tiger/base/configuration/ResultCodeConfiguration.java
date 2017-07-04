/**
 * Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
 */
package com.xyz.tiger.base.configuration;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.fastjson.JSON;
import com.xyz.tiger.base.common.persistence.JsonResult;

/**
 * @Description: 定义API的状态返回信息
 * @author: Hanht
 * @since: jdk1.8
 * @time: 2017年6月29日
 */
@Configuration
@PropertySource(value = "classpath:resultCode.properties",encoding = "utf-8")
@ConfigurationProperties(prefix ="resultCode")
public class ResultCodeConfiguration implements InitializingBean {

	private String successResultCode;
	private String errorResultCode;

	private String successCode;
	private String successMsg;
	private String errCode;
	private String errMsg;
	private String blackipCode;
	private String blackipMsg;
	private String IpMaxInvoke;
	private String IpMaxInvokeMsg;

	private String loginNameWrong;
	private String loginNameWrongMsg;
	private String passwordWrong;
	private String passwordWrongMsg;

	private String loginErrorLimit;
	private String loginErrorLimitMsg;

	private String paramErrorCode;
	private String paramErrorMsg;

	private String tokenErrorCode;
	private String tokenErrorCodeMsg;

	private String signatureErrorCode;
	private String signatureErrorCodeMsg;
	
	private String timeStampErrorCode;
	private String timeStampErrorCodeMsg;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		successResultCode= JSON.toJSONString(new JsonResult(successCode,successMsg));
		errorResultCode= JSON.toJSONString(new JsonResult(errCode,errMsg));
	}

	public JsonResult getResult(){
		return new JsonResult(successCode,successMsg);
	}

	public String getSuccessResultCode() {
		return successResultCode;
	}

	public void setSuccessResultCode(String successResultCode) {
		this.successResultCode = successResultCode;
	}

	public String getErrorResultCode() {
		return errorResultCode;
	}

	public void setErrorResultCode(String errorResultCode) {
		this.errorResultCode = errorResultCode;
	}

	public String getSuccessCode() {
		return successCode;
	}

	public void setSuccessCode(String successCode) {
		this.successCode = successCode;
	}

	public String getSuccessMsg() {
		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getBlackipCode() {
		return blackipCode;
	}

	public void setBlackipCode(String blackipCode) {
		this.blackipCode = blackipCode;
	}

	public String getBlackipMsg() {
		return blackipMsg;
	}

	public void setBlackipMsg(String blackipMsg) {
		this.blackipMsg = blackipMsg;
	}

	public String getIpMaxInvoke() {
		return IpMaxInvoke;
	}

	public void setIpMaxInvoke(String ipMaxInvoke) {
		IpMaxInvoke = ipMaxInvoke;
	}

	public String getIpMaxInvokeMsg() {
		return IpMaxInvokeMsg;
	}

	public void setIpMaxInvokeMsg(String ipMaxInvokeMsg) {
		IpMaxInvokeMsg = ipMaxInvokeMsg;
	}

	public String getLoginNameWrong() {
		return loginNameWrong;
	}

	public void setLoginNameWrong(String loginNameWrong) {
		this.loginNameWrong = loginNameWrong;
	}

	public String getLoginNameWrongMsg() {
		return loginNameWrongMsg;
	}

	public void setLoginNameWrongMsg(String loginNameWrongMsg) {
		this.loginNameWrongMsg = loginNameWrongMsg;
	}

	public String getPasswordWrong() {
		return passwordWrong;
	}

	public void setPasswordWrong(String passwordWrong) {
		this.passwordWrong = passwordWrong;
	}

	public String getPasswordWrongMsg() {
		return passwordWrongMsg;
	}

	public void setPasswordWrongMsg(String passwordWrongMsg) {
		this.passwordWrongMsg = passwordWrongMsg;
	}

	public String getLoginErrorLimit() {
		return loginErrorLimit;
	}

	public void setLoginErrorLimit(String loginErrorLimit) {
		this.loginErrorLimit = loginErrorLimit;
	}

	public String getLoginErrorLimitMsg() {
		return loginErrorLimitMsg;
	}

	public void setLoginErrorLimitMsg(String loginErrorLimitMsg) {
		this.loginErrorLimitMsg = loginErrorLimitMsg;
	}

	public String getParamErrorCode() {
		return paramErrorCode;
	}

	public void setParamErrorCode(String paramErrorCode) {
		this.paramErrorCode = paramErrorCode;
	}

	public String getParamErrorMsg() {
		return paramErrorMsg;
	}

	public void setParamErrorMsg(String paramErrorMsg) {
		this.paramErrorMsg = paramErrorMsg;
	}

	public String getTokenErrorCode() {
		return tokenErrorCode;
	}

	public void setTokenErrorCode(String tokenErrorCode) {
		this.tokenErrorCode = tokenErrorCode;
	}

	public String getTokenErrorCodeMsg() {
		return tokenErrorCodeMsg;
	}

	public void setTokenErrorCodeMsg(String tokenErrorCodeMsg) {
		this.tokenErrorCodeMsg = tokenErrorCodeMsg;
	}

	public String getSignatureErrorCode() {
		return signatureErrorCode;
	}

	public void setSignatureErrorCode(String signatureErrorCode) {
		this.signatureErrorCode = signatureErrorCode;
	}

	public String getSignatureErrorCodeMsg() {
		return signatureErrorCodeMsg;
	}

	public void setSignatureErrorCodeMsg(String signatureErrorCodeMsg) {
		this.signatureErrorCodeMsg = signatureErrorCodeMsg;
	}

	public String getTimeStampErrorCode() {
		return timeStampErrorCode;
	}

	public void setTimeStampErrorCode(String timeStampErrorCode) {
		this.timeStampErrorCode = timeStampErrorCode;
	}

	public String getTimeStampErrorCodeMsg() {
		return timeStampErrorCodeMsg;
	}

	public void setTimeStampErrorCodeMsg(String timeStampErrorCodeMsg) {
		this.timeStampErrorCodeMsg = timeStampErrorCodeMsg;
	}
	
}
