package com.yuanlrc.base.bean;

/**
 * 附件状态
 */
public enum  AccessoryType
{
    NOT_SUBMITTED(0, "未提交"),
    SUBMITTED(1, "已提交");

    public Integer code;

    public String value;

    AccessoryType(Integer code, String value) {
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
