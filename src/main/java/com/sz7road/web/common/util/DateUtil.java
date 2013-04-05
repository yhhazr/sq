
package com.sz7road.web.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @date 2011-09-17, 16:33:04
 * @author long@7road.com
 * @description DateUtil:日期转换工具类
 */
public class DateUtil {

    public static String defaultSimpleFormater = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_FORMAT1 = "yyyy/MM/dd HH:mm:ss";

    /**
     * 默认简单日期字符串
     * 
     * @return
     */
    public static String getDefaultSimpleFormater() {
        return defaultSimpleFormater;
    }

    /**
     * 设置默认简单日期格式字符串
     * 
     * @param defaultFormatString
     */
    public static void setDefaultSimpleFormater(String defaultFormatString) {
        DateUtil.defaultSimpleFormater = defaultFormatString;
    }

    /**
     * 格式化日期
     * 
     * @param date
     * @param formatString
     * @return
     */
    public static String format(Date date, String formatString) {
        SimpleDateFormat df = new SimpleDateFormat(formatString);
        return df.format(date);
    }

    /**
     * 格式化日期(使用默认格式)
     * 
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, defaultSimpleFormater);
    }

    /**
     * 转换成日期
     * 
     * @param dateString
     * @param formatString
     * @return
     */
    public static Date parse(String dateString, String formatString) {
        SimpleDateFormat df = new SimpleDateFormat(formatString);
        try {
            return df.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 转换成日期(使用默认格式)
     * 
     * @param dateString
     * @return
     */
    public static Date parse(String dateString) {
        return parse(dateString, defaultSimpleFormater);
    }

    /**
     * 昨天
     * 
     * @return
     */
    public static Date yesterday() {
        return addDay(-1);
    }

    /**
     * 明天
     * 
     * @return
     */
    public static Date tomorrow() {
        return addDay(1);
    }

    /**
     * 现在
     * 
     * @return
     */
    public static Date now() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * 按日加
     * 
     * @param value
     * @return
     */
    public static Date addDay(int value) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, value);
        return now.getTime();
    }

    /**
     * 按日加,指定日期
     * 
     * @param date
     * @param value
     * @return
     */
    public static Date addDay(Date date, int value) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.DAY_OF_YEAR, value);
        return now.getTime();
    }

    /**
     * 按月加
     * 
     * @param value
     * @return
     */
    public static Date addMonth(int value) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MONTH, value);
        return now.getTime();
    }

    /**
     * 按月加,指定日期
     * 
     * @param date
     * @param value
     * @return
     */
    public static Date addMonth(Date date, int value) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.MONTH, value);
        return now.getTime();
    }

    /**
     * 按年加
     * 
     * @param value
     * @return
     */
    public static Date addYear(int value) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.YEAR, value);
        return now.getTime();
    }

    /**
     * 按年加,指定日期
     * 
     * @param date
     * @param value
     * @return
     */
    public static Date addYear(Date date, int value) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.YEAR, value);
        return now.getTime();
    }

    /**
     * 按小时加
     * 
     * @param value
     * @return
     */
    public static Date addHour(int value) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.HOUR_OF_DAY, value);
        return now.getTime();
    }

    /**
     * 按小时加,指定日期
     * 
     * @param date
     * @param value
     * @return
     */
    public static Date addHour(Date date, int value) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.HOUR_OF_DAY, value);
        return now.getTime();
    }

    /**
     * 按分钟加
     * 
     * @param value
     * @return
     */
    public static Date addMinute(int value) {
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, value);
        return now.getTime();
    }

    /**
     * 按分钟加,指定日期
     * 
     * @param date
     * @param value
     * @return
     */
    public static Date addMinute(Date date, int value) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.MINUTE, value);
        return now.getTime();
    }

    /**
     * 年份
     * 
     * @return
     */
    public static int year() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR);
    }

    /**
     * 月份
     * 
     * @return
     */
    public static int month() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.MONTH);
    }

    /**
     * 日(号)
     * 
     * @return
     */
    public static int day() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 小时(点)
     * 
     * @return
     */
    public static int hour() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.HOUR);
    }

    /**
     * 分钟
     * 
     * @return
     */
    public static int minute() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.MINUTE);
    }

    /**
     * 秒
     * 
     * @return
     */
    public static int second() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.SECOND);
    }

    /**
     * 星期几(礼拜几)
     * 
     * @return
     */
    public static int weekday() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 是上午吗?
     * 
     * @return
     */
    public static boolean isAm() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.AM_PM) == 0;
    }

    /**
     * 是下午吗?
     * 
     * @return
     */
    public static boolean isPm() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.AM_PM) == 1;
    }

    /**
    
     * @return 当前的时间戳 yyyy-MM-dd HH:mm:ss
    
     */
    public static String getNowUnix() {

        return DateTimeToUnix(new Date());

    }

    /**
     *  获取时间戳
     * @param date
     *      需要转化时间戳的时间
     * @return 时间戳
     */
    public static String DateTimeToUnix(Date date) {

        return Long.toString(date.getTime());

    }

    /**
     *  获取格式化时间字符串
     * 
     * @return 格式化时间字符串
     */
    public static String formatNow() {

        return DateUtil.format(DateUtil.now());

    }
    
    public static Timestamp getCurrentTimestamp(){
    	Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    	return currentTime;
    }
    
    /**
     *  将日期转换成当前月份1号的日期
     * 
     * @return 当前月的1号所对应的日期
     */
    public static Date coverToHourDate(Date date) {
    	 Calendar cal=Calendar.getInstance();
		 cal.setTimeInMillis(date.getTime());
		 cal.set(Calendar.MINUTE, 0);
		 cal.set(Calendar.SECOND, 0);
		 cal.set(Calendar.MILLISECOND, 0);
		 return new Date(cal.getTimeInMillis());
    }
    
    /**
     *  将日期转换成当前月份1号的日期
     * 
     * @return 当前月的1号所对应的日期
     */
    public static Date getFirstDayOfYear(Date date) {
    	Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.MONTH, cal.getActualMinimum(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return new Date(cal.getTimeInMillis());
    }
    
    public static Date getLastDayOfYear(Date date) {
    	Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.MONTH, cal.getActualMaximum(Calendar.MONTH));
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return new Date(cal.getTimeInMillis());
   }
}
