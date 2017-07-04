package com.xyz.tiger.dao.base.datasource.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.xyz.tiger.base.util.log.LogUtil;
import com.xyz.tiger.dao.base.datasource.DataSourceContextHolder;
import com.xyz.tiger.dao.base.datasource.DataSourceType;

/**
 * 在service层切换数据源
 * 
 * 必须在事务AOP之前执行，通过Order注解,order的值越小，越先执行
 * 如果一旦开始切换到写库，则之后的读都会走写库
 *
 */
@Aspect
@Order(-1)
@Component
public class DataSourceAop {
	
	@Before("execution(* com.xyz.tiger..service..*.find*(..)) "
			+ " or execution(* com.xyz.tiger..service..*.get*(..)) "
			+ " or execution(* com.xyz.tiger..service..*.select*(..))")
    public void setReadDataSourceType() {
		//如果已经开启写事务了，那之后的所有读都从写库读
		if(!DataSourceType.write.getType().equals(DataSourceContextHolder.getReadOrWrite())){
			LogUtil.info(" DataSourceAop readDataSource 拦截 ");
			DataSourceContextHolder.setRead();
		}
    }
	
	@Before("execution(* com.xyz.tiger..service..*.insert*(..)) "
    		+ " or execution(* com.xyz.tiger..service..*.update*(..))"
    		+ " or execution(* com.xyz.tiger..service..*.add*(..))"
    		+ " or @annotation(com.xyz.tiger.dao.base.datasource.annotation.WriteDataSource) ")
    public void setWriteDataSourceType() {
        DataSourceContextHolder.setWrite();
    }
	
}
