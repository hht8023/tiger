/**
 * Copyright © 1998-2017, enn Inc. All Rights Reserved.
 */
package com.xyz.tiger.base.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * log4j2日志Util
 * 
 * @author Hanht
 * @date 2017年6月7日
 */
public class LogUtil {

	private static Logger logger = null;

	static {
		StackTraceElement[] stackTraceElements = new Throwable().getStackTrace();
		String callerClassName = stackTraceElements[1].getClassName();
		logger = LoggerFactory.getLogger(callerClassName);
	}

	private LogUtil() {
	}

	public static void error(String msg) {  
		StackTraceElement se = Thread.currentThread().getStackTrace()[2];
		String clazzMethod = " " + se.getClassName() + "." + se.getMethodName() + "(" + se.getFileName() + ":" + se.getLineNumber() + ") ";

		String log = clazzMethod +"	" + " MSG: " + msg;
		logger.error(log);
    } 
	
	public static void error(String msg, Throwable e) {
		StackTraceElement se = Thread.currentThread().getStackTrace()[2];
		String clazzMethod = " " + se.getClassName() + "." + se.getMethodName() + "(" + se.getFileName() + ":" + se.getLineNumber() + ") ";

		String log = clazzMethod +"	" + " MSG: " + msg;
		logger.error(log, e);
	}

	public static void warn(String msg) {
		StackTraceElement se = Thread.currentThread().getStackTrace()[2];
		String clazzMethod = " " + se.getClassName() + "." + se.getMethodName() + "(" + se.getFileName() + ":" + se.getLineNumber() + ") ";

		String log = clazzMethod + "	" + " MSG: " + msg;
		logger.warn(log);
	}

	public static void info(String msg) {
		StackTraceElement se = Thread.currentThread().getStackTrace()[2];
		String clazzMethod = " " + se.getClassName() + "." + se.getMethodName() + "(" + se.getFileName() + ":" + se.getLineNumber() + ") ";

		String log = clazzMethod + "	" + " MSG: " + msg;
		logger.info(log);
	}

	public static void debug(String msg) {
		StackTraceElement se = Thread.currentThread().getStackTrace()[2];
		String clazzMethod = " " + se.getClassName() + "." + se.getMethodName() + "(" + se.getFileName() + ":" + se.getLineNumber() + ") ";

		String log = clazzMethod + " MSG: " + msg;
		logger.debug(log);
	}
}
