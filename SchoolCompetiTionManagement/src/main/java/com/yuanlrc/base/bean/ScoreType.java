package com.yuanlrc.base.bean;

/**
 * 评分类型
 */
public enum  ScoreType {
    SCORE_NOT_HAS(0,"未评分"),SCORE_IS_HAS(1,"已评分");
    private final int code;
    private final String info;

    ScoreType(int code, String info) {
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
