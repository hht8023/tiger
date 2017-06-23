package com.xyz.tiger.dao.base.sql.order;

/**
 * @Description 
 * 		排序接口，实现该接口的类，具有排序功能
 * @author Hanht
 * @date 2016年8月11日 下午5:34:26
 */
public interface OrderBy {
	/**
	 * 按照给定的字段名升序排列
	 * 
	 * @param fieldName
	 *            字段名
	 * @return orderby对象，方便链式操作
	 * 
	 * @date 2016年8月11日 下午5:34:26
	 */
	public OrderBy asc(String fieldName);

	/**
	 * 安州给定的字段降序排列
	 * 
	 * @param fieldName
	 *            字段名
	 * @return orderby对象，方便链式操作
	 * 
	 * @date 2016年8月11日 下午5:34:26
	 */
	public OrderBy desc(String fieldName);
}
