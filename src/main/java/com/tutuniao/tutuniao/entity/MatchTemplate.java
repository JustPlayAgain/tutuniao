package com.tutuniao.tutuniao.entity;

import com.alibaba.druid.support.monitor.annotation.MTable;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 比赛证书entity
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
    /** 出生日期 **/
    private Date birthDate;
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
    private Date createDate;
}
