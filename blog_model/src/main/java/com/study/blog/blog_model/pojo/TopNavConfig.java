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
 * 顶部导航表
 * </p>
 *
 * @author Alex Li
 * @since 2022-10-03
 */
@Getter
@Setter
@TableName("top_nav_config")
@ApiModel(value = "TopNavConfig对象", description = "顶部导航表")
public class TopNavConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("顶部导航栏名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty("父id")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty("tab对应的url")
    @TableField("url")
    private String url;
}
