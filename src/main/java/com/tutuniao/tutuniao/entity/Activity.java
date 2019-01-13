package com.tutuniao.tutuniao.entity;

import com.alibaba.druid.support.monitor.annotation.MTable;
import lombok.Data;

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
    private Date activityDate;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    private Date updateDate;
}
