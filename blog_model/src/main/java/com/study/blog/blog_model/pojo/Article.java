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
 * 文章表
 * </p>
 *
 * @author Alex Li
 * @since 2023-01-14
 */
@Getter
@Setter
@TableName("article")
@ApiModel(value = "Article对象", description = "文章表")
public class Article extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("作者ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("标题")
    @TableField("title")
    private String title;

    @ApiModelProperty("摘要")
    @TableField("summary")
    private String summary;

    @ApiModelProperty("浏览数")
    @TableField("view_counts")
    private Integer viewCounts;

    @ApiModelProperty("点赞数")
    @TableField("like_counts")
    private Integer likeCounts;

    @ApiModelProperty("分类ID")
    @TableField("category_id")
    private Long categoryId;

    @ApiModelProperty("文章内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
}
