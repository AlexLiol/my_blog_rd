package com.study.blog.blog_admin.controller;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.blog.blog_admin.utils.ResponseBeanUtil;
import com.study.blog.blog_core.constant.ResponseCodeEnum;
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
    public ResponseBean addPermission(@RequestBody PermissionDto permissionDto) {
        ResponseCodeEnum responseCodeEnum = preCheckPermission(permissionDto);
        if (responseCodeEnum != ResponseCodeEnum.SUCCESS) {
            return ResponseBeanUtil.buildResponseByCode(responseCodeEnum);
        }
        Permission permission = PermissionDto.transformUserDto(permissionDto);
        permission.setCreateTime(LocalDateTime.now());
        permission.setUpdateTime(LocalDateTime.now());
        permissionService.save(permission);
        return ResponseBeanUtil.buildSuccessResponse();
    }

    private ResponseCodeEnum preCheckPermission(PermissionDto permissionDto) {
        if (StringUtils.isBlank(permissionDto.getName())) {
            return ResponseCodeEnum.PERMISSION_NAME_EMPTY_ERROR;
        }
        if (StringUtils.isBlank(permissionDto.getDescription())) {
            return ResponseCodeEnum.PERMISSION_DESCRIPTION_EMPTY_ERROR;
        }

        if (permissionDto.getType() <= 0) {
            return ResponseCodeEnum.PERMISSION_TYPE_NOT_EXIST_ERROR;
        }
        if (permissionDto.getParentId() < 0) {
            return ResponseCodeEnum.PERMISSION_PARENT_ID_NOT_EXIST_ERROR;
        }
        Permission permission = permissionService.getByName(permissionDto.getName());
        if (permission != null) {
            return ResponseCodeEnum.PERMISSION_EXISTS_ERROR;
        }
        return ResponseCodeEnum.SUCCESS;
    }
}
