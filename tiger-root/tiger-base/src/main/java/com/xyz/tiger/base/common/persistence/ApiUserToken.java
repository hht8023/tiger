package com.xyz.tiger.base.common.persistence;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: API相关参数信息
 * @author: Hanht
 * @since: jdk1.8
 * @time: 2017年6月29日
 */
public class ApiUserToken implements Serializable {
	
	private static final long serialVersionUID = 1640649351444893129L;
	
	private Long id;
	private String token;
	private Date createTime;
	private Date validTime;
	private Date lastVisitTime;
	private Long visitCounter;
	private String appkey;
	private Object userId;
	private String userEmail;
	private Long delayTime;
	private String deviceId;
	private String deviceToken;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getValidTime() {
		return validTime;
	}
	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}
	public Date getLastVisitTime() {
		return lastVisitTime;
	}
	public void setLastVisitTime(Date lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}
	public Long getVisitCounter() {
		return visitCounter;
	}
	public void setVisitCounter(Long visitCounter) {
		this.visitCounter = visitCounter;
	}
	public String getAppkey() {
		return appkey;
	}
	public void setAppkey(String appkey) {
		this.appkey = appkey;
	}
	public Object getUserId() {
		return userId;
	}
	public void setUserId(Object userId) {
		this.userId = userId;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public Long getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(Long delayTime) {
		this.delayTime = delayTime;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceToken() {
		return deviceToken;
	}
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}
	
}
