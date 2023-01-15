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
 * 评论垃圾箱表
 * </p>
 *
 * @author Alex Li
 * @since 2023-01-14
 */
@Getter
@Setter
@TableName("comment_trash")
@ApiModel(value = "CommentTrash对象", description = "评论垃圾箱表")
public class CommentTrash extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("评论内容")
    @TableField("content")
    private String content;

    @ApiModelProperty("文章ID")
    @TableField("article_id")
    private Long articleId;

    @ApiModelProperty("父ID，0代表一级评论")
    @TableField("parent_id")
    private Long parentId;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;

    @ApiModelProperty("源评论ID")
    @TableField("root_id")
    private Long rootId;
}
