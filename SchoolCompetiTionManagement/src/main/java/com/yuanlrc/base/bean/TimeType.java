package com.yuanlrc.base.bean;

/**
 * 判断进入的页面的时间是否符合要求
 */
public enum  TimeType {
    TRUE(0, "时间正确"),
    ERROR(1, "时间错误");

    private Integer code;

    private String value;

    TimeType(Integer code, String value) {
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
