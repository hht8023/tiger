package com.xyz.tiger.dao.base.util;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.xyz.tiger.base.util.date.DateStyle;
import com.xyz.tiger.base.util.date.DateUtil;

/**
 * SQL工具类
 * @author Hanht
 * @date 2016年8月16日
 */

public class SqlUtils {

	public static final String SPACE = " ";
	
	/**
	 * 处理value
	 */
	public static Object handleValue(Object value) {
		if (value instanceof String) {
			value = "\'" + value + "\'";
		}else if (value instanceof Date) {
			Date date = (Date) value;
			String dateStr = DateUtil.DateToString(date, DateStyle.YYYY_MM_DD_HH_MM_SS);
			value = "STR_TO_DATE('" + dateStr + "','%Y-%m-%d %H:%i:%s')";
		} else if (value instanceof Boolean) {
			Boolean v = (Boolean) value;
			value = v ? 1 : 0;
		} else if (null == value || StringUtils.isBlank(value.toString())) {
			value = "''";
		}
		return value;
	}
	
	/**
	 * value左右侧拼接空格
	 */
	public static String appendSpace(Object value){
		return SPACE+value+SPACE;
	}
}
