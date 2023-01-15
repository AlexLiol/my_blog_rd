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
 * 操作日志表
 * </p>
 *
 * @author Alex Li
 * @since 2023-01-14
 */
@Getter
@Setter
@TableName("op_log")
@ApiModel(value = "OpLog对象", description = "操作日志表")
public class OpLog extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("操作人ID")
    @TableField("operator_id")
    private Long operatorId;

    @ApiModelProperty("操作类型")
    @TableField("operator_type")
    private Byte operatorType;

    @ApiModelProperty("操作内容")
    @TableField("operate_content")
    private String operateContent;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
}
