package com.study.blog.blog_core.constant;

/**
 * @ClassName ResponseCodeEnum
 * @Description 响应码枚举
 * @Author Alex Li
 * @Date 2022/10/1 21:58
 * @Version 1.0
 */
public enum ResponseCodeEnum {
    /**
     * 成功
     */
    SUCCESS(200, "请求成功"),
    SERVER_ERROR(100000, "系统异常"),
    PARAM_ERROR(100001, "参数错误"),

    REPEAT_PASSWORD_NOT_EQUAL(110000, "重复密码不一致"),
    USERNAME_LENGTH_ERROR(110001, "用户名长度应在8~32位之间"),
    PASSWORD_LENGTH_ERROR(110002, "密码长度应在8~32位之间"),
    USERNAME_OR_PASSWORD_NOT_BLANK_ERROR(110003, "用户名或密码不能为空"),
    EMAIL_PATTERN_ERROR(110004, "邮件格式不正确"),
    MOBILE_PATTERN_ERROR(110005, "手机号格式不正确"),
    USER_REPEATED_ERROR(110006, "用户名已存在"),
    USER_NOT_EXISTS_ERROR(110007, "用户不存在"),

    ;

    /**
     * 响应码
     */
    private final int    code;
    /**
     * 响应信息
     */
    private final String msg;

    ResponseCodeEnum(int code, String msg) {
        this.code = code;
        this.msg  = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
