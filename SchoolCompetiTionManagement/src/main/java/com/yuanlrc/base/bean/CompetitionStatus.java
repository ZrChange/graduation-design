package com.yuanlrc.base.bean;

/**
 * 参赛类型
 */
public enum CompetitionStatus {
    OVERDUE(1,"过期"),NOT_OVERDUE (2,"未过期");

    private final int code;
    private final String info;

    CompetitionStatus(int code, String info) {
        this.code=code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
