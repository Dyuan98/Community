## Dyuan的Spring学习项目  
### 学习网站  
[B站码匠社区](https://www.bilibili.com/video/av65117012)  
### 资料
[Spring文档](https://spring.io/guides)  
[Spring Boot文档](https://docs.spring.io/spring-boot/docs/2.2.0.RC1/reference/htmlsingle/)  
[Spring Web 文档](https://spring.io/guides/gs/serving-web-content)  
[elastic中文社区](https://elasticsearch.cn/explore)  
[Bootstrap文档](https://v3.bootcss.com/https://v3.bootcss.com/)  
[Github OAuth](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)  
[OK Http](https://square.github.io/okhttp/)  
[Spring-boot Mybatis整合](https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/index.html)  
[thymeleaf文档](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#setting-attribute-values)   
[Spring MVC 文档](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html)
### 工具
[maven仓库](https://mvnrepository.com/)  
[Git 下载](https://git-scm.com/download)  
[H2数据库](http://www.h2database.com/html/quickstart.html)  
[visual-paradigm](https://www.visual-paradigm.com)  
[flyway管理数据库](https://flywaydb.org/getstarted/firststeps/maven)  
[Lombok通过注解简化程序](https://projectlombok.org/setup/maven)
### 脚本
___user表创建语句：___
```sql
create table USER
(
    ID           INT auto_increment,
    ACCOUNT_ID   VARCHAR(100),
    NAME         VARCHAR(50),
    TOKEN        CHAR(36),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    constraint USER_PK
        primary key (ID)
);
```
___flyway脚本语句___
```bash
mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```
