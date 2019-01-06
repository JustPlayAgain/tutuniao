package com.tutuniao.tutuniao.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class GuoMeiTemplate implements Serializable {
    /** 序号 **/
    private String numberId;
    /** 名字 **/
    private String name;
    /** 国籍 **/
    private String nationality;
    /** 名族 **/
    private String nation;
    /** 性别 **/
    private String gender;
    /** 出生日期 **/
    private String birthDate;
    /** 证书编号 **/
    private String certificateNumber;
    /** 专业 **/
    private String profession;
    /** 申报级别 **/
    private String declareLevel;
    /** 考试级别 **/
    private String examinationLevel;
    /** 原级别 **/
    private String originalLevel;
    /** 籍贯 **/
    private String nativePlace;
    /** 插入数据库时间 **/
    private String insertTime;
}
