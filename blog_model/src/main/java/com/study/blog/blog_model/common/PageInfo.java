package com.study.blog.blog_model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName PageInfo
 * @Description TODO
 * @Author Alex Li
 * @Date 2022/10/8 22:09
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {

    private long total;

    private int pageSize;
}
