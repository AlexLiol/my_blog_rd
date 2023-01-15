package com.study.blog.blog_model.dto;

import com.study.blog.blog_model.pojo.Permission;

/**
 * @ClassName PermissionDto
 * @Description TODO
 * @Author Alex Li
 * @Date 2023/1/15 16:43
 * @Version 1.0
 */
public class PermissionDto extends Permission {

    public static Permission transformUserDto(PermissionDto permissionDto) {
        Permission permission = new Permission();
        permission.setId(permissionDto.getId());
        permission.setName(permissionDto.getName());
        permission.setDescription(permissionDto.getDescription());
        permission.setType(permissionDto.getType());
        permission.setParentId(permissionDto.getParentId());
        return permission;
    }
}
