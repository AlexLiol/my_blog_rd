package com.study.blog.blog_core.constant;

/**
 * @ClassName DeleteEnum
 * @Description TODO
 * @Author Alex Li
 * @Date 2022/10/4 09:45
 * @Version 1.0
 */
public enum DeleteStatusEnum {
    UN_DELETE(Boolean.FALSE, "未删除"),
    DELETED(Boolean.TRUE, "已删除"),
    ;

    private boolean status;

    private String desc;

    DeleteStatusEnum(boolean delete, String desc) {
        this.status = delete;
        this.desc   = desc;
    }

    public boolean getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }
}
