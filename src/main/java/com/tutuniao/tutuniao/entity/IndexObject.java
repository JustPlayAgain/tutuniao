package com.tutuniao.tutuniao.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class IndexObject implements Serializable {
    private String banners;
    private String content;
    private List<News> newsList;
    private List<Activity> activityList;

}
