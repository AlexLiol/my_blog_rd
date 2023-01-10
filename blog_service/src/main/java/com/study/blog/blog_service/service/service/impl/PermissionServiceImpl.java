package com.study.blog.blog_service.service.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.blog_dao.mapper.PermissionMapper;
import com.study.blog.blog_model.pojo.Permission;
import com.study.blog.blog_service.service.service.IPermissionService;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author Alex Li
 * @since 2022-10-03
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
