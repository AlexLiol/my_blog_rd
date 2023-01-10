package com.study.blog.blog_core.constant;

/**
 * @ClassName UserStatusEnum
 * @Description TODO
 * @Author Alex Li
 * @Date 2022/10/4 09:47
 * @Version 1.0
 */
public enum UserStatusEnum {
    UNKNOWN(0, "未知"), OPEN(1, "启用"), CLOSE(2, "禁用"),
    ;

    private int    status;
    private String desc;

    UserStatusEnum(int status, String desc) {
        this.status = status;
        this.desc   = desc;
    }

    public int getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
