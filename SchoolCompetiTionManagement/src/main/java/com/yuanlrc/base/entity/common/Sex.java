package com.yuanlrc.base.entity.common;

public enum Sex {
    MALE(1,"男"),
    FEMALE(2, "女"),
    UNKNOWN(0, "未知");


    private int code;
    private String value;

    Sex(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
