package com.study.blog.blog_model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName RolePermissionConfigDto
 * @Description TODO
 * @Author Alex Li
 * @Date 2023/1/16 19:20
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionConfigDto {

    private long roleId;

    private List<Long> permissionIds;

}
