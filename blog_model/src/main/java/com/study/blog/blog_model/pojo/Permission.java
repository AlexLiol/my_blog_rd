package com.study.blog.blog_model.pojo;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author Alex Li
 * @since 2023-01-14
 */
@Getter
@Setter
@TableName("permission")
@ApiModel(value = "Permission对象", description = "权限表")
public class Permission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("上级菜单ID")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty("权限名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("权限描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("权限类型，1-菜单，2-按钮")
    @TableField("type")
    private Integer type;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
}
