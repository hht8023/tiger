package com.xyz.tiger.dao.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 
 * 		实体类字段约束注解<br>
 * 		标有此注解的字段对应数据库中的字段名强制约束为该注解中的name值
 * @author Hanht
 * @date 2016年8月9日 下午3:38:49 
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableColumn {
	String value() default "";
}
