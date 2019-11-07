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
    /** 身份证 **/
    private String idCard;
    /** 出生日期 **/
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date birthDate;
    /** 大赛名称 **/
    private String worksName;
    /** 专业 **/
    private String profession;
    /**  获奖结果 **/
    private String results;

    /** 是否删除 **/
    private int valid;

    /** 插入数据库时间 **/
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date createDate;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 关联活动id
     */
    private Integer actId;

//    private Activity activity;
    private String  activityName;
}
