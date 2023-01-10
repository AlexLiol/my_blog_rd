package com.study.blog.blog_admin.config.shiro.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import com.study.blog.blog_admin.utils.JedisUtil;
import com.study.blog.blog_admin.utils.JwtUtil;
import com.study.blog.blog_core.constant.Constants;
import com.study.blog.blog_core.utils.PropertiesUtil;
import com.study.blog.blog_core.utils.SerializableUtil;

/**
 * @ClassName CustomCache
 * @Description 自定义 shiro 缓存
 * @Author Alex Li
 * @Date 2022/10/2 23:52
 * @Version 1.0
 */
public class CustomCache<K, V> implements Cache<K, V> {

    /**
     * 获取缓存 key
     */
    private String getKey(Object key) {
        return Constants.PREFIX_SHIRO_CACHE + JwtUtil.getClaim(key.toString(), Constants.USERNAME);
    }

    @Override
    public Object get(Object key) throws CacheException {
        if (Boolean.FALSE.equals(JedisUtil.exists(this.getKey(key)))) {
            return null;
        }
        return JedisUtil.getObject(this.getKey(key));
    }

    @Override
    public Object put(Object k, Object v) throws CacheException {
        PropertiesUtil.readProperties("config.properties");
        String shiroCacheExpireTime = PropertiesUtil.getProperty("shiroCacheExpireTime");
        return JedisUtil.setObject(this.getKey(k), v, Integer.parseInt(shiroCacheExpireTime));
    }

    @Override
    public Object remove(Object k) throws CacheException {
        if (Boolean.FALSE.equals(JedisUtil.exists(this.getKey(k)))) {
            return null;
        }
        return JedisUtil.delKey(this.getKey(k));
    }

    /**
     * 清空所有的缓存，慎用！防止清除不能清除的数据
     */
    @Override
    public void clear() throws CacheException {
        Objects.requireNonNull(JedisUtil.getJedis()).flushDB();
    }

    /**
     * Redis DB 大小，慎用！
     */
    @Override
    public int size() {
        Long size = Objects.requireNonNull(JedisUtil.getJedis()).dbSize();
        return size.intValue();
    }

    /**
     * 获取所有的 key
     */
    @Override
    public Set keys() {
        Set<byte[]> keys = Objects.requireNonNull(JedisUtil.getJedis()).keys("*".getBytes());
        Set<Object> set = new HashSet<>();
        for (byte[] bytes : keys) {
            set.add(SerializableUtil.unserializable(bytes));
        }
        return set;
    }

    /**
     * 获取所有的 value
     */
    @Override
    public Collection values() {
        Set keys = this.keys();
        List<Object> values = new ArrayList<>();
        for (Object key : keys) {
            values.add(JedisUtil.getObject(this.getKey(key)));
        }
        return values;
    }
}
