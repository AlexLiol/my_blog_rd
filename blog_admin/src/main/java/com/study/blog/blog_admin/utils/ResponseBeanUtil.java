package com.study.blog.blog_admin.utils;

import com.study.blog.blog_core.constant.ResponseCodeEnum;
import com.study.blog.blog_model.common.ResponseBean;

/**
 * @ClassName ResponseUtil
 * @Description TODO
 * @Author Alex Li
 * @Date 2022/10/1 21:57
 * @Version 1.0
 */
public class ResponseBeanUtil {

    public static ResponseBean newResponse(int code, String msg, Object data) {
        return new ResponseBean(code, msg, data);
    }

    public static ResponseBean newResponse(int code, String msg) {
        return new ResponseBean(code, msg, null);
    }

    public static ResponseBean buildResponseByCode(ResponseCodeEnum codeEnum, Object data) {
        return newResponse(codeEnum.getCode(), codeEnum.getMsg(), data);
    }

    public static ResponseBean buildResponseByCode(ResponseCodeEnum codeEnum) {
        return buildResponseByCode(codeEnum, null);
    }

    public static ResponseBean buildSuccessResponse(Object data) {
        return buildResponseByCode(ResponseCodeEnum.SUCCESS, data);
    }

    public static ResponseBean buildSuccessResponse() {
        return buildSuccessResponse(null);
    }
}
