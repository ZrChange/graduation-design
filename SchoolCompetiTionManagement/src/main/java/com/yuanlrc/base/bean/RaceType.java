package com.yuanlrc.base.bean;

/**
 * 竞赛类型
 */
public enum RaceType {
    OPENNESS(1,"开放型"),PROFESSIONAL (2,"专业型");
    private final int code;
    private final String info;

    RaceType(int code, String info) {
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
