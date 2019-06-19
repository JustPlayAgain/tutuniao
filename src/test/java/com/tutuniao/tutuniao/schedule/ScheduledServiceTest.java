package com.tutuniao.tutuniao.schedule;

import com.alibaba.fastjson.JSONObject;
import com.tutuniao.tutuniao.common.Constant;
import com.tutuniao.tutuniao.entity.IndexObject;
import com.tutuniao.tutuniao.util.HttpClientUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScheduledServiceTest {

    @Test
    public void buildIndex() {
//        IndexObject indexObject = new ScheduledService().buildIndex();
//        System.out.println(JSONObject.toJSONString(indexObject));

//        System.out.println(ScheduledService.buildIndexJson());
        String dataAsStringFromUrl = HttpClientUtils.doGet(Constant.academyArtUrl);
        ScheduledService.downloadImage(dataAsStringFromUrl);
    }
}