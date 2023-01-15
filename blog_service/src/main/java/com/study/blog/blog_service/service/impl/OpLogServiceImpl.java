package com.study.blog.blog_service.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.study.blog.blog_dao.mapper.OpLogMapper;
import com.study.blog.blog_model.pojo.OpLog;
import com.study.blog.blog_service.service.IOpLogService;

/**
 * <p>
 * 操作日志表 服务实现类
 * </p>
 *
 * @author Alex Li
 * @since 2023-01-14
 */
@Service
public class OpLogServiceImpl extends ServiceImpl<OpLogMapper, OpLog> implements IOpLogService {

}
