package com.study.blog.blog_admin.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.blog.blog_admin.utils.ResponseBeanUtil;
import com.study.blog.blog_core.constant.AdminResponseCodeEnum;
import com.study.blog.blog_model.common.ResponseBean;
import com.study.blog.blog_model.dto.RoleDto;
import com.study.blog.blog_model.pojo.Role;
import com.study.blog.blog_model.pojo.RolePermission;
import com.study.blog.blog_service.service.IRolePermissionService;
import com.study.blog.blog_service.service.IRoleService;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author Alex Li
 * @since 2023-01-14
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final IRoleService roleService;

    private final IRolePermissionService rolePermissionService;

    @Autowired
    public RoleController(IRoleService roleService, IRolePermissionService rolePermissionService) {
        this.roleService           = roleService;
        this.rolePermissionService = rolePermissionService;
    }

    @RequestMapping("/add")
    public ResponseBean addRole(@RequestBody RoleDto roleDto) {
        AdminResponseCodeEnum adminResponseCodeEnum = preCheckRole(roleDto);
        if (AdminResponseCodeEnum.SUCCESS != adminResponseCodeEnum) {
            return ResponseBeanUtil.buildResponseByCode(adminResponseCodeEnum);
        }
        Role role = RoleDto.transformRole(roleDto);
        role.setCreateTime(LocalDateTime.now());
        role.setUpdateTime(LocalDateTime.now());
        boolean flag = this.roleService.save(role);
        if (!flag) {
            return ResponseBeanUtil.buildResponseByCode(AdminResponseCodeEnum.ROLE_ADD_FAIL_ERROR);
        }
        adminResponseCodeEnum = configRolePermissions(role.getId(), roleDto.getPermissionIds());
        if (AdminResponseCodeEnum.SUCCESS != adminResponseCodeEnum) {
            return ResponseBeanUtil.buildResponseByCode(adminResponseCodeEnum);
        }
        return ResponseBeanUtil.buildSuccessResponse();
    }

    @RequestMapping("/update")
    public ResponseBean updateRole(@RequestBody RoleDto roleDto) {
        AdminResponseCodeEnum adminResponseCodeEnum = preCheckRole(roleDto);
        if (AdminResponseCodeEnum.SUCCESS != adminResponseCodeEnum) {
            return ResponseBeanUtil.buildResponseByCode(adminResponseCodeEnum);
        }
        if (roleDto.getId() <= 0) {
            return ResponseBeanUtil.buildResponseByCode(AdminResponseCodeEnum.PARAM_ERROR);
        }
        Role role = RoleDto.transformRole(roleDto);
        role.setUpdateTime(LocalDateTime.now());
        boolean flag = this.roleService.updateById(role);
        if (!flag) {
            return ResponseBeanUtil.buildResponseByCode(AdminResponseCodeEnum.ROLE_UPDATE_FAIL_ERROR);
        }
        adminResponseCodeEnum = configRolePermissions(role.getId(), roleDto.getPermissionIds());
        if (AdminResponseCodeEnum.SUCCESS != adminResponseCodeEnum) {
            return ResponseBeanUtil.buildResponseByCode(adminResponseCodeEnum);
        }
        return ResponseBeanUtil.buildSuccessResponse();
    }


    @RequestMapping("/delete")
    public ResponseBean deleteRole(@RequestBody Map<String, String> params) {
        long id = Long.parseLong(params.get("id"));
        if (id <= 0) {
            return ResponseBeanUtil.buildResponseByCode(AdminResponseCodeEnum.PARAM_ERROR);
        }
        boolean flag = this.roleService.removeById(id);
        if (!flag) {
            return ResponseBeanUtil.buildResponseByCode(AdminResponseCodeEnum.ROLE_DELETE_FAIL_ERROR);
        }
        // TODO:删除角色与权限的关联关系
        return ResponseBeanUtil.buildSuccessResponse();
    }

    @RequestMapping("/search")
    public ResponseBean searchRole(@RequestBody Map<String, String> params) {

        return ResponseBeanUtil.buildSuccessResponse();
    }


    private AdminResponseCodeEnum preCheckRole(RoleDto roleDto) {
        if (roleDto == null) {
            return AdminResponseCodeEnum.PARAM_ERROR;
        }
        if (StringUtils.isBlank(roleDto.getName())) {
            return AdminResponseCodeEnum.ROLE_NAME_EMPTY_ERROR;
        }
        if (StringUtils.isBlank(roleDto.getDescription())) {
            return AdminResponseCodeEnum.ROLE_DESCRIPTION_EMPTY_ERROR;
        }
        Role role = this.roleService.getByName(roleDto.getName(), roleDto.getId());
        if (role != null) {
            return AdminResponseCodeEnum.ROLE_EXISTS_ERROR;
        }
        return AdminResponseCodeEnum.SUCCESS;
    }

    private AdminResponseCodeEnum configRolePermissions(Long roleId, List<Long> permissionIds) {
        // 删除用户原有角色
        List<Long> existPermissionIds = this.rolePermissionService.getPermissionIdsByRoleIds(new ArrayList<Long>() {{
            add(roleId);
        }});
        if (existPermissionIds.size() > 0) {
            boolean flag = this.rolePermissionService.removeByRoleId(roleId);
            if (!flag) {
                return AdminResponseCodeEnum.DELETE_ROLE_PERMISSIONS_ERROR;
            }
        }
        // 批量新增新角色
        List<RolePermission> rolePermissions = RoleDto.transformRolePermissions(roleId, permissionIds);
        boolean flag = rolePermissionService.saveBatch(rolePermissions);
        if (!flag) {
            return AdminResponseCodeEnum.ADD_ROLE_PERMISSIONS_ERROR;
        }
        return AdminResponseCodeEnum.SUCCESS;
    }
}
