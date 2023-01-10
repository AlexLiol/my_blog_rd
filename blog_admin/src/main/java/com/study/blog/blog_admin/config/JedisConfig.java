package com.study.blog.blog_admin.config;

import java.time.Duration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @ClassName JedisConfig
 * @Description jedis 客户端配置
 * @Author Alex Li
 * @Date 2022/10/1 21:32
 * @Version 1.0
 */
@Configuration
@PropertySource("classpath:config.properties")
@ConfigurationProperties(prefix = "redis")
@Slf4j
@Data
public class JedisConfig {

    private String host;
    private int    port;
    private String password;
    private int    timeout;
    @Value("${redis.pool.max-active}")
    private int    maxActive;
    @Value("${redis.pool.max-wait}")
    private int    maxWait;
    @Value("${redis.pool.max-idle}")
    private int    maxIdle;
    @Value("${redis.pool.min-idle}")
    private int    minIdle;

    @Bean
    public JedisPool redisPoolFactory() {
        try {
            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(maxActive);
            jedisPoolConfig.setMaxWait(Duration.ofMillis(maxWait));
            jedisPoolConfig.setMaxIdle(maxIdle);
            jedisPoolConfig.setMinIdle(minIdle);
            jedisPoolConfig.setJmxEnabled(false);
            if (StringUtils.isBlank(password)) {
                password = null;
            }
            JedisPool jedisPool = new JedisPool(jedisPoolConfig, host, port, timeout, password);
            log.info("初始化Redis连接池jedisPool成功 host:{}, port:{}", host, port);
            return jedisPool;
        } catch (Exception e) {
            log.error("初始化Redis连接池jedisPool异常:{}", e.getMessage());
        }
        return null;
    }
}
