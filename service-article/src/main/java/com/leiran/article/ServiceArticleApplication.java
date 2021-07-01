package com.leiran.article;

import com.leiran.log.aop.EnableAopLog;
import com.leiran.security.annotation.EnableSecurityModule;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author lei ran
 **/

@SpringBootApplication
@EnableEurekaClient
@EnableSecurityModule
@EnableAopLog
@MapperScan({"com.leiran.article.mapper", "com.leiran.security.mapper", "com.leiran.log.mapper"})
public class ServiceArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceArticleApplication.class, args);
    }
}
