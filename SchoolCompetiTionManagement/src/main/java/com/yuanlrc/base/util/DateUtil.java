package com.yuanlrc.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    // 各种时间格式
    public static final SimpleDateFormat date_sdf = new SimpleDateFormat(
            "yyyy-MM-dd");
    // 各种时间格式
    public static final SimpleDateFormat yyyyMMdd = new SimpleDateFormat(
            "yyyyMMdd");
    // 各种时间格式
    public static final SimpleDateFormat date_sdf_wz = new SimpleDateFormat(
            "yyyy年MM月dd日");
    public static final SimpleDateFormat time_sdf = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm");
    public static final SimpleDateFormat yyyymmddhhmmss = new SimpleDateFormat(
            "yyyyMMddHHmmss");
    public static final SimpleDateFormat short_time_sdf = new SimpleDateFormat(
            "HH:mm");
    public static final SimpleDateFormat datetimeFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    // 以毫秒表示的时间
    private static final long DAY_IN_MILLIS = 24 * 3600 * 1000;
    private static final long HOUR_IN_MILLIS = 3600 * 1000;
    private static final long MINUTE_IN_MILLIS = 60 * 1000;
    private static final long SECOND_IN_MILLIS = 1000;

    // 指定模式的时间格式
    private static SimpleDateFormat getSDFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 当前日历，这里用中国时间表示
     *
     * @return 以当地时区表示的系统当前日历
     */
    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 505      * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
     * 506      *
     * 507      * @param src
     * 508      *            将要转换的原始字符窜
     * 509      * @param pattern
     * 510      *            转换的匹配格式
     * 511      * @return 如果转换成功则返回转换后的日期
     * 512      * @throws ParseException
     * 513      * @throws AIDateFormatException
     * 514
     */
    public static Date parseDate(String src, String pattern)
            throws ParseException {
        return getSDFormat(pattern).parse(src);
    }

    /**
     * 默认方式表示的系统当前日期，具体格式：时：分
     *
     * @return 默认日期按“时：分“格式显示
     */
    public static String formatShortTime() {
        Date time = getCalendar().getTime();
        return short_time_sdf.format(time);
    }


    /**
     * 开始时间和结束时间 HH:MM
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static String hhmm(long startTime, long endTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        Long result = endTime - startTime;    //获取两时间相差的毫秒数
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long hour = result % nd / nh;     //获取相差的小时数
        long min = result % nd % nh / nm;  //获取相差的分钟数
        long day = result / nd;
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");//初始化Formatter的转换格式。
        long hMiles = hour * 3600000;  //小时数转换成毫秒
        long mMiles = min * 60000;    //分钟数转换成毫秒
        long resulMiles = (hMiles + mMiles);
        //下面这段很重要 ,计算之后设置时区,不然会差几小时
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+00:00"));
        String resultFormat = formatter.format(resulMiles);
        return resultFormat;
    }
}
