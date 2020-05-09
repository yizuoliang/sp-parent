# SpringBoot+Maven多模块项目



## 前言：

​	由于公司团队在进行微服务的开发与改造，但却没展现出微服务的特性出来，于是有一个疑问，微服务真的适合我们公司的产品吗？类似OA系统、ERP系统、运维系统、视频监测系统、crm系统等需要使用微服务吗。

​	微服务架构不是万能的，服务难以治理，技术天花板高，微服务应用是分布式系统，由此会带来固有的复杂性。

​	在复杂度较小的场景中，一体化架构的效率明显更高，一体化架构，也需要考虑尽可能解耦，下面就是用SpringBoot+Maven多模块，生成一个项目架构，maven模块根据实际的业务进行划分，整合一些常用的框架，提高开发效率。

## 一、maven多模块管理

搭建maven多模块项目结构。

![](https://cdn.jsdelivr.net/gh/yizuoliang/picBed/img/20200507184309.bmp)

## 二、将mybatis-plus框架引入项目

参考官方文档：https://mybatis.plus/guide/page.html

### 1.分页插件

**mybatis-plus**提供的原生分页api，是查出表中所有数据，在内存中进行分页，而对于有大量数据的表，这种方式显然不合适；我们需要达到物理分页的目的，所以要配置分页插件。

```
@Configuration
public class MyBatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        paginationInterceptor.setOverflow(true);
        return paginationInterceptor;
    }

}
```

### 2.新增和更新时间的自动填充

1.添加注解填充字段`@TableField(..fill = FieldFill.INSERT_UPDATE)`

```java
@TableField(fill = FieldFill.INSERT_UPDATE)
private Date updateTime;

@TableField(fill = FieldFill.INSERT)
private Date createTime;
```

2.自定义实现类

```
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime",new Date(),metaObject);
        this.setFieldValByName("createTime",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(
                "updateTime",new Date(),metaObject
        );
    }
}
```

## 三、使用Flyway来管理数据库版本

​	Flyway是独立于数据库的应用、管理并跟踪数据库变更的数据库版本管理工具。暂时先简单配置，可能很多项目中，数据库中放了很多配置项，也就是说程序启动有些配置要先去查数据库，拿到数据后，再继续配置相关的服务，这个时候就要先执行flyway

```
spring:
  flyway:
    clean-disabled: trueclasspath:db/migration
    locations: classpath:db/migration
    baseline-on-migrate: true
```

## 四、异常统一处理，统一数据格式



## 五、系统日志处理



## 六、使用Quartz框架定时调用



## 七、excel导入导出模板



## 八、HttpClient调用



## 九、Shiro安全框架



## 十、ElasticSearch的使用



## 十一、websocket使用



## 十二、redis的使用



## 十三、JWT认证和授权





