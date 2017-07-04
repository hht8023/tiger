/**
* Copyright © 1998-2017, Glodon Inc. All Rights Reserved.
*/
package com.xyz.tiger.base.configuration;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.xyz.tiger.base.util.log.LogUtil;

@Configuration
public class MyWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter {
	
	@Autowired
	InterceptorConfig interceptorConfig;
	
	@Override
    public void addInterceptors(InterceptorRegistry registry){
		List<Map<String,String>> list = interceptorConfig.getInterceptor();
		if(list != null && !CollectionUtils.isEmpty(list)){
			for(Map<String,String> map : list){
				String className = map.get("className");
				String[] pathPatterns = map.get("pathPatterns").split(",");
				LogUtil.info("     拦截器类和路径            " +className + "===" + pathPatterns);
				try {
					InterceptorRegistration ir = registry.addInterceptor((HandlerInterceptor)Class.forName(className).newInstance());
					for(String pp : pathPatterns){
						ir.addPathPatterns(pp);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	        super.addInterceptors(registry);
		}
    }
}
