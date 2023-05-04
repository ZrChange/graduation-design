package com.yuanlrc.base.bean;

/**
 * 审批意见
 */
public enum ApproveType {
    AGREE(1,"同意"),REJECT(2,"拒绝");

    private int code;
    private String value;

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

    ApproveType(int code, String value) {
        this.code = code;
        this.value = value;
    }
}
