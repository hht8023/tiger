/**
 * Copyright © 1998-2017, enn Inc. All Rights Reserved.
 */
package com.xyz.tiger.dao.base.util.page;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 简单分页模型
 * </p>
 * 
 * @author Hanht
 * @date 2017年3月7日
 */
public class Pagination implements Serializable {

	private static final long serialVersionUID = 1855430379688677151L;
	/* 总页数 */
	private int total;
	/* 总记录数 */
	private long records;
	/* 当前页 */
	private int page = 1;
	/* 每页显示条数，默认 10 */
	private int size = 10;
	/**
	 * 查询数据列表
	 */
	private List<?> rows = Collections.emptyList();
	
	public Pagination() {
		super();
	}
	/**
	 * <p>
	 * 分页构造函数
	 * </p>
	 * 
	 * @param page
	 *            当前页
	 * @param size
	 *            每页显示条数
	 * @param records
	 *            总记录数
	 * @param List<T> rows
	 *            符合条件的记录数
	 */
	public Pagination(int page, int size, long records, List<?> rows) {
		if (page > 1) {
			this.page = page;
		}
		this.size = size;
		this.records = records;
		this.total = getTotal();
		this.rows = rows;
	}
	
	
	protected static int offsetCurrent(int page, int size) {
		if (page > 0) {
			return (page - 1) * size;
		}
		return 0;
	}

	public int getOffsetCurrent() {
		return offsetCurrent(this.page, this.size);
	}
	
	public int getTotal() {
		if (this.size == 0) {
			return 0;
		}
		this.total = (int)Math.ceil((double)this.records / (double)this.size);
		return this.total;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	public long getRecords() {
		return records;
	}
	public void setRecords(long records) {
		this.records = records;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<Object> rows) {
		this.rows = rows;
	}
	
	public static void main(String args[]){
		double a = 16;
		double b = 10;
		System.out.println(Math.ceil(a/b));
	}
}
