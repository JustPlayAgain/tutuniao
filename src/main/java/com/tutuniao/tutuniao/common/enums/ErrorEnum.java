package com.tutuniao.tutuniao.common.enums;

public enum ErrorEnum {


    PARAM_TYPE("13000", "参数异常，请确认"),
    EXCEL_ERROR("14000", "不是正确的excel格式，请确认后提交"),

    INSERT_DATA_ERROR("110001", "插入数据失败"),
    UPDATE_DATA_ERROR("110002", "修改输入失败"),
    DELETE_DATA_ERROR("110004", "删除失败，未找到数据"),
    SELECT_DATA_ERROR("110003", "查询数据失败"),

    GUOMEITEMPLATE_NULL("120000", "当前编号或姓名不存在，请确认后查询"),
    MATCHTEMPLATE_NULL("120002", "当前身份证或姓名不存在，请确认后查询"),
    GUOMEITEMPLATE_ERROR("120001", "查询信息为空，请确认");


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
