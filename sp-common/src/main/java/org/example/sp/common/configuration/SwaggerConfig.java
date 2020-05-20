package org.example.sp.common.configuration;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: yizl
 * @Date: 2020/5/9
 * @Description:
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //.groupName("分组")
                .select()
                //配置要扫描接口的方式,
                .apis(RequestHandlerSelectors.basePackage("org.example.sp"))
                //类上加了Api注解的才能扫描到
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //过滤
                .paths(PathSelectors.any())
                .build();
    }

    @Value("${version}")
    private String version;

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("SpringBoot项目 API 文档")
                .description("详细文档说明")
                //这里要获取项目父版本
                .version(version)
                .build();
    }

}
