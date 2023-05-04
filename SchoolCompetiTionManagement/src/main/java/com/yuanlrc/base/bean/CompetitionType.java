package com.yuanlrc.base.bean;

/**
 * 参赛类型
 */
public enum CompetitionType {
    PERSONAL(1,"个人"),TEAM (2,"团队");

    private final int code;
    private final String info;

    CompetitionType(int code, String info) {
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
