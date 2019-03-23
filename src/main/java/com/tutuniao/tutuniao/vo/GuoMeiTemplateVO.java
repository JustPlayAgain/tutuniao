package com.tutuniao.tutuniao.vo;

import com.tutuniao.tutuniao.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GuoMeiTemplateVO extends BaseEntity implements Serializable {
    /** 名字 **/
    private String studentName;
    /** 证书编号 **/
    private String certificateNumber;
    /** 身份证 **/
    private String idCard;
    /** 比赛 测评名称 **/
    private String  activityName;
    /** 插入数据库时间 **/
    private Date createDate;
}
