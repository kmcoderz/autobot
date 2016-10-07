package com.test.autobot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class CacheConfig extends CachingConfigurerSupport {

	@Value("${spring.redis.host}")
	private String HOST;
	@Value("${spring.redis.port}")
	private int PORT;
	@Value("${spring.redis.database}")
	private int DATABASE;
	@Value("${spring.redis.password}")
	private String PASSWORD;

	@Bean(name = "jedisConnectionFactory")
	public JedisConnectionFactory redisConnectionFactory() {
		JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();

		redisConnectionFactory.setHostName(HOST);
		redisConnectionFactory.setPort(PORT);
		redisConnectionFactory.setPassword(PASSWORD);
		redisConnectionFactory.setDatabase(DATABASE);
		return redisConnectionFactory;
	}

	@Bean(name = "redisTemplate")
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory cf) {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
		redisTemplate.setConnectionFactory(cf);
		return redisTemplate;
	}
}
