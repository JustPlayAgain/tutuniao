/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.tutuniao.tutuniao.entity;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tutuniao.tutuniao.util.Utils;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BaseEntity implements Serializable {
    private static final long serialVersionUID = -7700209670671495655L;

    @Transient
    private transient List<Integer> ids;

    @Transient
    private transient Date startTime;

    @Transient
    private transient Date endTime;

    @Transient
    private transient  Integer pageIndex;
    @Transient
    private transient Integer pageSize;

    @Transient
    private transient  String orderFields;



    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderFields() {
        return orderFields;
    }

    public void setOrderFields(String orderFields) {
        this.orderFields = orderFields;
    }

    public Page<PageInfo> getPageInfos(BaseEntity baseEntity) {
        Page<PageInfo> pageInfo;
        if (Utils.isNotNull(baseEntity.getPageIndex()) && Utils.isNotNull(baseEntity.getPageSize())) {
            pageInfo = PageHelper.startPage(baseEntity.getPageIndex(), baseEntity.getPageSize());
        }else{
            pageInfo = PageHelper.startPage(0, 10);
        }
        return pageInfo;
    }
}
