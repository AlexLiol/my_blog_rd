package com.study.blog.blog_admin.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.study.blog.blog_core.constant.Constants;
import com.study.blog.blog_core.utils.Base64ConvertUtil;
import com.study.blog.blog_model.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName JwtUtil
 * @Description TODO
 * @Author Alex Li
 * @Date 2022/10/1 22:57
 * @Version 1.0
 */
@Slf4j
@Configuration
@PropertySource("classpath:config.properties")
public class JwtUtil {

    /**
     * token 过期时间，配置文件中拿
     */
    private static String accessTokenExpireTime;

    /**
     * jwt 认证加密私钥(base64 加密)
     */
    private static String encryptKey;

    private static String refreshTokenExpireTime;

    /**
     * 校验 token 是否正确
     */
    public static boolean verify(String token) {
        try {
            String secret = getClaim(token, Constants.USERNAME) + Base64ConvertUtil.decode(encryptKey);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            jwtVerifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            log.error("JwtToken 认证解密出现 UnsupportedEncodingException 异常:{}", e.getMessage());
            throw new CustomException("JwtToken 认证解密出现 UnsupportedEncodingException 异常:" + e.getMessage());
        } catch (TokenExpiredException e) {
            log.info("JWTToken 过期，需要重置");
            throw e;
        }
    }

    /**
     * 获取 token 中的信息无需 secret 解密也可获得
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            // 只能输出 String 类型，若是其他类型返回 null
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            log.error("解密 token 中的公共信息出现 JWTDecodeException:{}", e.getMessage());
            throw new CustomException(
                "解密 token 中的公共信息出现 JWTDecodeException:" + e.getMessage() + ",token:" + token + ",claim:" + claim);
        }
    }

    public static String sign(String username, String currentTimeMills) {
        try {
            // 账号加 JWT 私钥加密
            String secret = username + Base64ConvertUtil.decode(encryptKey);
            // 此处过期时间以毫秒为单位，因此要 * 1000
            Date date = new Date(System.currentTimeMillis() + Long.parseLong(accessTokenExpireTime) * 1000);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create().withClaim(Constants.USERNAME, username)
                .withClaim(Constants.CURRENT_TIME_MILLIS, currentTimeMills).withIssuedAt(new Date()).withExpiresAt(date)
                .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            log.error("JWTToken 加密出现 UnsupportedEncodingException:{}", e.getMessage());
            throw new CustomException("JWTToken 加密出现 UnsupportedEncodingException:" + e.getMessage());
        } catch (Exception e) {
            log.error("JWTToken 签名出现错误:{}", e.getMessage());
            throw new CustomException("JWTToken 签名出现错误:" + e.getMessage());
        }
    }

    public static String genToken(String username) {
        // 清除可能存在的 shiro 权限信息缓存
        String cacheKey = Constants.PREFIX_SHIRO_CACHE + username;
        if (JedisUtil.exists(cacheKey)) {
            JedisUtil.delKey(cacheKey);
        }
        // 设置 RefreshToken，时间戳为当前时间戳，直接设置即可
        String currentTimeMills = String.valueOf(System.currentTimeMillis());
        String refreshTokenKey = Constants.PREFIX_SHIRO_REFRESH_TOKEN + username;
        JedisUtil.setObject(refreshTokenKey, currentTimeMills, Integer.parseInt(refreshTokenExpireTime));
        // 从Header中Authorization返回AccessToken，时间戳为当前时间戳
        return JwtUtil.sign(username, currentTimeMills);
    }

    @Value("${accessTokenExpireTime}")
    public void setAccessTokenExpireTime(String accessTokenExpireTime) {
        JwtUtil.accessTokenExpireTime = accessTokenExpireTime;
    }

    @Value("${encryptJWTKey}")
    public void setEncryptKey(String encryptKey) {
        JwtUtil.encryptKey = encryptKey;
    }

    @Value("${refreshTokenExpireTime}")
    public void setRefreshTokenExpireTime(String refreshTokenExpireTime) {
        JwtUtil.refreshTokenExpireTime = refreshTokenExpireTime;
    }
}
