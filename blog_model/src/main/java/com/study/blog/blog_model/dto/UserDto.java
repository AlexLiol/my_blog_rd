package com.study.blog.blog_model.dto;

import java.util.ArrayList;
import java.util.List;

import com.study.blog.blog_model.pojo.User;
import com.study.blog.blog_model.pojo.UserRole;

import lombok.Data;

/**
 * @ClassName UserDto
 * @Description TODO
 * @Author Alex Li
 * @Date 2022/10/8 23:08
 * @Version 1.0
 */
@Data
public class UserDto extends User {

    private String repeatPassword;

    private List<Long> roleIds;

    public static User transformUserDto(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setAvatar(userDto.getAvatar());
        user.setEmail(userDto.getEmail());
        user.setGender(userDto.getGender());
        user.setStatus(userDto.getStatus());
        user.setMobile(userDto.getMobile());
        user.setUserDesc(userDto.getUserDesc());
        return user;
    }

    public static List<UserRole> transformUserRoles(Long userId, List<Long> roleIds) {
        List<UserRole> userRoles = new ArrayList<>();
        for (Long roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            userRoles.add(userRole);
        }
        return userRoles;
    }
}
