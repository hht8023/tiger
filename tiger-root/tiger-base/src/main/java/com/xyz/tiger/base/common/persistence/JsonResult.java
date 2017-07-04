/**
 * Copyright © 1998-2017, enn Inc. All Rights Reserved.
 */
package com.xyz.tiger.base.common.persistence;

import com.alibaba.fastjson.JSON;

/**
 * 定义api的返回对象格式
 * 
 * @author Hanht
 * @date 2017年6月5日
 */
public class JsonResult {
	private String code;
	private String message;
	private Object data;


	public JsonResult(String code) {
		this.setCode(code);
	}
	
	public JsonResult(String code, String message) {
		this.setCode(code);
		this.setMessage(message);
	}
	
	public JsonResult(String code, Object data) {
		this.setCode(code);
		this.setData(data);
	}
	
	public JsonResult(String code, String message, Object data) {
		this.setCode(code);
		this.setMessage(message);
		this.setData(data);
	}

	public String toJSONString(){
		return JSON.toJSONString(this);
    }
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
