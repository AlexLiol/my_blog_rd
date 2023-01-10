package com.study.blog.blog_dao.utils;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.study.blog.blog_model.pojo.BaseEntity;

/**
 * @ClassName CodeGenerator
 * @Description TODO
 * @Author Alex Li
 * @Date 2022/9/23 09:19
 * @Version 1.0
 */
public class CodeGenerator {

    public static void main(String[] args) {
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder(
            "jdbc:mysql://localhost:3306/blog?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&allowPublicKeyRetrieval=true",
            "root", "12345678").build();

        AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfig);

        String projectPath = System.getProperty("user.dir");
        GlobalConfig globalConfig = new GlobalConfig.Builder().author("Alex Li")
            .outputDir(projectPath + "/blog_dao/src/main/java").enableSwagger().build();

        autoGenerator.global(globalConfig);
        PackageConfig packageConfig =
            new PackageConfig.Builder().parent("com.study.blog.blog_dao").entity("pojo").mapper("mapper")
                .xml("mapper.xml").service("service").serviceImpl("service.impl").controller("controller").build();

        autoGenerator.packageInfo(packageConfig);
        StrategyConfig strategyConfig = new StrategyConfig.Builder().entityBuilder().superClass(BaseEntity.class)
            .enableLombok().enableTableFieldAnnotation().columnNaming(NamingStrategy.underline_to_camel)
            .enableFileOverride().mapperBuilder().enableFileOverride().serviceBuilder()
            .superServiceClass("com.study.blog.blog_service.service.IBaseService").enableFileOverride()
            .controllerBuilder().enableRestStyle().enableHyphenStyle().enableFileOverride().build();
        autoGenerator.strategy(strategyConfig);

        TemplateConfig templateConfig = new TemplateConfig.Builder().build();
        autoGenerator.template(templateConfig);

        autoGenerator.execute();
    }
}
