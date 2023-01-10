package com.study.blog.blog_admin.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.study.blog.blog_admin.utils.ResponseBeanUtil;
import com.study.blog.blog_core.constant.ResponseCodeEnum;
import com.study.blog.blog_model.common.ResponseBean;
import com.study.blog.blog_model.exception.CustomException;
import com.study.blog.blog_model.exception.CustomUnauthorizedException;

/**
 * @ClassName ExceptionAdvice
 * @Description 异常控制处理器
 * @Author Alex Li
 * @Date 2022/10/1 21:50
 * @Version 1.0
 */
@RestControllerAdvice
public class ExceptionAdvice {

    /**
     * 捕捉所有的 Shiro 异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResponseBean handle401(ShiroException e) {
        return ResponseBeanUtil.newResponse(HttpStatus.UNAUTHORIZED.value(), "无权访问(Unauthorized):" + e.getMessage());
    }

    /**
     * 单独捕捉 UnauthorizedException 异常，没有权限导致抛出异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseBean handle401(UnauthorizedException e) {
        return ResponseBeanUtil.newResponse(HttpStatus.UNAUTHORIZED.value(),
            "无权访问(Unauthorized):当前Subject没有此请求所需权限(" + e.getMessage() + ")");
    }

    /**
     * 单独捕捉 UnauthenticatedException 异常，该异常为以游客身份访问有权限管控的请求无法对匿名主体进行授权，而授权失败所抛出的异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthenticatedException.class)
    public ResponseBean handle401(UnauthenticatedException e) {
        return ResponseBeanUtil.newResponse(HttpStatus.UNAUTHORIZED.value(),
            "无权访问(Unauthorized):当前Subject是匿名Subject，请先登录(This subject is anonymous.)");
    }

    /**
     * 捕捉 UnauthorizedException 自定义异常
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(CustomUnauthorizedException.class)
    public ResponseBean handle401(CustomUnauthorizedException e) {
        return ResponseBeanUtil.newResponse(HttpStatus.UNAUTHORIZED.value(), "无权访问(Unauthorized):" + e.getMessage());
    }

    /**
     * 捕捉校验异常(BindException)
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseBean validException(BindException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, Object> result = this.getValidError(fieldErrors);
        return ResponseBeanUtil.newResponse(HttpStatus.BAD_REQUEST.value(), result.get("errorMsg").toString(),
            result.get("errorList"));
    }

    /**
     * 捕捉校验异常(MethodArgumentNotValidException)
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseBean validException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        Map<String, Object> result = this.getValidError(fieldErrors);
        return ResponseBeanUtil.newResponse(HttpStatus.BAD_REQUEST.value(), result.get("errorMsg").toString(),
            result.get("errorList"));
    }

    /**
     * 捕捉其他所有自定义异常
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomException.class)
    public ResponseBean handle(CustomException e) {
        return ResponseBeanUtil.buildResponseByCode(ResponseCodeEnum.SERVER_ERROR);
    }

    /**
     * 捕捉 404 异常
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseBean handle(NoHandlerFoundException e) {
        return ResponseBeanUtil.newResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    /**
     * 捕捉其他所有异常
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseBean globalException(HttpServletRequest request, Throwable ex) {
        return ResponseBeanUtil.newResponse(getStatus(request).value(), ex.toString() + ":" + ex.getMessage());
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (null == statusCode) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    private Map<String, Object> getValidError(List<FieldError> fieldErrors) {
        Map<String, Object> result = new HashMap<>(16);
        List<String> errorList = new ArrayList<>();
        StringBuffer errorMsg = new StringBuffer("校验异常(ValidExcetion):");
        for (FieldError fieldError : fieldErrors) {
            errorList.add(fieldError.getField() + "-" + fieldError.getDefaultMessage());
            errorMsg.append(fieldError.getField()).append("-").append(fieldError.getDefaultMessage()).append(".");
        }
        result.put("errorList", errorList);
        result.put("errorMsg", errorMsg);
        return result;
    }
}
