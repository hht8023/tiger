package com.xyz.tiger.cache.redis.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
@PropertySource("classpath:application-cache.properties")
@ConfigurationProperties(prefix ="redis")
public class RedisConfiguration {
	
    private  String host;
    private  String port;
    private  String timeout;
    private  String passwords;

    private  String maxtotal;
    private  String maxidle;
    private  String minidle;
    private  String maxwaitmillis;
    private  String testonborrow;
    private  String testwhileidle;
    
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getTimeout() {
		return timeout;
	}
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	public String getPasswords() {
		return passwords;
	}
	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}
	public String getMaxtotal() {
		return maxtotal;
	}
	public void setMaxtotal(String maxtotal) {
		this.maxtotal = maxtotal;
	}
	public String getMaxidle() {
		return maxidle;
	}
	public void setMaxidle(String maxidle) {
		this.maxidle = maxidle;
	}
	public String getMinidle() {
		return minidle;
	}
	public void setMinidle(String minidle) {
		this.minidle = minidle;
	}
	public String getMaxwaitmillis() {
		return maxwaitmillis;
	}
	public void setMaxwaitmillis(String maxwaitmillis) {
		this.maxwaitmillis = maxwaitmillis;
	}
	public String getTestonborrow() {
		return testonborrow;
	}
	public void setTestonborrow(String testonborrow) {
		this.testonborrow = testonborrow;
	}
	public String getTestwhileidle() {
		return testwhileidle;
	}
	public void setTestwhileidle(String testwhileidle) {
		this.testwhileidle = testwhileidle;
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(factory);
		// key序列化方式,但是如果方法上有Long等非String类型的话，会报类型转换错误
		// 所以在没有自己定义key生成策略的时候，以下这个代码建议不要这么写，可以不配置或者自己实现ObjectRedisSerializer
		RedisSerializer<String> redisSerializer = new StringRedisSerializer();// Long类型不可以会出现异常信息;
		redisTemplate.setKeySerializer(redisSerializer);
		
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);

		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.afterPropertiesSet();

		return redisTemplate;
	}
}
