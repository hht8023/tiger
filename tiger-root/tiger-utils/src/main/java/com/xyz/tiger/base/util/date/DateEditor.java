/**
 * Copyright © 1998-2017, enn Inc. All Rights Reserved.
 */
package com.xyz.tiger.base.util.date;

import java.beans.PropertyEditorSupport;
import java.util.Date;

import org.springframework.util.StringUtils;

/**
 * 日期类型解析器 <br>
 * 对于需要转换为Date类型的属性，使用DateEditor进行处理
 * 
 * @author Hanht
 * @date 2017年3月15日
 */
public class DateEditor extends PropertyEditorSupport {

	public DateEditor() {
	}

	/**
	 * Parse the Date from the given text, using the specified DateFormat.
	 */
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (!StringUtils.hasText(text)) {
			setValue(null);
		} else {
			if (text.contains(":"))
				setValue(DateUtil.StringToDate(text, DateStyle.YYYY_MM_DD_HH_MM_SS));
			else
				setValue(DateUtil.StringToDate(text, DateStyle.YYYY_MM_DD));
		}
	}

	/**
	 * Format the Date as String, using the specified DateFormat.
	 */
	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		return (value != null ? DateUtil.DateToString(value, DateStyle.YYYY_MM_DD) : "");
	}
}
