package com.study.blog.blog_service.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.blog_dao.mapper.PermissionMapper;
import com.study.blog.blog_model.pojo.Permission;
import com.study.blog.blog_service.service.IPermissionService;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author Alex Li
 * @since 2023-01-14
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Override
    public Permission getByName(String name) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<Permission>();
        wrapper.eq("name", name);
        return this.getOne(wrapper);
    }
}
