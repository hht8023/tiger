/**
* Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
*/
package com.xyz.tiger.base.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:interceptors.properties")
@ConfigurationProperties(prefix ="interceptors")
public class InterceptorConfig {
	
	private List<Map<String, String>> interceptor = new ArrayList<>();

	public List<Map<String, String>> getInterceptor() {
		return interceptor;
	}

	public void setInterceptor(List<Map<String, String>> interceptor) {
		this.interceptor = interceptor;
	}
	
}
