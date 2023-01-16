package com.study.blog.blog_admin.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.blog.blog_admin.utils.ResponseBeanUtil;
import com.study.blog.blog_core.constant.AdminResponseCodeEnum;
import com.study.blog.blog_model.common.ResponseBean;
import com.study.blog.blog_model.dto.PermissionDto;
import com.study.blog.blog_model.pojo.Permission;
import com.study.blog.blog_service.service.IPermissionService;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author Alex Li
 * @since 2023-01-14
 */
@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    private final IPermissionService permissionService;

    @Autowired
    public PermissionController(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @RequestMapping("/add")
//    @RequiresPermissions({"permission:add"})
    public ResponseBean addPermission(@RequestBody PermissionDto permissionDto) {
        AdminResponseCodeEnum adminResponseCodeEnum = preCheckPermission(permissionDto);
        if (adminResponseCodeEnum != AdminResponseCodeEnum.SUCCESS) {
            return ResponseBeanUtil.buildResponseByCode(adminResponseCodeEnum);
        }
        Permission permission = PermissionDto.transformUserDto(permissionDto);
        permission.setCreateTime(LocalDateTime.now());
        permission.setUpdateTime(LocalDateTime.now());
        boolean flag = permissionService.save(permission);
        if (!flag) {
            return ResponseBeanUtil.buildResponseByCode(AdminResponseCodeEnum.PERMISSION_ADD_FAIL_ERROR);
        }
        return ResponseBeanUtil.buildSuccessResponse();
    }

    @RequestMapping("/update")
//    @RequiresPermissions({"permission:update"})
    public ResponseBean updatePermission(@RequestBody PermissionDto permissionDto) {
        AdminResponseCodeEnum adminResponseCodeEnum = preCheckPermission(permissionDto);
        if (adminResponseCodeEnum != AdminResponseCodeEnum.SUCCESS) {
            return ResponseBeanUtil.buildResponseByCode(adminResponseCodeEnum);
        }
        if (permissionDto.getId() <= 0) {
            return ResponseBeanUtil.buildResponseByCode(AdminResponseCodeEnum.PERMISSION_ID_NOT_EXIST_ERROR);
        }
        Permission permission = PermissionDto.transformUserDto(permissionDto);
        permission.setUpdateTime(LocalDateTime.now());
        boolean flag = permissionService.updateById(permission);
        if (!flag) {
            return ResponseBeanUtil.buildResponseByCode(AdminResponseCodeEnum.PERMISSION_UPDATE_FAIL_ERROR);
        }
        return ResponseBeanUtil.buildSuccessResponse();
    }

    @RequestMapping("/delete")
    @RequiresPermissions({"permission:delete"})
    public ResponseBean deletePermission(@RequestBody Map<String, String> params) {
        long id = Long.parseLong(params.get("id"));
        if (id <= 0) {
            return ResponseBeanUtil.buildResponseByCode(AdminResponseCodeEnum.PARAM_ERROR);
        }
        boolean flag = permissionService.removeById(id);
        if (!flag) {
            return ResponseBeanUtil.buildResponseByCode(AdminResponseCodeEnum.PERMISSION_DELETE_FAIL_ERROR);
        }
        return ResponseBeanUtil.buildSuccessResponse();
    }

    @RequestMapping("/search")
    @RequiresPermissions({"permission:search"})
    public ResponseBean searchPermission(@RequestBody PermissionDto permissionDto) {
        // TODO:查询权限
        return ResponseBeanUtil.buildSuccessResponse();
    }

    private AdminResponseCodeEnum preCheckPermission(PermissionDto permissionDto) {
        if (StringUtils.isBlank(permissionDto.getName())) {
            return AdminResponseCodeEnum.PERMISSION_NAME_EMPTY_ERROR;
        }
        if (StringUtils.isBlank(permissionDto.getDescription())) {
            return AdminResponseCodeEnum.PERMISSION_DESCRIPTION_EMPTY_ERROR;
        }

        if (permissionDto.getType() <= 0) {
            return AdminResponseCodeEnum.PERMISSION_TYPE_NOT_EXIST_ERROR;
        }
        if (permissionDto.getParentId() < 0) {
            return AdminResponseCodeEnum.PERMISSION_PARENT_ID_NOT_EXIST_ERROR;
        }
        Permission permission = permissionService.getByName(permissionDto.getName(), permissionDto.getId());
        if (permission != null) {
            return AdminResponseCodeEnum.PERMISSION_EXISTS_ERROR;
        }
        return AdminResponseCodeEnum.SUCCESS;
    }
}
