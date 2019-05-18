package com.tutuniao.tutuniao.entity;

import com.alibaba.druid.support.monitor.annotation.MTable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 测评证书entity
 */

@Data
@MTable(name = "t_match_template")
public class MatchTemplate extends BaseEntity implements Serializable {


    /** 主键id **/
    private Integer id;
    /** 序号 **/
    private Integer numberId;
    /** 名字 **/
    private String studentName;
    /**
     * 身份证
     */
    private String idCard;
    /** 出生日期 **/
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date birthDate;
    /** 测评名称 **/
    private String worksName;
    /** 证书编号 **/
    private String certificateNumber;
    /** 专业 **/
    private String profession;
    /** 考试级别 **/
    private String examinationLevel;
    /** 所在地 **/
    private String nativePlace;
    /** 考试时间 **/
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date examDate;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date createDate;


}