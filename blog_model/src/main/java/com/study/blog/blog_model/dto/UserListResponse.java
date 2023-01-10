package com.study.blog.blog_model.dto;

import java.util.List;

import com.study.blog.blog_model.common.PageInfo;
import com.study.blog.blog_model.pojo.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName UserListResponse
 * @Description TODO
 * @Author Alex Li
 * @Date 2022/10/8 22:14
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListResponse {

    private PageInfo   pageInfo;
    private List<User> userList;
}
