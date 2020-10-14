package com.liu;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.liu.dao")
public class SpringredisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringredisApplication.class, args);
    }

}
