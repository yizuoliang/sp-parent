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

## 四、统一数据格式、swagger的使用

### 1.swagger介绍

​	先主流架构基本都是前后端分离，前后端的联系，变成API接口，所以API文档准确性往往很重要，有时候后台开发更改了接口，文档未更新，需要维护接口文档耗费过多精力，swagger就很好解决了这个问题，接口和文档始终报纸一致。

### 2.下载文档，转化成word

​	公司的文档需要word格式，给第三方使用也需要文档格式，而swagger-ui未提供下载，所以在网上找了个开源项目，可以提供下载，配置简单好用。

**GitHub 地址：https://github.com/JMCuixy/swagger2word**

注：许多公司用到的项目文档管理平台，例如Yapi，里面都可以导入swagger数据格式。

### 3.使用`swagger-bootstrap-ui`的UI框架

`swagger-ui`的ui貌似比较简陋,不符合国人的眼光,所以使用网上开源的`swagger-bootstrap-ui`框架

**官方文档:  https://doc.xiaominfo.com**

swagger-bootstrap-ui默认访问地址是：`http://${host}:${port}/doc.html`

## 五、异常统一处理、系统日志处理

异常日志参考 : java开发手册(泰山版)

## 六、使用Quartz框架定时调用

![](https://cdn.jsdelivr.net/gh/yizuoliang/picBed/img/20200521165502.bmp)

```java

spring:
  ## quartz定时任务,采用数据库方式
  quartz:
    job-store-type: jdbc
    initialize-schema: embedded
    #设置自动启动，默认为 true
    auto-startup: true
    #启动时更新己存在的Job
    overwrite-existing-jobs: true
    properties:
      org:
        quartz:
          scheduler:
            #d调度实例名称,quartz集群时要相同
            instanceName: sp-quartz
            instanceId: AUTO
          jobStore:
            class: org.quartz.impl.jdbcjobstore.JobStoreTX
            driverDelegateClass: org.quartz.impl.jdbcjobstore.StdJDBCDelegate
            # quartz表的前缀
            tablePrefix: qrtz_
            # 关闭quartz集群
            isClustered: false
            misfireThreshold: 60000
            clusterCheckinInterval: 10000
          threadPool:
            class: org.quartz.simpl.SimpleThreadPool
            threadCount: 10
            threadPriority: 5
           
```

## 七、easyexcel的使用

**[官方网站: https://yuque.com/easyexcel](https://www.yuque.com/easyexcel/doc/easyexcel)**

## 八、Shiro安全框架

### 1.shiro架构

![](https://cdn.jsdelivr.net/gh/yizuoliang/picBed/img/20200529100426.png)

![](https://cdn.jsdelivr.net/gh/yizuoliang/picBed/img/20200529100210.png)

### 2.密码的MD5盐值加密

​	1.不管前端传过来的加密密码或者明文密码,后台按照,密码加盐值MD5加密,存入数据库.

​	2.定义凭证匹配器(认证是告诉Realm,加密方式)

```
    /**
     *  凭证匹配器(注册到自定义的Realm中)
     *  在认证时,就会按照下面方式进行认证(说白了,就是将登陆的密码,按照下面方式进行加密,在和数据库的对比)
     *
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置散列算法:MD5
        credentialsMatcher.setHashAlgorithmName(ShiroConstant.ALGORITHM_NAME);
        // MD5加密次数
        credentialsMatcher.setHashIterations(ShiroConstant.ENCODE_COUNT);
        return credentialsMatcher;
    }

```

注: 前端应该对用户输入的密码进行加密后再传输(http通道安全),下面有一种方案;

1.前端对密码进行加盐MD5;

2.后台再对密码加盐MD5,存入数据库;

## 九、JWT认证和授权



## 十、redis的使用



## 十一、Docker部署



## 





