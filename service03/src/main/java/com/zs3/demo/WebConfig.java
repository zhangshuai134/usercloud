package com.zs3.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@SpringBootApplication
@Slf4j
public class WebConfig {

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) throws Exception {
        String keySerialize = "org.springframework.data.redis.serializer.StringRedisSerializer";
        String valueSerialize = "org.springframework.data.redis.serializer.JdkSerializationRedisSerializer";
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer((RedisSerializer) Class.forName(keySerialize).newInstance());
        redisTemplate.setValueSerializer((RedisSerializer) Class.forName(valueSerialize).newInstance());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
