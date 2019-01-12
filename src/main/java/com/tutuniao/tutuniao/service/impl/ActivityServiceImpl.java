package com.tutuniao.tutuniao.service.impl;

import com.tutuniao.tutuniao.entity.Activity;
import com.tutuniao.tutuniao.service.ActivityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    @Override
    public int insertActivity(Activity activity) {
        return 0;
    }

    @Override
    public int deleteActivityById(Integer activityId) {
        return 0;
    }

    @Override
    public int updateActivityById(Integer activityId) {
        return 0;
    }

    @Override
    public List<Activity> queryActivityList(Activity activity) {
        return null;
    }
}
