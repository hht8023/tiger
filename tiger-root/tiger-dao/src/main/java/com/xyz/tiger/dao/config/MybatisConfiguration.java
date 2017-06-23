/**
 * Copyright © 1998-2017, enn Inc. All Rights Reserved.
 */
package com.xyz.tiger.dao.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
<<<<<<< HEAD
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
=======
>>>>>>> 3ac410474fc9bf4f6d22ce2d95a733bf756752cb
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import com.xyz.tiger.dao.base.datasource.DataSourceConfiguration;
import com.xyz.tiger.dao.base.datasource.DataSourceContextHolder;
import com.xyz.tiger.dao.base.datasource.DataSourceType;
import com.xyz.tiger.utils.log.LogUtil;
import com.xyz.tiger.utils.spirng.SpringContextHolder;

/**
<<<<<<< HEAD
 * 注册数据源
 * 
=======
>>>>>>> 3ac410474fc9bf4f6d22ce2d95a733bf756752cb
 * @author Hanht
 * @date 2017年6月16日
 */
@Configuration
@AutoConfigureAfter(DataSourceConfiguration.class)
<<<<<<< HEAD
@MapperScan(basePackages = "com.xyz.tiger.*.dao")
=======
@MapperScan(basePackages="com.fei.springboot.dao")
>>>>>>> 3ac410474fc9bf4f6d22ce2d95a733bf756752cb
public class MybatisConfiguration {

	@Value("${datasource.readSize}")
	private String dataSourceSize;
<<<<<<< HEAD

	@Value("${mybatis.typeAliasesPackage}")
	private String typeAliasesPackage;

	@Value("${mybatis.mapperLocations}")
	private String mapperLocations;

	@Value("${mybatis.configLocation}")
	private String configLocation;

	@Autowired
	@Qualifier("writeDataSource")
	private DataSource writeDataSource;

	@Autowired
	@Qualifier("readDataSource1")
	private DataSource readDataSource1;

	@Autowired
	@Qualifier("readDataSource2")
	private DataSource readDataSource2;

=======
	
	@Autowired
	@Qualifier("writeDataSource")
	private DataSource writeDataSource;
	@Autowired
	@Qualifier("readDataSource1")
	private DataSource readDataSource01;
	@Autowired
	@Qualifier("readDataSource2")
	private DataSource readDataSource02;
	
>>>>>>> 3ac410474fc9bf4f6d22ce2d95a733bf756752cb
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactorys() throws Exception {
		LogUtil.info("--------------------  sqlSessionFactory init ---------------------");
		SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(roundRobinDataSouceProxy());
<<<<<<< HEAD
		// 设置实体类所在的包
		sessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
		// 设置mapper.xml文件所在位置
		Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocations);
		sessionFactoryBean.setMapperLocations(resources);
		// 设置mybatis-config.xml配置文件位置
		sessionFactoryBean.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
		return sessionFactoryBean.getObject();
	}

	/**
	 * 把所有数据库都放在路由中
	 */
	@Bean(name = "roundRobinDataSouceProxy")
	public AbstractRoutingDataSource roundRobinDataSouceProxy() {
		final int readSize = Integer.parseInt(dataSourceSize);
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		// 把所有数据库都放在targetDataSources中,注意key值要和determineCurrentLookupKey()中代码写的一致，
		// 否则切换数据源时找不到正确的数据源
		targetDataSources.put(DataSourceType.write.getType(), writeDataSource);
		targetDataSources.put(DataSourceType.read.getType() + 1, readDataSource1);
		targetDataSources.put(DataSourceType.read.getType() + 2, readDataSource2);
		// 路由类，寻找对应的数据源
		AbstractRoutingDataSource proxy = new AbstractRoutingDataSource() {
			private AtomicInteger count = new AtomicInteger(0);

			/**
			 * 这是AbstractRoutingDataSource类中的一个抽象方法，
			 * 而它的返回值是你所要用的数据源dataSource的key值，有了这个key值，
			 * targetDataSources就从中取出对应的DataSource，如果找不到，就用配置默认的数据源。
			 */
			@Override
			protected Object determineCurrentLookupKey() {
				String typeKey = DataSourceContextHolder.getReadOrWrite();

				if (typeKey == null) {
					throw new NullPointerException("数据库路由时，决定使用哪个数据库源类型不能为空...");
				}

				if (typeKey.equals(DataSourceType.write.getType())) {
					LogUtil.info("使用数据库write.............");
					return DataSourceType.write.getType();
				}

				// 读库， 简单负载均衡
				int number = count.getAndAdd(1);
				int lookupKey = (number % readSize) + 1;
				LogUtil.info("使用数据库read" + lookupKey);
				return DataSourceType.read.getType() + lookupKey;
			}
		};

		proxy.setDefaultTargetDataSource(writeDataSource);// 默认库
		proxy.setTargetDataSources(targetDataSources);
		return proxy;
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	// 事务管理
	@Bean
	public PlatformTransactionManager annotationDrivenTransactionManager() {
		return new DataSourceTransactionManager((DataSource) SpringContextHolder.getBean("roundRobinDataSouceProxy"));
	}
=======
		
		return sessionFactoryBean.getObject();
	}
	
	/**
     * 把所有数据库都放在路由中
     */
    @Bean(name="roundRobinDataSouceProxy")
    public AbstractRoutingDataSource roundRobinDataSouceProxy() {
    	final int readSize = Integer.parseInt(dataSourceSize);
    	Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        //把所有数据库都放在targetDataSources中,注意key值要和determineCurrentLookupKey()中代码写的一致，
        //否则切换数据源时找不到正确的数据源
        targetDataSources.put(DataSourceType.write.getType(), writeDataSource);
        for (int i = 0; i < readSize; i++) {
			targetDataSources.put(DataSourceType.read.getType() + (i + 1), "readDataSource" + (i + 1));
		}
        
        //路由类，寻找对应的数据源
        AbstractRoutingDataSource proxy = new AbstractRoutingDataSource(){
        	 private AtomicInteger count = new AtomicInteger(0);
        	/**
             * 这是AbstractRoutingDataSource类中的一个抽象方法，
             * 而它的返回值是你所要用的数据源dataSource的key值，有了这个key值，
             * targetDataSources就从中取出对应的DataSource，如果找不到，就用配置默认的数据源。
             */
        	@Override
        	protected Object determineCurrentLookupKey() {
        		String typeKey = DataSourceContextHolder.getReadOrWrite();
        		
        		if(typeKey == null){
        			throw new NullPointerException("数据库路由时，决定使用哪个数据库源类型不能为空...");
        		}
        		
                if (typeKey.equals(DataSourceType.write.getType())){
                	LogUtil.info("使用数据库write.............");
                    return DataSourceType.write.getType();
                }
                	
                //读库， 简单负载均衡
                int number = count.getAndAdd(1);
                int lookupKey = (number % readSize) + 1;
                LogUtil.info("使用数据库read-" + lookupKey+1);
                return DataSourceType.read.getType() + lookupKey + 1;
        	}
        };
        
        proxy.setDefaultTargetDataSource(writeDataSource);//默认库
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }
    
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
    	return new SqlSessionTemplate(sqlSessionFactory);
    }
    
    //事务管理
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager((DataSource)SpringContextHolder.getBean("roundRobinDataSouceProxy"));
    }
>>>>>>> 3ac410474fc9bf4f6d22ce2d95a733bf756752cb
}
