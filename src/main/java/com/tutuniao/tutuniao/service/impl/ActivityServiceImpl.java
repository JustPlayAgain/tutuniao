package com.tutuniao.tutuniao.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tutuniao.tutuniao.entity.Activity;
import com.tutuniao.tutuniao.mapper.ActivityMapper;
import com.tutuniao.tutuniao.service.ActivityService;
import com.tutuniao.tutuniao.util.Jackson2Helper;
import com.tutuniao.tutuniao.util.Utils;
import com.tutuniao.tutuniao.vo.PageVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public int insertActivity(Activity activity) {
        log.info("新增【活动数据】开始 参数为:{}", Jackson2Helper.toJsonString(activity));
        return activityMapper.insertActivity(activity);
    }

    @Override
    public int deleteActivityById(Activity activity) {
        return 0;
    }

    @Override
    public int updateActivityById(Activity activity) {
        log.info("更新【活动数据】开始 参数为:{}", Jackson2Helper.toJsonString(activity));
        return activityMapper.updateActivityById(activity);
    }

    @Override
    public PageVO<List<Activity>> queryActivityList(Activity activity) {
        Page<PageInfo> pageInfo = null;
        boolean flag = false;
        if (Utils.isNotNull(activity.getPageIndex()) && Utils.isNotNull(activity.getPageSize())) {
            pageInfo = PageHelper.startPage(activity.getPageIndex(), activity.getPageSize());
            flag = true;
        }
        log.info("查询【活动数据】开始 参数为:{}", Jackson2Helper.toJsonString(activity));
        List<Activity> activityList = activityMapper.queryActivityList(activity);
        log.info("查询【活动数据】结束 结果为:{}", Jackson2Helper.toJsonString(activityList));

        return new PageVO<>(flag ? (int) pageInfo.getTotal() : activityList.size(), activityList, pageInfo.getPageSize());
    }

    @Override
    public Activity queryActivityById(Activity activity) {
        log.info("新增【活动数据】开始 参数为:{}", Jackson2Helper.toJsonString(activity));
        return activityMapper.queryActivityById(activity);
    }
}
