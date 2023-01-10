package com.study.blog.blog_service.service.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.blog_dao.mapper.CategoryMapper;
import com.study.blog.blog_model.pojo.Category;
import com.study.blog.blog_service.service.service.ICategoryService;

/**
 * <p>
 * 分类表 服务实现类
 * </p>
 *
 * @author Alex Li
 * @since 2022-10-03
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}
