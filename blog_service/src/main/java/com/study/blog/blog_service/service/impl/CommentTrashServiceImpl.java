package com.study.blog.blog_service.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.blog_dao.mapper.CommentTrashMapper;
import com.study.blog.blog_model.pojo.CommentTrash;
import com.study.blog.blog_service.service.ICommentTrashService;

/**
 * <p>
 * 评论垃圾箱表 服务实现类
 * </p>
 *
 * @author Alex Li
 * @since 2023-01-14
 */
@Service
public class CommentTrashServiceImpl extends ServiceImpl<CommentTrashMapper, CommentTrash> implements ICommentTrashService {

}
