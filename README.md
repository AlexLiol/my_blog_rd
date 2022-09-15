# Getting Started

### Reference Documentation

For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.7.3/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.7.3/maven-plugin/reference/html/#build-image)

### 技术栈
* Git
* Maven
* SpringBoot
* Mybatis-Plus
* Shiro
* MySQL
* Redis
* Lombok
* Logback
* qiniu（暂未接）

### Module 介绍
* blog_agg 整个项目的父功能，维护了所有项目的依赖的版本，只看 pom.xml 即可
* my_blog  博客的前台使用 API，包含熔断（未做）、限流（未做）、参数校验、数据转换等等
* blog_admin 博客的后台管理系统，包含 RBAC 权限管理、文章管理、评论管理、标签管理、分类管理、用户管理、角色管理、权限管理等等
* blog_service  博客的业务层
* blog_dao  博客的 DAO 层
* blog_pojo  博客的实体类，包含 pojo,vo,dto 等等
* blog_core  博客通用工具类，常量，枚举等等
* 