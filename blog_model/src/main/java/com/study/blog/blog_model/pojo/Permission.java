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
 * 权限表
 * </p>
 *
 * @author Alex Li
 * @since 2022-10-03
 */
@Getter
@Setter
@TableName("permission")
@ApiModel(value = "Permission对象", description = "权限表")
public class Permission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("上级菜单ID")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty("权限名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("权限描述")
    @TableField("remark")
    private String remark;

    @ApiModelProperty("权限类型，1-菜单，2-按钮")
    @TableField("type")
    private Byte type;

    @ApiModelProperty("0-未删除，1-已删除")
    @TableField("is_delete")
    private Boolean isDelete;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
}
