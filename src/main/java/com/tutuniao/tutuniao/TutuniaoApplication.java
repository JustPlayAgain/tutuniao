package com.tutuniao.tutuniao;

import com.tutuniao.tutuniao.util.CookieUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("com.tutuniao.tutuniao.mapper")
@Import({CookieUtils.class})
public class TutuniaoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TutuniaoApplication.class, args);
    }

}

