package com.study.blog.blog_service.service.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.blog_dao.mapper.CommentMapper;
import com.study.blog.blog_model.pojo.Comment;
import com.study.blog.blog_service.service.service.ICommentService;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author Alex Li
 * @since 2022-10-03
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
