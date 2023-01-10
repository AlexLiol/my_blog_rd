package com.study.blog.blog_model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName ResponseBean
 * @Description TODO
 * @Author Alex Li
 * @Date 2022/10/1 21:55
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBean {

    private Integer code;

    private String msg;

    private Object data;

}
