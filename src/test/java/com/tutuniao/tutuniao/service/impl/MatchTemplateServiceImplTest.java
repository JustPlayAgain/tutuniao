package com.tutuniao.tutuniao.service.impl;

import com.tutuniao.tutuniao.entity.MatchTemplate;
import com.tutuniao.tutuniao.service.MatchTemplateService;
import com.tutuniao.tutuniao.util.Jackson2Helper;
import com.tutuniao.tutuniao.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MatchTemplateServiceImplTest {

    @Autowired
    private MatchTemplateService matchTemplateService;

//    @Test
    public void queryMatchTemplateById() {
        MatchTemplate matchTemplate = matchTemplateService.queryMatchTemplateById(3);
        log.info("图图鸟证书: ========= {}",Jackson2Helper.toJsonStringNotNull(matchTemplate));
    }

//    @Test
    public void insertMatchTemplate() {
        MatchTemplate matchTemplate = new MatchTemplate();
        matchTemplate.setNumberId(3333);
        matchTemplate.setStudentName("测试3");
        matchTemplate.setIdCard("测试3");
        matchTemplate.setBirthDate(new Date());
        matchTemplate.setGender("男");
        matchTemplate.setProfession("测试专业3");
        matchTemplate.setGroupLevel("组别test3");
        matchTemplate.setWorksName("测试工作3");
        matchTemplate.setTutor("老师3");
        matchTemplate.setResults("获奖结果3");
        matchTemplate.setCreateDate(new Date());
        int num = matchTemplateService.insertMatchTemplate(matchTemplate);
        log.info("插入证书结果: =========== {}", num);
    }

//    @Test
    public void deleteMatchTemplateById() {
    }

//    @Test
    public void updateMatchTemplateById() {
        MatchTemplate matchTemplate = new MatchTemplate();
        matchTemplate.setId(3);
        matchTemplate.setNumberId(10000);
        matchTemplate.setGender("女");
        int num = matchTemplateService.updateMatchTemplateById(matchTemplate);
        log.info("修改结果: ============ {}", num);
    }

//    @Test
    public void queryMatchTemplateList() {
        MatchTemplate matchTemplate = new MatchTemplate();
        matchTemplate.setPageIndex(1);
        matchTemplate.setPageSize(1);
        PageVO<List<MatchTemplate>> listPageVO = matchTemplateService.queryMatchTemplateList(matchTemplate);
        log.info("图图鸟证书列表: ========= {}",Jackson2Helper.toJsonStringNotNull(listPageVO));
    }

    @Test
    public void importMatchData() {
    }
}