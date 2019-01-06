package com.tutuniao.tutuniao.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class MatchTemplate implements Serializable {
    /** 序号 **/
    private String numberId;
    /** 名字 **/
    private String name;
    /** 出生日期 **/
    private String birthDate;
    /**  性别 **/
    private String gender;
    /** 专业 **/
    private String profession;
    /**  组别 **/
    private String groupLevel;
    /** 作品名称 **/
    private String worksName;
    /**  辅导老师 **/
    private String tutor;
    /**  获奖结果 **/
    private String results;
    /** 插入数据库时间 **/
    private String insertTime;
}
