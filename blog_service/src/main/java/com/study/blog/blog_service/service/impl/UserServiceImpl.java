package com.study.blog.blog_service.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.blog_core.constant.UserStatusEnum;
import com.study.blog.blog_dao.mapper.UserMapper;
import com.study.blog.blog_model.pojo.User;
import com.study.blog.blog_service.service.IUserService;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Alex Li
 * @since 2023-01-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User getByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username).eq("status", UserStatusEnum.OPEN.getStatus());
        return this.getOne(queryWrapper);
    }

    @Override
    public User getByUsername(String username, Long exceptId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username).eq("status", UserStatusEnum.OPEN.getStatus());
        if (exceptId != null && exceptId > 0) {
            queryWrapper.ne("id", exceptId);
        }
        return this.getOne(queryWrapper);
    }

    @Override
    public Long getCountByCond(Long id, String username, Integer status) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (status != null && status != UserStatusEnum.UNKNOWN.getStatus()) {
            queryWrapper.eq("status", status);
        }
        if (id != null && id != 0) {
            queryWrapper.eq("id", id);
        }
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        return this.count(queryWrapper);
    }

    @Override
    public List<User> getUserByCond(Long id, String username, Integer status, Integer page, Integer pageSize) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(1)");
        if (status != null && status != UserStatusEnum.UNKNOWN.getStatus()) {
            queryWrapper.eq("status", status);
        }
        if (id != null && id != 0) {
            queryWrapper.eq("id", id);
        }
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        int offset = (page - 1) * pageSize;
        queryWrapper.last(String.format("limit %d, %d", offset, pageSize));
        return this.list(queryWrapper);
    }

    @Override
    public boolean updateUserStatus(Long id, Integer status) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.set("status", status);
        wrapper.eq("id", id);
        return this.update(wrapper);
    }
}
