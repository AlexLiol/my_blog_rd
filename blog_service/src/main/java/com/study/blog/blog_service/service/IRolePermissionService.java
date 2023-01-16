package com.study.blog.blog_service.service;

import java.util.List;

import com.study.blog.blog_model.pojo.RolePermission;

/**
 * <p>
 * 角色权限关联表 服务类
 * </p>
 *
 * @author Alex Li
 * @since 2023-01-14
 */
public interface IRolePermissionService extends IBaseService<RolePermission> {

    /**
     * 根据角色ID列表获取权限ID列表
     */
    List<Long> getPermissionIdsByRoleIds(List<Long> roleIds);

    boolean removeByRoleId(Long roleId);
}
