package com.study.blog.blog_service.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.blog_dao.mapper.RoleMapper;
import com.study.blog.blog_model.pojo.Role;
import com.study.blog.blog_service.service.IRoleService;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Alex Li
 * @since 2023-01-14
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Override
    public Role getByName(String name, Long id) {
        QueryWrapper<Role> wrapper = new QueryWrapper<Role>();
        wrapper.eq("name", name);
        if (id != null && id != 0) {
            wrapper.ne("id", id);
        }
        return this.getOne(wrapper);
    }
}
