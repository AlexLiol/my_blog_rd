package com.study.blog.blog_service.service.service;

import java.util.List;

import com.study.blog.blog_model.pojo.UserRole;
import com.study.blog.blog_service.service.IBaseService;

/**
 * <p>
 * 用户角色关联表 服务类
 * </p>
 *
 * @author Alex Li
 * @since 2022-10-03
 */
public interface IUserRoleService extends IBaseService<UserRole> {

    /**
     * 根据用户ID获取角色ID列表
     */
    List<Long> getRoleIdsByUserId(Long userId);
}
