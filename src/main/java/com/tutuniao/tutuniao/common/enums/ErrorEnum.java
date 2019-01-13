package com.tutuniao.tutuniao.common.enums;

public enum ErrorEnum {

    OPTION_TYPE("13000", "操作类型为空，请确认"),
    REQUEST_TYPE("13001", "操作类型为空，请确认"),
    RESOURCE_TYPE("13002", "资源类型不匹配，请确认"),

    INSERT_DATA_ERROR("110001", "插入数据失败"),
    UPDATE_DATA_ERROR("110002", "修改输入失败");


    private String code;
    private String name;

    ErrorEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
