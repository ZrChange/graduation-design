package com.yuanlrc.base.bean;

/**
 * 竞赛流程
 */
public enum CompetitionProcess {
    SIGNUP(1,"报名"),PRELIMINARIES (2,"初赛"),SECONDROUND (3,"复赛"),FINALS(4,"决赛"),END(5,"结束");
    private final int code;
    private final String info;

    CompetitionProcess(int code, String info) {
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
