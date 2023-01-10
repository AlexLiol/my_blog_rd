package com.study.blog.blog_model.exception;

/**
 * @ClassName CustomUnauthorizedException
 * @Description TODO
 * @Author Alex Li
 * @Date 2022/10/1 22:21
 * @Version 1.0
 */
public class CustomUnauthorizedException extends RuntimeException {

    public CustomUnauthorizedException(String msg) {
        super(msg);
    }

    public CustomUnauthorizedException() {
        super();
    }
}
