package com.study.blog.blog_service.service.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.blog_dao.mapper.RoleMapper;
import com.study.blog.blog_model.pojo.Role;
import com.study.blog.blog_service.service.service.IRoleService;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Alex Li
 * @since 2022-10-03
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
