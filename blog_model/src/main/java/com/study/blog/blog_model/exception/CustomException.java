package com.study.blog.blog_model.exception;

/**
 * @ClassName CustomException
 * @Description TODO
 * @Author Alex Li
 * @Date 2022/10/1 22:21
 * @Version 1.0
 */
public class CustomException extends RuntimeException {

    public CustomException(String msg) {
        super(msg);
    }

    public CustomException() {
        super();
    }
}
