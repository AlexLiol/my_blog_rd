package com.study.blog.blog_service.service;

import com.study.blog.blog_model.pojo.Permission;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author Alex Li
 * @since 2023-01-14
 */
public interface IPermissionService extends IBaseService<Permission> {
    Permission getByName(String name);
}
