package com.tutuniao.tutuniao;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {

    @Test
    public void test () {
        String level = "error";
        String level2 = "warn";
        String level3 = "info";
        log.info("========日志测试开始========");
        log.error("error ====== {} ", level);
        log.warn("warn ====== {}", level2);
        log.info("info ====== {}", level3);
        log.info("========日志测试结束========");
    }
}
