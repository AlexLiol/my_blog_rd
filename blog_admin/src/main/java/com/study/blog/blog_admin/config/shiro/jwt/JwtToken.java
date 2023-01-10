package com.study.blog.blog_admin.config.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

import lombok.AllArgsConstructor;

/**
 * @ClassName JwtToken
 * @Description JwtToken
 * @Author Alex Li
 * @Date 2022/10/1 22:43
 * @Version 1.0
 */
@AllArgsConstructor
public class JwtToken implements AuthenticationToken {

    private String token;

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
