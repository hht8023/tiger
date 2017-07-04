package com.xyz.tiger.dao.base.datasource;

import com.xyz.tiger.base.util.log.LogUtil;


/**
 * 本地线程，数据源上下文
 */
public class DataSourceContextHolder {

	//线程本地环境
	private static final ThreadLocal<String> local = new ThreadLocal<String>();

    public static ThreadLocal<String> getLocal() {
        return local;
    }

    /**
     * 读库
     */
    public static void setRead() {
        local.set(DataSourceType.read.getType());
        LogUtil.info("数据库切换到读库...");
    }

    /**
     * 写库
     */
    public static void setWrite() {
        local.set(DataSourceType.write.getType());
        LogUtil.info("数据库切换到写库...");
    }

    public static String getReadOrWrite() {
        return local.get();
    }
    
    public static void clear(){
    	local.remove();
    }
}
