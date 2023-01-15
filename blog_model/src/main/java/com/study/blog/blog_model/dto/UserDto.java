package com.study.blog.blog_model.dto;

import com.study.blog.blog_model.pojo.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName UserDto
 * @Description TODO
 * @Author Alex Li
 * @Date 2022/10/8 23:08
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends User {

    private String repeatPassword;

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
}
