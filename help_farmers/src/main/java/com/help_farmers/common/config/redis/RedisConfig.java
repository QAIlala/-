package com.help_farmers.common.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * @Author: WZ
 * @Date: 2023/6/29 17:19
 * @Description:
 */

@Configuration
public class RedisConfig {

    /**
     * String类型的RedisTemplate
     * @param connectionFactory redis 的连接池
     * @return
     */
    @Bean("StringRedisTemplate")
    public RedisTemplate StringRedisTemplate(@Autowired LettuceConnectionFactory connectionFactory){
        // 创建一个RedisTemplate对象，指定了键和值的类型。在这里，键的类型是String，值的类型是Serializable。
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        // 设置键的序列化器为StringRedisSerializer，用于将键序列化为字符串。
        redisTemplate.setKeySerializer(new StringRedisSerializer());//key序列化方式
        // 设置值的序列化器为StringRedisSerializer，用于将值序列化为字符串。
        redisTemplate.setValueSerializer(new StringRedisSerializer());//value序列化
        // 设置hash键的序列化器为StringRedisSerializer，用以将hash键序列化为字符串。
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // 设置哈希值的序列化器为StringRedisSerializer，用于将哈希值序列化为字符串。
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        // 设置RedisTemplate的连接工厂为传入的LettuceConnectionFactory对象。
        redisTemplate.setConnectionFactory(connectionFactory);
        // 返回配置好的RedisTemplate对象。
        return redisTemplate;
    }

    /**
     * 序列化成json的　RedisTemplate
     * @param connectionFactory
     * @return
     */
    @Bean("JsonRedisTemplate")
    public RedisTemplate<String, Serializable> redisTemplate02(@Autowired LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());//key序列化方式
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());//value序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

    /**
     * 序列化成JDK的　RedisTemplate
     * @param connectionFactory
     * @return
     */
    @Bean("JdkRedisTemplate")
    public RedisTemplate<String, Serializable> redisTemplate03(@Autowired LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());//key序列化方式
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());//value序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }

}
