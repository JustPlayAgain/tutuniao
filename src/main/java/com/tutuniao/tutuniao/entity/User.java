package com.tutuniao.tutuniao.entity;

import com.alibaba.druid.support.monitor.annotation.MTable;
import lombok.Data;

import java.io.Serializable;

@Data
@MTable(name = "t_user")
public class User extends BaseEntity implements Serializable {
    private Integer id;
    private String userName;
    private String userPassword;
}
