package com.study.blog.blog_admin.config.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

/**
 * @ClassName CustomCacheManager
 * @Description shiro 缓存管理器
 * @Author Alex Li
 * @Date 2022/10/3 00:04
 * @Version 1.0
 */
public class CustomCacheManager implements CacheManager {

    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new CustomCache<>();
    }
}
