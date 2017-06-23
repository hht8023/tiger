/**
 * Copyright © 1998-2017, enn Inc. All Rights Reserved.
 */
package com.xyz.tiger.dao.base.util.page;

import java.io.Serializable;


/**
 * 封装类传递数据，用于之前jsonReader中的变量
 * 
 * @author Hanht
 * @date 2017年3月6日
 */
public class JQGridRequest implements Serializable{
	
	private static final long serialVersionUID = 36321075138929075L;
	
	/* 当前页数 */
	private int page;
	/* 每页显示多少条记录  */
	private int rows;
	/* 排序的列 */
	private String sidx;
	/* 排序规则 */
	private String sord;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSidx() {
		return sidx;
	}

	public void setSidx(String sidx) {
		this.sidx = sidx;
	}

	public String getSord() {
		return sord;
	}

	public void setSord(String sord) {
		this.sord = sord;
	}

	/* LIMIT 参数 返回记录行的偏移量 */
	public int getOffset() {
		return (this.getPage() - 1) * this.getRows();
	}

}
