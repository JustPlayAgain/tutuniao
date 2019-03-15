package com.tutuniao.tutuniao.entity;

import com.alibaba.druid.support.monitor.annotation.MTable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@MTable(name = "t_guomei_template")
public class GuoMeiTemplate extends BaseEntity implements Serializable {
    /** 主键id **/
    private Integer id;
    /** 序号 **/
    private Integer numberId;
    /** 名字 **/
    private String studentName;
    /** 国籍 **/
    private String nationality;
    /** 民族 **/
    private String nation;
    /** 性别 **/
    private String gender;
    /** 出生日期 **/
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date birthDate;
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
    /** 所在地 **/
    private String nativePlace;
    /** 考试时间 **/
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date examDate;
    /** 插入数据库时间 **/
    private Date createDate;
    /** 插入数据库用户 **/
    private String createUser;
    /**是否删除 0：否 1：是**/
    private int valid;
}
