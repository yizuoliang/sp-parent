package org.example.sp.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * 功能描述: springboot启动类
 * @Author: yizl
 * @Date: 2020/5/8 11:04
 */

@SpringBootApplication(scanBasePackages = "org.example")
@MapperScan(basePackages = {"org.example.sp.**.mapper"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
