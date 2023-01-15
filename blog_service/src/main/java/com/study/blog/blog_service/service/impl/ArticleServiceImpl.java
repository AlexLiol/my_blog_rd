package com.study.blog.blog_service.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.blog_dao.mapper.ArticleMapper;
import com.study.blog.blog_model.pojo.Article;
import com.study.blog.blog_service.service.IArticleService;

/**
 * <p>
 * 文章表 服务实现类
 * </p>
 *
 * @author Alex Li
 * @since 2023-01-14
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
