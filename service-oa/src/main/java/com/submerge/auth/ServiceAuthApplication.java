package com.submerge.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * ClassName: SserviceAuthApplication
 * Package: PACKAGE_NAME
 * Description:
 *
 * @Author Submerge--WangDong
 * @Create 2024-04-01 18:45
 * @Version 1.0
 */
@SpringBootApplication
@ComponentScan("com.submerge")
public class ServiceAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceAuthApplication.class, args);
    }
}
