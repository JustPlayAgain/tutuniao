package com.tutuniao.tutuniao.common.cookie;

public enum CookieEnum {
    USER("UserEntity");
    private String key;

    CookieEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
