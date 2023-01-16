package com.study.blog.blog_core.constant;

/**
 * @ClassName ResponseCodeEnum
 * @Description 响应码枚举
 * @Author Alex Li
 * @Date 2022/10/1 21:58
 * @Version 1.0
 */
public enum AdminResponseCodeEnum {
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
    PERMISSION_NAME_EMPTY_ERROR(110008, "权限名称不能为空"),
    PERMISSION_DESCRIPTION_EMPTY_ERROR(110009, "权限描述不能为空"),
    PERMISSION_TYPE_NOT_EXIST_ERROR(110010, "权限类型不存在"),
    PERMISSION_PARENT_ID_NOT_EXIST_ERROR(110011, "权限的父级权限不存在"),
    PERMISSION_EXISTS_ERROR(110012, "权限已存在"),
    PERMISSION_ID_NOT_EXIST_ERROR(110013, "权限ID不存在"),
    PERMISSION_ADD_FAIL_ERROR(110014, "权限添加失败"),
    PERMISSION_UPDATE_FAIL_ERROR(110015, "权限更新失败"),
    PERMISSION_DELETE_FAIL_ERROR(110015, "权限删除失败"),
    USER_ADD_FAIL_ERROR(110016, "用户添加失败"),
    USER_UPDATE_FAIL_ERROR(110017, "用户更新失败"),
    USER_DELETE_FAIL_ERROR(110018, "用户删除失败"),
    USER_STATUS_NOT_EXIST_ERROR(110019, "用户状态不存在"),
    USER_STATUS_UPDATE_FAIL_ERROR(110020, "用户状态更新失败"),
    USER_DELETE_YOURSELF_ERROR(110021, "用户不能删除自己"),
    ROLE_NAME_EMPTY_ERROR(110022, "角色名称不能为空"),
    ROLE_DESCRIPTION_EMPTY_ERROR(110023, "角色描述不能为空"),
    ROLE_ADD_FAIL_ERROR(110024, "角色添加失败"),
    ROLE_UPDATE_FAIL_ERROR(110025, "角色更新失败"),
    ROLE_DELETE_FAIL_ERROR(110026, "角色删除失败"),
    ROLE_EXISTS_ERROR(110027, "角色已存在"),
    DELETE_USER_ROLES_ERROR(110028, "删除用户角色失败"),
    ADD_USER_ROLES_ERROR(110029, "添加用户角色失败"),
    DELETE_ROLE_PERMISSIONS_ERROR(110030, "删除用户角色失败"),
    ADD_ROLE_PERMISSIONS_ERROR(110031, "添加角色权限失败"),
    ;

    /**
     * 响应码
     */
    private final int    code;
    /**
     * 响应信息
     */
    private final String msg;

    AdminResponseCodeEnum(int code, String msg) {
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
