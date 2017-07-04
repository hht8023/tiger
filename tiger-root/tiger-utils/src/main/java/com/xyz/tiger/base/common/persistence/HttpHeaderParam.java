package com.xyz.tiger.base.common.persistence;

import javax.servlet.http.HttpServletRequest;

import com.xyz.tiger.base.common.constant.GlobalConstant;
import com.xyz.tiger.base.util.DesUtil;

/**
 * @Description: HttpHeader参数信息
 * @author: Hanht
 * @since: jdk1.8
 * @time: 2017年6月29日
 */
public class HttpHeaderParam {
	// 签名串
	private String sign;
	private Long userId;
	private String appkey;
	private String timeStamp;

	public String toString() {
		StringBuilder instance = new StringBuilder();
		instance.append(" HttpHeader: {");
		instance.append(GlobalConstant.TIMESTAMP + "[").append(this.timeStamp).append("], ");
		instance.append(GlobalConstant.SIGN + "[").append(this.sign).append("] ");
		instance.append(GlobalConstant.USE_ID + "[").append(this.userId).append("] ");
		instance.append(GlobalConstant.APPKEY + "[").append(this.appkey).append("] ");
		instance.append(" }");
		return instance.toString();
	}

	/**
	 * 解析HttpServletRequest，生成HttpHeader对象
	 * 
	 * @param request
	 * @return
	 */
	public static HttpHeaderParam parseRequestHeader(HttpServletRequest request) throws Exception {
		HttpHeaderParam header = new HttpHeaderParam();
		header.setTimeStamp(request.getHeader(GlobalConstant.TIMESTAMP));
		header.setSign(request.getHeader(GlobalConstant.SIGN));
		header.setAppkey(request.getHeader(GlobalConstant.APPKEY));
		// 用户ip都是加密的
		String userId = DesUtil.decrypt(request.getHeader(GlobalConstant.USE_ID), header.getAppkey());
		header.setUserId(Long.parseLong(userId));
		// 将解密后的userId再保存起来等会用到
		request.getSession().setAttribute(GlobalConstant.USE_ID, userId);
		return header;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAppkey() {
		return appkey;
	}

	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

}
