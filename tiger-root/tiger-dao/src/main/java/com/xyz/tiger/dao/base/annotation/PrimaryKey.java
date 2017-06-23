package com.xyz.tiger.dao.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 定义PO类的主键类型
 * @author Hanht
 * @date 2016年8月9日 下午3:40:01
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrimaryKey {
	/**
	 * 主键数据类型，默认32位UUID字符串
	 * @return
	 */
	PUBVALUE.PkRule pkRule() default PUBVALUE.PkRule.UUID;
}
