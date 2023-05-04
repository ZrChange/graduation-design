package com.yuanlrc.base.util;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.Result;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZrStringUtil {

    /**
     * 判断是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str)
    {
        if(str == null || str.trim().length() == 0)
            return true;

        return false;
    }

    /**
     * 判断是否是手机号
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone)
    {
        String chkphone = "^1[3|4|5|7|8]\\d{9}$";
        Pattern r = Pattern.compile(chkphone);
        Matcher m = r.matcher(phone);
        if (m.matches()){
            return true;
        }
        return false;
    }

    /**
     * 判断是否是邮箱
     * @param email
     * @return
     */
    public static boolean isEmail(String email)
    {
        //邮箱验证
        String pattern = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
        Pattern emailReg = Pattern.compile(pattern);
        Matcher m = emailReg.matcher(email);
        if (m.matches()) {
            return true;
        }

        return false;
    }

    /**
     * 身份证验证
     * @param card
     * @return
     */
    public static boolean isCard(String card)
    {
        String checkIdCard = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$";
        Pattern c = Pattern.compile(checkIdCard);
        Matcher m2 = c.matcher(card);
        if (m2.matches()){
            return true;
        }
        return false;
    }

}
