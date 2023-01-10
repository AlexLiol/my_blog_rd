package com.study.blog.blog_model.pojo;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Alex Li
 * @since 2022-10-03
 */
@Getter
@Setter
@TableName("user")
@ApiModel(value = "User对象", description = "用户表")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty("密码")
    @TableField("password")
    private String password;

    @ApiModelProperty("头像链接")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty("邮箱地址")
    @TableField("email")
    private String email;

    @ApiModelProperty("0-保密，1-男，2-女")
    @TableField("gender")
    private Integer gender;

    @ApiModelProperty("0-正常")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("0-未删除，1-已删除")
    @TableField("is_delete")
    private Boolean isDelete;

    @ApiModelProperty("上次登录时间")
    @TableField("last_login")
    private LocalDateTime lastLogin;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty("手机号")
    @TableField("mobile")
    private String mobile;

    @ApiModelProperty("个人介绍")
    @TableField("desc")
    private String desc;
}
