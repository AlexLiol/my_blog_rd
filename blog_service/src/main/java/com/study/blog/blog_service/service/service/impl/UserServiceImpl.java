package com.study.blog.blog_service.service.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.blog_core.constant.DeleteStatusEnum;
import com.study.blog.blog_core.constant.UserStatusEnum;
import com.study.blog.blog_dao.mapper.UserMapper;
import com.study.blog.blog_model.pojo.User;
import com.study.blog.blog_service.service.service.IUserService;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Alex Li
 * @since 2022-10-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User getByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username).eq("is_delete", DeleteStatusEnum.UN_DELETE.getStatus())
            .eq("status", UserStatusEnum.OPEN.getStatus());
        return this.getOne(queryWrapper);
    }

    @Override
    public User getByUsername(String username, Long exceptId) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username).eq("is_delete", DeleteStatusEnum.UN_DELETE.getStatus())
            .eq("status", UserStatusEnum.OPEN.getStatus()).ne("id", exceptId);
        return this.getOne(queryWrapper);
    }

    @Override
    public Long getCountByCond(Long id, String username, Integer status, Boolean isDelete) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (status != null && status != UserStatusEnum.UNKNOWN.getStatus()) {
            queryWrapper.eq("status", status);
        }
        if (isDelete != null) {
            queryWrapper.eq("is_delete", isDelete);
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
    public List<User> getUserByCond(Long id, String username, Integer status, Boolean isDelete, Integer page,
                                    Integer pageSize) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("count(1)");
        if (status != null && status != UserStatusEnum.UNKNOWN.getStatus()) {
            queryWrapper.eq("status", status);
        }
        if (isDelete != null) {
            queryWrapper.eq("is_delete", isDelete);
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

}
