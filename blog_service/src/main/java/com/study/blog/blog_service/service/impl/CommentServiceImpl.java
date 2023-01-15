package com.study.blog.blog_service.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.blog_dao.mapper.CommentMapper;
import com.study.blog.blog_model.pojo.Comment;
import com.study.blog.blog_service.service.ICommentService;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author Alex Li
 * @since 2023-01-14
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
