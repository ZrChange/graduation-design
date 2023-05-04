package com.yuanlrc.base.bean;

public enum  ApplyType {
    NOT_CHECK(0, "未报名"),
    CHECK(1, "报名通过");

    private Integer code;

    private String value;

    ApplyType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
