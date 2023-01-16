package com.study.blog.blog_core.constant;

/**
 * @ClassName Constants
 * @Description 常量
 * @Author Alex Li
 * @Date 2022/10/1 19:47
 * @Version 1.0
 */
public class Constants {

    /**
     * jwt token 有效期时间,毫秒
     */
    public static final long JWT_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24;

    /**
     * jwt token prefix
     */
    public static final String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * JWT - username
     */
    public static final String USERNAME = "username";

    /**
     * JWT-currentTimeMillis:当前时间戳，用于 jwtToken 校验
     */
    public static final String CURRENT_TIME_MILLIS        = "currentTimeMillis";
    /**
     * redis shiro-refresh_token key 前缀
     */
    public static final String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";
    /**
     * redis shiro:cache: 前缀
     */
    public static final String PREFIX_SHIRO_CACHE         = "shiro:cache:";
    /**
     * redis 设置返回结果
     */
    public static final String OK                         = "OK";

    /**
     * 用户名/密码最大长度
     */
    public static final int USERNAME_PASSWORD_MAX_LENGTH = 32;

    /**
     * 用户名/密码最小长度
     */
    public static final int USERNAME_PASSWORD_MIN_LENGTH = 8;

}
