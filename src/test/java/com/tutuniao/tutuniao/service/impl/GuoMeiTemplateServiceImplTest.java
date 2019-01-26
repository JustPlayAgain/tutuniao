package com.tutuniao.tutuniao.service.impl;

import com.tutuniao.tutuniao.entity.GuoMeiTemplate;
import com.tutuniao.tutuniao.service.GuoMeiTemplateService;
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
public class GuoMeiTemplateServiceImplTest {

    @Autowired
    private GuoMeiTemplateService guoMeiTemplateService;

    @Test
    public void queryGuoMeiTemplate() {
    }

    @Test
    public void insertGuoMeiTemplate() {
        GuoMeiTemplate guoMeiTemplate = new GuoMeiTemplate();
        guoMeiTemplate.setNumberId(11111);
        guoMeiTemplate.setStudentName("王五");
        guoMeiTemplate.setNationality("中国");
        guoMeiTemplate.setNation("汉族");
        guoMeiTemplate.setGender("女");
        guoMeiTemplate.setBirthDate(new Date());
        guoMeiTemplate.setCertificateNumber("22222");
        guoMeiTemplate.setProfession("test2222");
        guoMeiTemplate.setDeclareLevel("test1");
        guoMeiTemplate.setExaminationLevel("test1");
        guoMeiTemplate.setOriginalLevel("test1");
        guoMeiTemplate.setNativePlace("test1");
        guoMeiTemplate.setExamDate(new Date());
        guoMeiTemplate.setCreateDate(new Date());
        guoMeiTemplate.setCreateUser("admin");
        guoMeiTemplate.setValid(0);
        int num = guoMeiTemplateService.insertGuoMeiTemplate(guoMeiTemplate);
        log.info("插入国美证书结果: ======== {}", num);
    }

    @Test
    public void deleteGuoMeiTemplateById() {
        int num = guoMeiTemplateService.deleteGuoMeiTemplateById(1);
        log.info("逻辑删除国美证书结果: ======== {}", num);
    }

    @Test
    public void updateGuoMeiTemplateById() {
        GuoMeiTemplate guoMeiTemplate = new GuoMeiTemplate();
        guoMeiTemplate.setId(1);
        guoMeiTemplate.setProfession("AAAAA");
        int num = guoMeiTemplateService.updateGuoMeiTemplateById(guoMeiTemplate);
        log.info("修改国美证书结果: ======== {}", num);
    }

    @Test
    public void queryGuoMeiTemplateList() {
        GuoMeiTemplate guoMeiTemplate = new GuoMeiTemplate();
        guoMeiTemplate.setPageIndex(1);
        guoMeiTemplate.setPageSize(1);
        PageVO<List<GuoMeiTemplate>> listPageVO = guoMeiTemplateService.queryGuoMeiTemplateList(guoMeiTemplate);
        log.info("查询列表信息: ========= {}", Jackson2Helper.toJsonStringNotNull(listPageVO));
    }

    @Test
    public void queryGuoMeiTemplateById() {
        GuoMeiTemplate guoMeiTemplate = guoMeiTemplateService.queryGuoMeiTemplateById(1);
        log.info("查询单条信息: ========= {}", Jackson2Helper.toJsonStringNotNull(guoMeiTemplate));
    }

    @Test
    public void importGuoMeiData() {
    }
}