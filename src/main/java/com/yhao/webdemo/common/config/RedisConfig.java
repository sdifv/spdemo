package com.yhao.webdemo.common.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;


@EnableCaching  // 后续方法上可以基于注解实现缓存
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * Redis本身提供了一下一种序列化的方式：
     * GenericToStringSerializer: 可以将任何对象泛化为字符串并序列化
     * Jackson2JsonRedisSerializer: 跟JacksonJsonRedisSerializer实际上是一样的
     * JacksonJsonRedisSerializer: 序列化object对象为json字符串
     * JdkSerializationRedisSerializer: 序列化java对象
     * StringRedisSerializer: 简单的字符串序列化
     */

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // 1. 设置key的序列化方式
        redisTemplate.setKeySerializer(stringRedisSerializer);

        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        // 1. 设置序列化时的可见性。
        //    第一个参数是选择序列化哪些属性，比如是序列化setter?还是filed
        //    第二个参数是选择哪些修饰符权限的属性来序列化，比如private或者public
        //    这里的any是指对所有权限修饰的属性都可见(可序列化)
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 2. 设置出现故障即错误的类型
        //    第一个是指验证程序，此时的参数为无需验证，其他参数可以查看源码了解
        //    第二是指该类不能为final修饰，否则将会报错
        mapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jsonRedisSerializer.setObjectMapper(mapper);

        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        return redisTemplate;
    }
}
