/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.tutuniao.tutuniao.vo;

import java.io.Serializable;


public class PageVO<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int total;
    private int pageSize;
    private T t;

    public PageVO() {
    }

    ;

    public PageVO(int total, T t, int pageSize) {
        super();
        this.total = total;
        this.t = t;
        this.pageSize = pageSize;
    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public T getT() {
        return t;
    }

    public void setT(T data) {
        this.t = t;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
