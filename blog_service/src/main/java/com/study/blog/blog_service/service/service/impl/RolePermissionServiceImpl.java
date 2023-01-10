package com.study.blog.blog_service.service.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.blog_core.constant.DeleteStatusEnum;
import com.study.blog.blog_dao.mapper.RolePermissionMapper;
import com.study.blog.blog_model.pojo.RolePermission;
import com.study.blog.blog_service.service.service.IRolePermissionService;

/**
 * <p>
 * 角色权限关联表 服务实现类
 * </p>
 *
 * @author Alex Li
 * @since 2022-10-03
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
    implements IRolePermissionService {

    @Override
    public List<Long> getPermissionIdsByRoleIds(List<Long> roleIds) {
        QueryWrapper<RolePermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_delete", DeleteStatusEnum.UN_DELETE.getStatus()).in("role_id", roleIds);
        List<RolePermission> rolePermissions = this.list(queryWrapper);
        return rolePermissions.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());
    }
}
