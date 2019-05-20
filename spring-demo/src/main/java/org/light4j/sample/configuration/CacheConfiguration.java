package org.light4j.sample.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.lang.reflect.Method;
import java.time.Duration;

@EnableCaching
@Configuration
public class CacheConfiguration extends CachingConfigurerSupport {

    /**
     * 采用 RedisCacheManager 作为缓存管理器
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1));// 设置缓存有效期1h
        return RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }


    /**
     * 自定义生成Key的规则
     *
     * @return
     */
    @Override
    public KeyGenerator keyGenerator() {
//        return super.keyGenerator();
        return new KeyGenerator() {
            @Override
            public Object generate(Object o, Method method, Object... objects) {
                // 格式化缓存key字符串
                StringBuilder builder = new StringBuilder();
                // 追加类名
                builder.append(o.getClass().getName());
                // 追加方法名
                builder.append(method.getName());
                // 遍历参数并追加
                for (Object object : objects) {
                    builder.append(object.toString());
                }

                return builder.toString();
            }
        };
    }
}
