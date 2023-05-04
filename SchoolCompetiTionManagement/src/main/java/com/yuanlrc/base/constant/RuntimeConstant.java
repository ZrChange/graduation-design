package com.yuanlrc.base.constant;

import java.util.Arrays;
import java.util.List;

/**
 * 系统运行时的常量
 *
 * @author Administrator
 */
public class RuntimeConstant {

    //登录拦截器无需拦截的url
    public static List<String> loginExcludePathPatterns = Arrays.asList(
            "/system/login",
            "/admin/css/**",
            "/system/auth_order",
            "/admin/fonts/**",
            "/admin/js/**",
            "/admin/images/**",
            "/error",
            "/admin/select2/**",
            "/cpacha/generate_cpacha",
            "/admin/kindeditor/**",
            "/upload/uploadFile",
            "/admin/bootstrap-select/**",
            "/admin/college/majorList",
            "/upload/**",
            "/common/**",
            "/download/**"
    );
    //权限拦截器无需拦截的url
    public static List<String> authorityExcludePathPatterns = Arrays.asList(
            "/system/login",
            "/system/index",
            "/system/auth_order",
            "/system/no_right",
            "/admin/css/**",
            "/admin/fonts/**",
            "/admin/js/**",
            "/admin/images/**",
            "/admin/select2/**",
            "/admin/bootstrap-select/**",
            "/admin/datepicker/**",
            "/error",
            "/cpacha/generate_cpacha",
            "/system/logout",
            "/system/update_userinfo",
            "/system/update_pwd",
            "/photo/view",
            "/admin/judge/judgeList",
            "/admin/kindeditor/**",
            "/upload/uploadFile",
            "/admin/college/majorList",
            "/admin/individualCompetition/**",
            "/admin/team/**",
            "/upload/**",
            "/admin/score/**",
            "/common/**",
            "/download/**",
            "/admin/clazz/**",
            "/admin/competition/**"
    );
}
