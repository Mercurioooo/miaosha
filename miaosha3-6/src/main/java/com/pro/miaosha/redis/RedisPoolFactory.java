package com.pro.miaosha.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
public class RedisPoolFactory {
	@Autowired
	RedisConfig redisConfig;
	@Bean
	public JedisPool JedisFactory() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
		poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
		poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait()*1000);
		//poolconfig,host,port,connectionTimeout(socket用的),密码,数据库个数
		JedisPool jp = new JedisPool(poolConfig, redisConfig.getHost(), redisConfig.getPort(),redisConfig.getTimeout()*1000, redisConfig.getPassword(), 0 );
		return jp;
	}
}
