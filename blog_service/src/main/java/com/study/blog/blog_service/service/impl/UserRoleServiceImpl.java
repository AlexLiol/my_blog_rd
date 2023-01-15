package com.study.blog.blog_service.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.blog_dao.mapper.UserRoleMapper;
import com.study.blog.blog_model.pojo.UserRole;
import com.study.blog.blog_service.service.IUserRoleService;

/**
 * <p>
 * 用户角色关联表 服务实现类
 * </p>
 *
 * @author Alex Li
 * @since 2023-01-14
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        if (userId <= 0) {
            return new ArrayList<>();
        }
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id").eq("user_id", userId);
        List<UserRole> userRoles = this.list(queryWrapper);
        return userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }
}
