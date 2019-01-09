package com.tutuniao.tutuniao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.Mapping;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.tutuniao.tutuniao.mapper")
public class TutuniaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TutuniaoApplication.class, args);
    }

}

