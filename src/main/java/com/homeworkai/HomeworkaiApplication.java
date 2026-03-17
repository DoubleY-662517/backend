package com.homeworkai;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.homeworkai.mapper")
public class HomeworkaiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkaiApplication.class, args);
    }
}
