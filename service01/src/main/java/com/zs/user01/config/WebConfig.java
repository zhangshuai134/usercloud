package com.zs.user01.config;

import com.zs.user01.shiro.MessageSubscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

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

    /**
     * 文件上传配置
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //文件最大
        factory.setMaxFileSize(DataSize.ofMegabytes(20L));
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.ofMegabytes(20L));
        factory.setLocation("/");
        return factory.createMultipartConfig();
    }

    /**
     * 消息监听器，使用MessageAdapter可实现自动化解码及方法代理
     *
     * @return
     */
    @Bean
    public MessageListenerAdapter listener(MessageSubscriber subscriber) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(subscriber, "onMessage");
        return adapter;
    }


    /*
     * Receiver实例
     */
    @Bean
    public MessageSubscriber subscriber() {
        return new MessageSubscriber();
    }


    /**
     * 将订阅器绑定到容器
     *
     * @param connectionFactory
     * @param listener
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listener) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listener, new PatternTopic("/redis/permission"));
        return container;
    }
}
