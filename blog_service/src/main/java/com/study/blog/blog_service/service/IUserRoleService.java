package com.study.blog.blog_service.service;

import java.util.List;

import com.study.blog.blog_model.pojo.UserRole;

/**
 * <p>
 * 用户角色关联表 服务类
 * </p>
 *
 * @author Alex Li
 * @since 2023-01-14
 */
public interface IUserRoleService extends IBaseService<UserRole> {

    /**
     * 根据用户ID获取角色ID列表
     */
    List<Long> getRoleIdsByUserId(Long userId);

    boolean removeByUserId(Long id);
}
