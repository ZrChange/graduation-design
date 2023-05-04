package com.yuanlrc.base.util;

import com.yuanlrc.base.bean.CodeMsg;
import com.yuanlrc.base.bean.Result;

import java.util.Date;

public class DatesUtil {
    public static boolean checkDateTime(Date startTime, Date endTime) {
        if (startTime.after(endTime)) {
            return false;
        }
        return true;
    }

}
