package com.xyz.tiger.running.domain;

import java.io.Serializable;
import java.util.Date;

import com.xyz.tiger.dao.base.annotation.PUBVALUE;
import com.xyz.tiger.dao.base.annotation.PrimaryKey;
import com.xyz.tiger.dao.base.annotation.Table;

@Table(value="t_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1068684416095259302L;
	
	@PrimaryKey(pkRule = PUBVALUE.PkRule.INCREMENT)
	private String id;
	private String userName;
	private String password;
	private Date gmtCreate;
	private Date gmtModified;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getGmtCreate() {
		return gmtCreate;
	}
	public void setGmtCreate(Date gmtCreate) {
		this.gmtCreate = gmtCreate;
	}
	public Date getGmtModified() {
		return gmtModified;
	}
	public void setGmtModified(Date gmtModified) {
		this.gmtModified = gmtModified;
	}
}
