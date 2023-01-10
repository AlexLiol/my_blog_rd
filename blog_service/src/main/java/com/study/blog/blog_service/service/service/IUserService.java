package com.study.blog.blog_service.service.service;

import java.util.List;

import com.study.blog.blog_model.pojo.User;
import com.study.blog.blog_service.service.IBaseService;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Alex Li
 * @since 2022-10-03
 */
public interface IUserService extends IBaseService<User> {

    /**
     * 根据用户名获取用户
     */
    User getByUsername(String username);

    /**
     * 根据用户名获取用户除去ID
     */
    User getByUsername(String username, Long exceptId);

    /**
     * 根据条件查询用户数量
     */
    Long getCountByCond(Long id, String username, Integer status, Boolean isDelete);

    /**
     * 根据条件分页查询用户
     */
    List<User> getUserByCond(Long id, String username, Integer status, Boolean isDelete, Integer page,
                             Integer pageSize);
}
