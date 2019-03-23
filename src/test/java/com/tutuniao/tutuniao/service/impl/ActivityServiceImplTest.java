package com.tutuniao.tutuniao.service.impl;

import com.tutuniao.tutuniao.entity.Activity;
import com.tutuniao.tutuniao.entity.News;
import com.tutuniao.tutuniao.mapper.ActivityMapper;
import com.tutuniao.tutuniao.util.Jackson2Helper;
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
public class ActivityServiceImplTest {
    @Autowired
    private ActivityMapper activityMapper;

//    @Test
    public void insertActivity() {
        Activity activity = new Activity();
        activity.setActivityName("测试");
        activity.setActivityCode("123");
        activity.setActivityDate(new Date());
        activity.setCreateUser("yugc");
        activity.setCreateDate(new Date());
        activity.setUpdateUser("yugc");
        activity.setUpdateDate(new Date());
        int i = activityMapper.insertActivity(activity);
        System.out.println(Jackson2Helper.toJsonStringNotNull(i));
        System.out.println(activity.getId());
    }

    @Test
    public void deleteActivityById() {
    }

//    @Test
    public void updateActivityById() {
        Activity activity = new Activity();
        activity.setId(1);
        activity.setActivityName("测试1111");
        activity.setActivityCode("12323123123");
        activity.setActivityDate(new Date());
        activity.setUpdateUser("yugc11112");
        activity.setUpdateDate(new Date());
        activityMapper.updateActivityById(activity);
    }

//    @Test
    public void queryActivityList() {
        List<Activity> activities = activityMapper.queryActivityList(new Activity());
        log.info("活动信息：{} ========", Jackson2Helper.toJsonString(activities));
    }

//    @Test
    public void queryActivityById() {
        Activity activity = new Activity();
        activity.setId(1);
        activity = activityMapper.queryActivityById(activity);
        log.info("活动信息：{} ========", Jackson2Helper.toJsonString(activity));
    }
}