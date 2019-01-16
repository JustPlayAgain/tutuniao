package com.tutuniao.tutuniao.common.cookie;

public enum CookieEnum {
    USER("_id");
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
