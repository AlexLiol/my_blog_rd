package com.study.blog.blog_service.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.blog_dao.mapper.TopNavConfigMapper;
import com.study.blog.blog_model.pojo.TopNavConfig;
import com.study.blog.blog_service.service.ITopNavConfigService;

/**
 * <p>
 * 顶部导航表 服务实现类
 * </p>
 *
 * @author Alex Li
 * @since 2023-01-14
 */
@Service
public class TopNavConfigServiceImpl extends ServiceImpl<TopNavConfigMapper, TopNavConfig> implements ITopNavConfigService {

}
