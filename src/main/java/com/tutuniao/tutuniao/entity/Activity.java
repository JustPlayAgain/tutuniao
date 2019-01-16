package com.tutuniao.tutuniao.entity;

import com.alibaba.druid.support.monitor.annotation.MTable;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@MTable(name = "t_activity")
public class Activity extends BaseEntity implements Serializable {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 活动code
     */
    private String activityCode;

    /**
     * 活动日期
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date activityDate;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date createDate;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date updateDate;
}
