package com.study.blog.blog_admin.utils;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.study.blog.blog_core.constant.Constants;
import com.study.blog.blog_model.pojo.User;
import com.study.blog.blog_service.service.service.IUserService;

/**
 * @ClassName UserUtil
 * @Description 获取当前用户的工具类
 * @Author Alex Li
 * @Date 2022/10/3 19:44
 * @Version 1.0
 */
@Component
public class UserUtil {

    private final IUserService userService;

    @Autowired
    public UserUtil(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 获取当前登录用户
     */
    public User getUser() {
        String token = SecurityUtils.getSubject().getPrincipal().toString();
        // 获取 username
        String username = JwtUtil.getClaim(token, Constants.USERNAME);
        return null;
    }
}
