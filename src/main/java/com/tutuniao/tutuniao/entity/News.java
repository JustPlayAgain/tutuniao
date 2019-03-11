package com.tutuniao.tutuniao.entity;

import com.alibaba.druid.support.monitor.annotation.MTable;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@MTable(name = "t_news")
public class News extends BaseEntity {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 跳转链接
     */
    private String newsUrl;

    /**
     * 缩略图地址
     */
    private String newsPic;

    /**
     * 活动名称
     */
    private String newsTitle;

    /**
     * 是否在官网展现 0:可见 1:不可见
     */
    private Integer newsIsAble;

    /**
     * 创建人
     */
    private String createUser;

    /**
     * 创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date createDate;

    /**
     * 修改人
     */
    private String updateUser;

    /**
     * 修改时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date updateDate;
}
