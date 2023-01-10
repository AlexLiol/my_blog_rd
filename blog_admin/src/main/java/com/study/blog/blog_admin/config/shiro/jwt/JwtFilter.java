package com.study.blog.blog_admin.config.shiro.jwt;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.study.blog.blog_admin.utils.JedisUtil;
import com.study.blog.blog_admin.utils.JwtUtil;
import com.study.blog.blog_admin.utils.ResponseBeanUtil;
import com.study.blog.blog_core.constant.Constants;
import com.study.blog.blog_core.utils.JsonConvertUtil;
import com.study.blog.blog_core.utils.PropertiesUtil;
import com.study.blog.blog_model.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName JwtFilter
 * @Description JWT 过滤器
 * @Author Alex Li
 * @Date 2022/10/1 22:43
 * @Version 1.0
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * 这里讲述下为什么默认返回 true=允许访问 例如提供一个地址 GET /article 登入用户和游客看到的内容是不同的 如果在这里返回了 false,请求会被直接拦截，用户看不到任何动态
     * 所以我们在这里返回true，Controller中可以通过 subject.isAuthenticated() 来判断用户是否登入
     * 如果有些资源只有登入用户才能访问，我们只需要在方法上面加上 @RequiresAuthentication 注解即可
     * 但是这样做有一个缺点，就是不能够对GET,POST等请求进行分别过滤鉴权(因为我们重写了官方的方法)，但实际上对应用影响不大
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        // 查看当前Header中是否携带Authorization属性(Token)，有的话就进行登录认证授权
        if (this.isLoginAttempt(request, response)) {
            try {
                this.executeLogin(request, response);
            } catch (Exception e) {
                // 认证出现异常，传递错误信息
                String msg = e.getMessage();
                Throwable throwable = e.getCause();
                if (throwable instanceof SignatureVerificationException) {
                    // 该异常为JWT的AccessToken认证失败(Token或者密钥不正确)
                    msg = "Token 或者密钥不正确(" + throwable.getMessage() + ")";
                } else if (throwable instanceof TokenExpiredException) {
                    // 该异常为JWT的AccessToken已过期，判断RefreshToken未过期就进行AccessToken刷新
                    if (this.refreshToken(request, response)) {
                        return true;
                    } else {
                        msg = "Token 已过期(" + throwable.getMessage() + ")";
                    }
                } else {
                    if (throwable != null) {
                        msg = throwable.getMessage();
                    }
                }
                response401(response, msg);
                return false;
            }
        } else {
            // 未携带 token
            HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
            String requestMethod = httpServletRequest.getMethod();
            String requestURI = httpServletRequest.getRequestURI();
            log.info("当前请求 {} Authorization属性(Token)为空 请求类型 {}", requestURI, requestMethod);
            final boolean mustLoginFlag = false;
            // mustLoginFlag = true; // 开启任何请求必须登录才能访问
            if (mustLoginFlag) {
                response401(response, "请先登录");
                return false;
            }
        }
        return true;
    }

    /**
     * 可以对比父类方法，只是将 executeLogin 方法调用去除了 如果没有去除将会循环调用 doGetAuthenticationInfo 方法
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        this.sendChallenge(request, response);
        return false;
    }

    /**
     * 检测Header里面是否包含Authorization字段，有就进行Token登录认证授权
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
        String token = this.getAuthzHeader(request);
        return token != null;
    }

    /**
     * 进行AccessToken登录认证授权
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
        JwtToken jwtToken = new JwtToken(this.getAuthzHeader(request));
        // 提交给UserRealm进行认证，如果错误他会抛出异常并被捕获
        this.getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 预处理
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        // 跨域已经在OriginFilter处全局配置
        /*
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        */
        return super.preHandle(request, response);
    }

    private boolean refreshToken(ServletRequest request, ServletResponse response) {
        // 拿到当前Header中Authorization的AccessToken(Shiro中getAuthzHeader方法已经实现)
        String token = this.getAuthzHeader(request);
        // 获取 token 中当前账号信息
        String username = JwtUtil.getClaim(token, Constants.USERNAME);
        // 判断 redis 中 refreshToken 是否存在
        if (JedisUtil.exists(Constants.PREFIX_SHIRO_REFRESH_TOKEN + username)) {
            // Redis 中 RefreshToken 还存在，获取 RefreshToken 的时间戳
            String currentTimeMillsRedis =
                Objects.requireNonNull(JedisUtil.getObject(Constants.PREFIX_SHIRO_REFRESH_TOKEN + username)).toString();
            // 获取当前 AccessToken 中的时间戳，与 RefreshToken 的时间戳对比，如果当前时间戳一致，进行 AccessToken 刷新
            if (JwtUtil.getClaim(token, Constants.CURRENT_TIME_MILLIS).equals(currentTimeMillsRedis)) {
                // 获取当前最新的时间戳
                String currentTimeMills = String.valueOf(System.currentTimeMillis());
                // 读取配置文件，获取 refreshTokenExpireTime 属性
                PropertiesUtil.readProperties("config.properties");
                String refreshTokenExpireTime = PropertiesUtil.getProperty("refreshTokenExpireTime");
                // 设置 RefreshToken 中的时间戳为当前最新时间戳，且刷新过期时间重新为 30min
                JedisUtil.setObject(Constants.PREFIX_SHIRO_REFRESH_TOKEN + username, currentTimeMills,
                    Integer.parseInt(refreshTokenExpireTime));
                // 刷新 AccessToken，设置时间戳为当前时间戳
                token = JwtUtil.sign(username, currentTimeMills);
                // 将新刷新后的 token 再次进行 Shiro 的登录
                JwtToken jwtToken = new JwtToken(token);
                // 提交给 UserRealm 进行认证，如果报错会抛出异常并被捕获，如果没有抛出异常则代表登录成功，返回 true
                this.getSubject(request, response).login(jwtToken);
                // 最后将刷新后的 token 存放到 response 的 header 中的 Authorization 字段中返回
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setHeader("Authorization", token);
                httpServletResponse.setHeader("Access-Control-Expose-Headers", "Authorization");
                return true;
            }
        }
        return false;
    }

    private void response401(ServletResponse response, String msg) {
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = httpServletResponse.getWriter()) {
            String data = JsonConvertUtil
                .object2Json(ResponseBeanUtil.newResponse(HttpStatus.UNAUTHORIZED.value(), "无权访问(Unauthorized):" + msg));
            out.append(data);
        } catch (IOException e) {
            log.error("直接返回 response 信息出现 IOException:{}", e.getMessage());
            throw new CustomException("直接返回 response 信息出现 IOException:" + e.getMessage());
        }
    }
}
