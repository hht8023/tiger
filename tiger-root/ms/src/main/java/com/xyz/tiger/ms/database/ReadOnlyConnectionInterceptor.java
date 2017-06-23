/**
 * Copyright © 1998-2017, enn Inc. All Rights Reserved.
 */
package com.xyz.tiger.ms.database;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * TODO
 * 
 * @author Hanht
 * @date 2017年6月15日
 */
@Aspect
@Component
public class ReadOnlyConnectionInterceptor implements Ordered {

	@Around("@annotation(readOnlyConnection)")
	public Object proceed(ProceedingJoinPoint proceedingJoinPoint, ReadOnlyConnection readOnlyConnection) throws Throwable {
		try {
			System.out.println("set database connection to read only");
			DbContextHolder.setDbType(DbContextHolder.DbType.SLAVE);
			Object result = proceedingJoinPoint.proceed();
			return result;
		} finally {
			DbContextHolder.clearDbType();
			System.out.println("restore database connection");
		}
	}

	@Override
	public int getOrder() {
		return 0;
	}

}
