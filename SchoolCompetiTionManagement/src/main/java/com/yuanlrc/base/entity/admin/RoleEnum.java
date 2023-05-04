package com.yuanlrc.base.entity.admin;

/**
 * 角色枚举类
 */
public enum RoleEnum {

    SUPERADMINISTRATOR(1l,"超级管理员"),
    ADMINISTRATOR(2l,"普通管理员"),
    TEST(4l,"测试角色"),
    JUDGE(9l,"评委"),
    STUDENT(10l,"学生");


    private Long code;
    private String value;

    RoleEnum(Long code, String value) {
        this.code = code;
        this.value = value;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
