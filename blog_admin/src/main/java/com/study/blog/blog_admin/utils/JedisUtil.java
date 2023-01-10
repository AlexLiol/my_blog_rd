package com.study.blog.blog_admin.utils;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.study.blog.blog_core.constant.Constants;
import com.study.blog.blog_core.utils.SerializableUtil;
import com.study.blog.blog_core.utils.StringUtil;
import com.study.blog.blog_model.exception.CustomException;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @ClassName JedisUtil
 * @Description redis 客户端工具类
 * @Author Alex Li
 * @Date 2022/10/2 16:49
 * @Version 1.0
 */
@Component
public class JedisUtil {

    /**
     * 静态注入 JedisPool 连接池 本来是正常注入JedisUtil，可以在Controller和Service层使用，但是重写Shiro的CustomCache无法注入JedisUtil
     * 现在改为静态注入JedisPool连接池，JedisUtil直接调用静态方法即可
     */
    private static JedisPool jedisPool;

    /**
     * 获取 Redis 实例
     */
    public static synchronized Jedis getJedis() {
        try {
            if (jedisPool != null) {
                return jedisPool.getResource();
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new CustomException("获取 Jedis 资源异常:" + e.getMessage());
        }
    }

    /**
     * 释放 jedis 资源
     */
    public static void closePool() {
        try {
            jedisPool.close();
        } catch (Exception e) {
            throw new CustomException("释放 Jedis 资源异常:" + e.getMessage());
        }
    }

    /**
     * 获取 Redis 键值 - object
     */
    public static Object getObject(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            byte[] bytes = jedis.get(key.getBytes());
            if (StringUtil.isNotNull(bytes)) {
                return SerializableUtil.unserializable(bytes);
            }
        } catch (Exception e) {
            throw new CustomException("获取 Redis 键值 getObject 方法异常:key=" + key + " cause=" + e.getMessage());
        }
        return null;
    }

    /**
     * 设置 Redis 键值对 -object -expiretime
     */
    public static String setObject(String key, Object value, int expireTime) {
        String result;
        try (Jedis jedis = jedisPool.getResource()) {
            result = jedis.set(key.getBytes(), SerializableUtil.serializable(value));
            if (Constants.OK.equals(result)) {
                jedis.expire(key.getBytes(), expireTime);
            }
            return result;
        } catch (Exception e) {
            throw new CustomException("设置 redis 键值对 setObject 方法异常:key=" + key + " value=" + value + " expireTime="
                + expireTime + " cause=" + e.getMessage());
        }
    }

    /**
     * 删除 key
     */
    public static Long delKey(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.del(key.getBytes());
        } catch (Exception e) {
            throw new CustomException("删除 redis 的键 delKey 方法异常，key=" + key + " cause=" + e.getMessage());
        }
    }

    /**
     * key 是否存在
     */
    public static Boolean exists(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key.getBytes());
        } catch (Exception e) {
            throw new CustomException("查询 redis 的键是否存在 exists 方法异常，key=" + key + " cause=" + e.getMessage());
        }
    }

    /**
     * 模糊查询获取 key 集合（keys 的速度非常快，但在一个大的数据库中使用它仍然可能造成性能问题，生产不推荐使用）
     */
    public static Set<String> keysS(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.keys(key);
        } catch (Exception e) {
            throw new CustomException("模糊查询 redis 的键集合 keysS 方法异常，key=" + key + " cause=" + e.getMessage());
        }
    }

    @Autowired
    public void setJedisPool(JedisPool jedisPool) {
        JedisUtil.jedisPool = jedisPool;
    }

}
