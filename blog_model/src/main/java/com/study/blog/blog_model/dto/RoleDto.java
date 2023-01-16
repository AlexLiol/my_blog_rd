package com.study.blog.blog_model.dto;

import java.util.ArrayList;
import java.util.List;

import com.study.blog.blog_model.pojo.Role;
import com.study.blog.blog_model.pojo.RolePermission;

import lombok.Data;

/**
 * @ClassName RoleDto
 * @Description TODO
 * @Author Alex Li
 * @Date 2023/1/16 16:39
 * @Version 1.0
 */
@Data
public class RoleDto extends Role {

    private List<Long> permissionIds;

    public static Role transformRole(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setName(roleDto.getName());
        role.setDescription(roleDto.getDescription());
        return role;
    }

    public static List<RolePermission> transformRolePermissions(Long roleId, List<Long> permissionIds) {
        List<RolePermission> rolePermissions = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissions.add(rolePermission);
        }
        return rolePermissions;
    }
}
