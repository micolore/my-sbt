package com.kubrick.sbt.cache.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author k
 * @version 1.0.0
 * @ClassName RedissonConfig
 * @description: TODO
 * @date 2020/12/29 上午12:49
 */
@Configuration
public class RedissonConfig {

	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();
		config.useSingleServer().setAddress("redis://localhost:6379");

		RedissonClient redisson = Redisson.create(config);

		return redisson;
	}

}
