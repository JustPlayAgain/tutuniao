package com.tutuniao.tutuniao.util.response;

public enum ResponseCode {
    SUCCESS("ok", "success", "成功"),
    ERROR("error", "error", "失败"),
    SYSTEM_ERROR("1002", "system_error", "系统异常"),
    PARAM_ERROR("1003", "param_error", "参数异常"),
    SQL_ERROR("1004", "sql_error", "数据库异常"),
    NEED_LOGIN("1005", "need_login", "请先登录!"),
    SERVER_ERROR("1006", "system_error", "服务异常"),
    START_FAILD("1007", "system_error", "启动失败，任务已启动"),
    STOP_FAILD("1008", "system_error", "停止失败，任务已停止"),
    CHECKFAILD("1009", "param_error", "角色名称重复，请重新输入"),
    COMPLETEFAILD("10086", "complete_faild", "完成失败,有部分带宽用量尚未上传或驳回后尚未再次提交"),
    UPDATEFAILD("10087", "update_faild", "完成失败,修改带宽主表状态失败"),


    SAMPLE_USER_NOT_EXIST("10001", "sample_user_not_exist", "用户不存在!");

    private String code;
    private String msg;
    private String description;

    ResponseCode(String code, String msg, String description) {
        this.code = code;
        this.msg = msg;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ResponseCode{");
        sb.append("code=").append(this.code);
        sb.append(", msg='").append(this.msg).append('\'');
        sb.append(", description='").append(this.description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}