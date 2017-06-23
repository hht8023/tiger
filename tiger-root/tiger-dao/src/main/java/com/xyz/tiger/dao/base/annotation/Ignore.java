package com.xyz.tiger.dao.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 
 * 		标识某个字段不录入数据库中<br>
 * 		标识该注解的字段将没有增删改查的功能。
 * @author Hanht
 * @date 2016年8月9日 下午3:39:25 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Ignore {

}
