/**
 * Copyright © 1998-2017, enn Inc. All Rights Reserved.
 */
package com.xyz.tiger.ms.database;

public class DbContextHolder {
	public enum DbType {
		MASTER, SLAVE
	}

	private static final ThreadLocal<DbType> contextHolder = new ThreadLocal<>();

	public static void setDbType(DbType dbType) {
		if (dbType == null)
			throw new NullPointerException();
		contextHolder.set(dbType);
	}

	public static DbType getDbType() {
		return contextHolder.get() == null ? DbType.MASTER : contextHolder.get();
	}

	public static void clearDbType() {
		contextHolder.remove();
	}
}
