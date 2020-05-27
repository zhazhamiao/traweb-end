package com.liaomiao.traweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "com.liaomiao.traweb.mapper")
public class TrawebApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrawebApplication.class, args);
    }

}
