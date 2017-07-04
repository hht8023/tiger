package com.xyz.tiger.dao.base.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.xyz.tiger.base.util.log.LogUtil;

/**
 * 解析配置项
 * 
 * @author Hanht
 * @date 2017年6月12日
 */
@Configuration
public class DataSourceConfiguration {

	@Value("${datasource.type}")
	private Class<? extends DataSource> dataSourceType;

	@Bean(name = "writeDataSource")
	@Primary
	@ConfigurationProperties(prefix = "datasource.write")
	public DataSource writeDataSource() {
		LogUtil.info("-------------------- writeDataSource init ---------------------");
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	/**
	 * 有多少个从库就要配置多少个
	 * 
	 * @return
	 */
	@Bean(name = "readDataSource1")
	@ConfigurationProperties(prefix = "datasource.read1")
	public DataSource readDataSourceOne() {
		LogUtil.info("-------------------- readDataSourceOne init ---------------------");
		return DataSourceBuilder.create().type(dataSourceType).build();
	}

	@Bean(name = "readDataSource2")
	@ConfigurationProperties(prefix = "datasource.read2")
	public DataSource readDataSourceTwo() {
		LogUtil.info("-------------------- readDataSourceTwo init ---------------------");
		return DataSourceBuilder.create().type(dataSourceType).build();
	}
}
