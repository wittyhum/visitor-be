package com.qianwang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.qianwang.mapper")
public class VisitorQwApplication {

    public static void main(String[] args) {
        SpringApplication.run(VisitorQwApplication.class, args);
    }
}
