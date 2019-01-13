package com.tutuniao.tutuniao.util.response;


public class Response<T> {
    private String status;
    private T data;
    private String message;


    public Response() {
    }

    public Response(String status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }


    public Response(String status, T data) {
        this.status = status;
        this.data = data;
        if (ResponseCode.SUCCESS.getCode().equals(status)) {
            this.message = ResponseCode.SUCCESS.getDescription();
        }
    }

    public Response(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public Response(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Response{");
        sb.append("status=").append(this.status);
        sb.append(", data=").append(this.data);
        sb.append(", message='").append(this.message).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
