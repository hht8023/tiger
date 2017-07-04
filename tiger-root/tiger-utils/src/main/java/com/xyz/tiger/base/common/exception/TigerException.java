package com.xyz.tiger.base.common.exception;

import com.xyz.tiger.base.common.persistence.JsonResult;

/**
 * @Description: 封装业务异常
 * @author: Hanht
 * @since: jdk1.8
 * @time: 2017年6月29日
 */
public class TigerException extends Exception {

	private static final long serialVersionUID = 1L;

	private String code;

	public TigerException(String message) {
		super(message);
	}

	public TigerException(String code, String message) {
		super(message);
		this.code = code;
	}

	public TigerException(String message, Throwable cause) {
		super(message, cause);
	}

	public TigerException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 异常返回封装对象
	 * 
	 * @return
	 */
	public JsonResult getReturnRestult() {
		return new JsonResult(this.getCode(), this.getMessage());
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
