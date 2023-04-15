package com.niu.web.business.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wq
 */
public final class CalendarUtils {
    
    /**
     * 格式化输出日期字符串
     * @param date
     * @param pattern
     * @return
     */
    public static final String toString(Date date,String pattern) {
        if(date==null){
            return "";
        }
        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyy-MM-dd";
        }
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }
    /**
     * 将日期字符串转化为日期
     * @param dateStr
     * @param pattern
     * @return
     */
    public static final Date parseDateStrToDate(String dateStr,String pattern) {
        if(StringUtils.isBlank(dateStr)){
            return null;
        }
        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyy-MM-dd";
        }
        SimpleDateFormat dateFormattor = new SimpleDateFormat(pattern);
        try {
            return dateFormattor.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
    /**
     * 将日期字符串转化为日期(严格要求日期格式)
     * @param dateStr
     * @param pattern
     * @return
     */
    public static final Date parseDateStrToDateLenient(String dateStr,String pattern) {
        if(StringUtils.isBlank(dateStr)){
            return null;
        }
        if (StringUtils.isBlank(pattern)) {
            pattern = "yyyy-MM-dd";
        }
        SimpleDateFormat dateFormattor = new SimpleDateFormat(pattern);
        try {
            dateFormattor.setLenient(false);//严格要求日期格式
            return dateFormattor.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }
    
    /**
     * 修改日期到参数日期的凌晨(00:00:00)
     * @return
     */
    public static final Date patchDateToDayStart(Date src) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(src);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        return calendar.getTime();
    }
	/**
	 * 修改日期到参数日期的午夜(23:59:59)
	 * @return
	 */
	public static final Date patchDateToDayMidnight(Date src) {
	    if (src==null){
	        return null;
        }
		Calendar calendar = Calendar.getInstance();
		calendar.setMinimalDaysInFirstWeek(7);
		calendar.setTime(src);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		//BUGFIX: sqlserver的datetime只能精确到1/300秒，999毫秒会四舍五入到第二天了 mysql 500毫秒会四舍五入到第二天了
		calendar.set(Calendar.MILLISECOND, 499);
		
		return calendar.getTime();
	}
	/**
     * 修改日期到 当时周第一天凌晨(00:00:00)
     * @param date
     * @return
     */
    public static Date patchDateToWeekBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Monday
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        return calendar.getTime();
    }
    /**
     * 修改日期到 当时周最后一天午夜(23:59:59)
     * @param date
     * @return
     */
    public static Date patchDateToWeekEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6); // Sunday
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        //BUGFIX: sqlserver的datetime只能精确到1/300秒，999毫秒会四舍五入到第二天了 mysql 500毫秒会四舍五入到第二天了
        calendar.set(Calendar.MILLISECOND, 499);
        return calendar.getTime();
    }
    /**
     * 修改日期到 当时月第一天凌晨(00:00:00)
     * @param date
     * @return
     */
    public static Date patchDateToMonthBegin(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    /**
     * 修改日期到 当时月最后一天午夜(23:59:59)
     * @param date
     * @return
     */
    public static Date patchDateToMonthEnd(Date date) {
        int m=getMonthByDate(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, m);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        //BUGFIX: sqlserver的datetime只能精确到1/300秒，999毫秒会四舍五入到第二天了 mysql 500毫秒会四舍五入到第二天了
        calendar.set(Calendar.MILLISECOND, 499);
        return calendar.getTime();
    }
    /**
     * 修改日期到 当时月第一天凌晨(00:00:00)
     * @param date
     * @return
     */
    public static Date patchDateToSeasonBegin(Date date) {
        int month=getMonthByDate(date);
        if (month < 4){
            month=1;
        }else if (month < 7){
            month=4;
        }else if (month < 10){
            month=7;
        }else{
            month=10;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    /**
     * 修改日期到 当时月最后一天午夜(23:59:59)
     * @param date
     * @return
     */
    public static Date patchDateToSeasonEnd(Date date) {
        int month=getMonthByDate(date);
        if (month < 4){
            month=1;
        }else if (month < 7){
            month=4;
        }else if (month < 10){
            month=7;
        }else{
            month=10;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(date);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        //BUGFIX: sqlserver的datetime只能精确到1/300秒，999毫秒会四舍五入到第二天了 mysql 500毫秒会四舍五入到第二天了
        calendar.set(Calendar.MILLISECOND, 499);
        return calendar.getTime();
    }
	/**
     * 修改日期到 当年第一天凌晨(00:00:00)
     */
    public static final Date patchDateToYearBegin(Date src) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(src);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    /**
     * 修改日期到 当年最后一天午夜(23:59:59)
     */
    public static final Date patchDateToYearEnd(Date src) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(src);
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, 31);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
      //BUGFIX: sqlserver的datetime只能精确到1/300秒，999毫秒会四舍五入到第二天了 mysql 500毫秒会四舍五入到第二天了
        calendar.set(Calendar.MILLISECOND, 499);
        return calendar.getTime();
    }
	/**
	 * 修改日期的时间
	 * @param src
	 * @param hour
	 * @param minutes
	 * @param seconds
	 * @return
	 */
	public static final Date modifyDateTime(Date src,int hour,int minutes,int seconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setMinimalDaysInFirstWeek(7);
		calendar.setTime(src);
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minutes);
		calendar.set(Calendar.SECOND, seconds);
		return calendar.getTime();
	}
	/**
     * 根据月份获取季度
     * @param month
     * @return
     */
    public static int getSeasonByMonth(int month){
        if (month < 4)
            return 1;
        if (month < 7)
            return 2;
        if (month < 10)
            return 3;
        if (month <= 12)
            return 4;
        
        throw new RuntimeException("error month:" + month);
    }
    /**
     * 根据日期和月份取得月份的天数
     * @param d
     * @param month
     * @return
     */
    public static int getDayNumByDateAndMonth(Date d, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.MONTH,month-1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
	/**
     * 根据日期获取年度数字
     * @param src
     * @return
     */
    public static final int getYearByDate(Date src) {
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(src);
        return calendar.get(Calendar.YEAR);
    }
    /**
     * 根据日期获取季度数字
     * @return
     */
    public static final int getSeasonByDate(Date src) {
        return getSeasonByMonth(getMonthByDate(src));
    }
    /**
     * 根据日期获取月份数字
     * @return
     */
    public static final int getMonthByDate(Date src) {
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(src);
        return calendar.get(Calendar.MONTH)+1;
    }
    /**
     * 根据日期获取周数字
     * @param date
     * @return
     */
    public static int getWeekByDateOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setMinimalDaysInFirstWeek(7);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(new Date());
        return c.get(Calendar.WEEK_OF_YEAR);
    }
    /**
     * 根据日期获取周数字
     * @param date
     * @return
     */
    public static WeekValObj getWeekValObjByDateOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setMinimalDaysInFirstWeek(7);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        int week = 0;
        int year = getYearByDate(date);
        if(month>=11){
            c.add(Calendar.DAY_OF_MONTH,-7);//防止最后一周跨年bug 先减去一周的时间 最后结果加1周
            week=c.get(Calendar.WEEK_OF_YEAR)+1;
        }else{
            week=c.get(Calendar.WEEK_OF_YEAR);
        }
        if(month==0 && week>40){
            year = year-1;
        }
        return new WeekValObj(year, week);
    }
    /**
     * 根据日期获取周数字 针对月份
     * @param date
     * @return
     */
    public static int getWeekByDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setMinimalDaysInFirstWeek(7);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_MONTH);
    }
    /**
     * 根据日期获取周数字
     * @param date
     * @return
     */
    public static WeekValObj getWeekValObjByDateOfMonth(Date date) {
    	Calendar c = Calendar.getInstance();
        c.setMinimalDaysInFirstWeek(7);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int week = c.get(Calendar.WEEK_OF_MONTH);
        return new WeekValObj(year, month+1, week);
    }
    /**
     * 根据日期获取日数字
     * @return
     */
    public static final int getDayByDate(Date src) {
        Calendar calendar= Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(src);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    /**
     * 根据日期获取相对于年度的日数字
     * @return
     */
    public static final int getDayByDateOfYear(Date src) {
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(src);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }
    /**
     * 获取当前年的周数
     * @param month
     * @return
     */
    public static final int getCurrentYearWeekNums() {
        Calendar calendar= Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(new Date());
        return calendar.getWeeksInWeekYear();
    }
	/**
	 * 得到当前年度数字
	 * @return
	 */
	public static final int getCurrentYear() {
		Calendar calendar= Calendar.getInstance();
		calendar.setMinimalDaysInFirstWeek(7);
		calendar.setTime(new Date());
		return calendar.get(Calendar.YEAR);
	}
    /**
     * 得到当前季度数字
     * @return
     */
    public static final int getCurrentSeason() {
        return getSeasonByMonth(getCurrentMonth());
    }
	/**
     * 得到当前月份数字
     * @return
     */
    public static final int getCurrentMonth() {
        Calendar calendar= Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(new Date());
        return calendar.get(Calendar.MONTH)+1;
    }
    /**
     * 根据日期获取周数字
     * @param
     * @return
     */
    public static int getCurrentWeek() {
        Calendar c = Calendar.getInstance();
        c.setMinimalDaysInFirstWeek(7);
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(new Date());
        return c.get(Calendar.WEEK_OF_YEAR);
    }
    /**
     * 得到当前日数字
     * @return
     */
    public static final int getCurrentDay() {
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    /**
     * 得到当前相对于周的日数字
     * @param
     * @return
     */
    public static final int getCurrentDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(new Date());
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        return week-1;
    }
    /**
     * 得到当前相对于年度的日数字
     * @return
     */
    public static final int getCurrentDayOfYear() {
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.DAY_OF_YEAR);
    }
    /**
     * 得到当前小时数字
     * @return
     */
    public static final int getCurrentHour() {
        Calendar calendar= Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(new Date());
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
    /**
     * 得到当前分钟数字
     * @return
     */
    public static final int getCurrentMinute() {
        Calendar calendar= Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(new Date());
        return calendar.get(Calendar.MINUTE);
    }
    /**
     * 得到当前秒钟数字
     * @return
     */
    public static final int getCurrentSecond() {
        Calendar calendar= Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(new Date());
        return calendar.get(Calendar.SECOND);
    }
    /**
     * 得到当前年的开始日期凌晨
     */
    public static Date getCurrentYearBeginDate() {
        return patchDateToYearBegin(new Date());
    }
    /**
     * 得到当前年的结束日期午夜
     */
    public static Date getCurrentYearEndDate() {
        return patchDateToYearEnd(new Date());
    }
    /**
     * 根据季度获取季度第一天凌晨
     * @param month
     * @return
     */
    public static Date getSeasonBeginByMonthOfCurrentYear(int season) {
        if (season==1){
            return getMonthBeginByMonthOfCurrentYear(1);
        }else if (season==2){
            return getMonthBeginByMonthOfCurrentYear(4);
        }else if (season==3){
            return getMonthBeginByMonthOfCurrentYear(7);
        }else if (season==4){
            return getMonthBeginByMonthOfCurrentYear(10);
        }
        return null;
    }
     /**
     * 根据季度获取季度最后一天午夜
     * @param month
     * @return
     */
    public static Date getSeasonEndByMonthOfCurrentYear(int season) {
        if (season==1){
            return getMonthEndByMonthOfCurrentYear(1);
        }else if (season==2){
            return getMonthEndByMonthOfCurrentYear(6);
        }else if (season==3){
            return getMonthEndByMonthOfCurrentYear(9);
        }else if (season==4){
            return getMonthEndByMonthOfCurrentYear(12);
        }
        return null;
    }
	/**
	 * 根据月份获取月份第一天凌晨
	 * @param month
	 * @return
	 */
	public static Date getMonthBeginByMonthOfCurrentYear(int month) {
		return getMonthBeginByYearAndMonth(getYearByDate(new Date()), month);
	}
	 /**
     * 根据月份获取月份最后一天午夜
     * @param month
     * @return
     */
    public static Date getMonthEndByMonthOfCurrentYear(int month) {
       return getMonthEndByYearAndMonth(getYearByDate(new Date()), month);
    }
    /**
     * 根据周数获取第n周第一天凌晨
     * @param month
     * @return
     */
    public static Date getWeekBeginByMonthOfCurrentYear(int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(new Date());
        int weeks=calendar.getWeeksInWeekYear();
        if(weeks>=week){
            calendar.set(Calendar.WEEK_OF_YEAR,week);
            calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Monday
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime(); 
        }
        return null;
    }
     /**
     * 根据周数获取第n周最后一天午夜
     * @param month
     * @return
     */
    public static Date getWeekEndByMonthOfCurrentYear(int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(new Date());
        int weeks=calendar.getWeeksInWeekYear();
        if(weeks>=week){
            calendar.set(Calendar.WEEK_OF_YEAR,week);
            calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()+6); // Sunday
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
          //BUGFIX: sqlserver的datetime只能精确到1/300秒，999毫秒会四舍五入到第二天了 mysql 500毫秒会四舍五入到第二天了
            calendar.set(Calendar.MILLISECOND, 499);
            return calendar.getTime(); 
        }
        return null;
    }
    /**
     * 根据年份获取年的周数
     * @param month
     * @return
     */
    public static final int getMaxWeekCountByYear(int year){
    	Calendar calendar= Calendar.getInstance();
    	calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(parseDateStrToDate(year+"-12-1","yyyy-MM-dd"));
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar.getWeeksInWeekYear();
    }
    /**
     * 根据日期获取年的周数
     * @param month
     * @return
     */
    public static final int getWeekNumsByDate(Date date) {
        Calendar calendar= Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        return calendar.getWeeksInWeekYear();
    }
    /**
     * 根据年份获取年度第一天凌晨
     * @param month
     * @return
     */
    public static Date getYearBeginByDate(Date date) {
        Calendar calendar= Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(date);
        int year=calendar.get(Calendar.YEAR);
        return getYearBeginByYear(year);
    }
    /**
     * 根据年份获取年份最后一天午夜
     * @param year
     * @return
     */
    public static Date getYearEndByDate(Date date) {
        Calendar calendar= Calendar.getInstance();
        calendar.setTime(date);
        int year=calendar.get(Calendar.YEAR);
        return getYearEndByYear(year);
    }
    /**
     * 根据年季获取年季的开始日期凌晨
     * @param year
     * @param month
     * @return
     */
    public static Date getSeasonBeginByDate(Date date) {
        Calendar calendar= Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(date);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        if (month<3)
            return getMonthBeginByYearAndMonth(year, 1);
        if (month<6)
            return getMonthBeginByYearAndMonth(year, 4);
        if (month<9)
            return getMonthBeginByYearAndMonth(year, 7);
        if (month<12)
            return getMonthBeginByYearAndMonth(year, 10);
        return null;
    }
    /**
     * 根据年季获取年季的开始日期凌晨
     * @param year
     * @param month
     * @return
     */
    public static Date getSeasonEndByDate(Date date) {
        Calendar calendar= Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(date);
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        if (month<3)
            return getMonthEndByYearAndMonth(year, 3);
        if (month<6)
            return getMonthEndByYearAndMonth(year, 6);
        if (month<9)
            return getMonthEndByYearAndMonth(year, 9);
        if (month<12)
            return getMonthEndByYearAndMonth(year, 12);
        return null;
    }
    /**
     * 根据日期获取月的开始日期凌晨
     * @param year
     * @param month
     * @return
     */
    public static Date getMonthBeginByDate(Date date) {
    	int year = getYearByDate(date);
    	int month = getMonthByDate(date);
        return getMonthBeginByYearAndMonth(year, month);
    }
    /**
     * 根据日期月的结束日期午夜
     * @param year
     * @param month
     * @return
     */
    public static Date getMonthEndByDate(Date date) {
    	int year = getYearByDate(date);
    	int month = getMonthByDate(date);
        return getMonthEndByYearAndMonth(year, month);
    }
    /**
     * 根据年周获取年份第n周第一天凌晨
     * @param month
     * @return
     */
    public static Date getWeekBeginByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Monday
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);  
        return calendar.getTime(); 
    }
     /**
     * 根据年周获取年份第n周最后一天午夜
     * @param month
     * @return
     */
    public static Date getWeekEndByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()+6); // Sunday
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
      //BUGFIX: sqlserver的datetime只能精确到1/300秒，999毫秒会四舍五入到第二天了 mysql 500毫秒会四舍五入到第二天了
        calendar.set(Calendar.MILLISECOND, 499);
        return calendar.getTime(); 
    }
    /**
     * 根据年份获取年的周数
     * @param month
     * @return
     */
    public static final int getWeekNumsByYear(int year) {
        Calendar calendar= Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.YEAR,year);
        return calendar.getWeeksInWeekYear();
    }
    /**
     * 根据年份获取年度第一天凌晨
     * @param month
     * @return
     */
    public static Date getYearBeginByYear(int year) {
        return parseDateStrToDate(year+"-01-01","yyyy-MM-dd");
    }
    /**
     * 根据年份获取年份最后一天午夜
     * @param year
     * @return
     */
    public static Date getYearEndByYear(int year) {
    	//BUGFIX: sqlserver的datetime只能精确到1/300秒，999毫秒会四舍五入到第二天了 mysql 500毫秒会四舍五入到第二天了
        return parseDateStrToDate(year+"-12-31 23:59:59 499","yyyy-MM-dd HH:mm:ss SSS");
    }
    /**
     * 根据年季获取年季的开始日期凌晨
     * @param year
     * @param month
     * @return
     */
    public static Date getSeasonBeginByYearAndSeason(int year,int season) {
        if (season == 1)
            return getMonthBeginByYearAndMonth(year, 1);
        if (season == 2)
            return getMonthBeginByYearAndMonth(year, 4);
        if (season == 3)
            return getMonthBeginByYearAndMonth(year, 7);
        if (season == 4)
            return getMonthBeginByYearAndMonth(year, 10);
        return null;
    }
    /**
     * 根据年季获取年季的开始日期凌晨
     * @param year
     * @param month
     * @return
     */
    public static Date getSeasonEndByYearAndSeason(int year,int season) {
        if (season == 1)
            return getMonthEndByYearAndMonth(year, 3);
        if (season == 2)
            return getMonthEndByYearAndMonth(year, 6);
        if (season == 3)
            return getMonthEndByYearAndMonth(year, 9);
        if (season == 4)
            return getMonthEndByYearAndMonth(year, 12);
        return null;
    }

    /**
     * 根据年、指定半年获取半年开始日期凌晨00:00:00
     * @param year
     * @param halfOfYear
     * @return
     */
    public static Date getHalfYearBeginByYearAndHalfOfYear(int year,int halfOfYear){
        if(halfOfYear == 1){
            return getMonthBeginByYearAndMonth(year, 1);
        }else if(halfOfYear == 2){
            return getMonthBeginByYearAndMonth(year, 7);
        }
        return null;
    }
    /**
     * 根据年、指定半年获取半年结束日期23:59:59
     * @param year
     * @param halfOfYear
     * @return
     */
    public static Date getHalfYearEndByYearAndHalfOfYear(int year,int halfOfYear){
        if(halfOfYear == 1){
            return getMonthEndByYearAndMonth(year, 6);
        }else if(halfOfYear == 2){
            return getMonthEndByYearAndMonth(year, 12);
        }
        return null;
    }
    /**
     * 根据年月获取年月的开始日期凌晨
     * @param year
     * @param month
     * @return
     */
    public static Date getMonthBeginByYearAndMonth(int year,int month) {
        return parseDateStrToDate(year+"-"+month+"-1","yyyy-MM-dd");
    }
    /**
     * 根据年月获取年月的结束日期午夜
     * @param year
     * @param month
     * @return
     */
    public static Date getMonthEndByYearAndMonth(int year,int month) {
    	Date nextMonthBegin = DateUtils.addMonths(parseDateStrToDate(year+"-"+month+"-1","yyyy-MM-dd"),1);
    	//BUGFIX: sqlserver的datetime只能精确到1/300秒，999毫秒会四舍五入到第二天了
        return DateUtils.addMilliseconds(nextMonthBegin, -501);
    }
    /**
     * 根据年月取得月份的天数
     * @param year
     * @param month
     * @return
     */
    public static int getDayNumByYearAndMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setMinimalDaysInFirstWeek(7);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH,month-1);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    /**
     * 根据年周获取年份第n周第一天凌晨
     * @param month
     * @return
     */
    public static Date getWeekBeginByYearAndWeek(int year,int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.YEAR,year);
        int weeks=calendar.getWeeksInWeekYear();
        if(weeks>=week){
            calendar.set(Calendar.WEEK_OF_YEAR, week);
            calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()); // Monday
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime(); 
        }
        return CalendarUtils.getWeekBeginByYearAndWeek(year+1, week-weeks);
    }
     /**
     * 根据年周获取年份第n周最后一天午夜
     * @param month
     * @return
     */
    public static Date getWeekEndByYearAndWeek(int year,int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.YEAR,year);
        int weeks=calendar.getWeeksInWeekYear();
        if(weeks>=week){
            calendar.set(Calendar.WEEK_OF_YEAR, week);
            calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek()+6); // Sunday
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
          //BUGFIX: sqlserver的datetime只能精确到1/300秒，999毫秒会四舍五入到第二天了 mysql 500毫秒会四舍五入到第二天了
            calendar.set(Calendar.MILLISECOND, 499);
            return calendar.getTime(); 
        }
        return CalendarUtils.getWeekEndByYearAndWeek(year+1, week-weeks);
    }
    /**
     * 根据年周周几获取日期凌晨
     * @param year
     * @param weekNo
     * @param weekDay
     * @return
     */
    public static Date getDateBeginByYearAndWeekAndWeekDay(int year, int week, int weekDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.YEAR,year);
        int weeks=calendar.getWeeksInWeekYear();
        if(weeks>=week){
            calendar.set(Calendar.WEEK_OF_YEAR, week);
            calendar.set(Calendar.DAY_OF_WEEK, weekDay);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime(); 
        }
        return CalendarUtils.getDateBeginByYearAndWeekAndWeekDay(year+1, week-weeks, weekDay);
    }
    /**
     * 根据年周周几获取日期午夜
     * @param year
     * @param weekNo
     * @param weekDay
     * @return
     */
    public static Date getDateEndByYearAndWeekAndWeekDay(int year, int week, int weekDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.YEAR,year);
        int weeks=calendar.getWeeksInWeekYear();
        if(weeks>=week){
            calendar.set(Calendar.WEEK_OF_YEAR, week);
            calendar.set(Calendar.DAY_OF_WEEK, weekDay);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
          //BUGFIX: sqlserver的datetime只能精确到1/300秒，999毫秒会四舍五入到第二天了 mysql 500毫秒会四舍五入到第二天了
            calendar.set(Calendar.MILLISECOND, 499);
            return calendar.getTime(); 
        }
        return CalendarUtils.getDateEndByYearAndWeekAndWeekDay(year+1, week-weeks, weekDay);
    }
    /**
     * 判断是否是SATURDAY
     * @param date
     * @return
     */
	public static boolean isSaturday(Date date) {
		int week = getDayOfWeekByDate(date);
		return week == 6;
	}
	/**
     * 判断是否是SUNDAY
     * @param date
     * @return
     */
    public static boolean isSunday(Date date) {
        int week = getDayOfWeekByDate(date);
        return week == 0;
    }
	/**
	 * 获取日期的周几
	 * @param date
	 * @return
	 */
    public static int getDayOfWeekByDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setMinimalDaysInFirstWeek(7);
		calendar.setTime(date);
		int week = calendar.get(Calendar.DAY_OF_WEEK);
		return week-1;
	}
	/**
	 * 根据类型获取 开始和结束日期
	 * @param statType
	 * @param year
	 * @param typeToNum 类型对应的数字
	 * @param season
	 * @throws ParseException
	 */
	public static Date[] getBeginAndEndDateByType(String statType,int year,int typeToNum){
		Date[] dates = new Date[2];
		if ("year".equals(statType)) {
			dates[0] = getYearBeginByYear(year);
			dates[1] = getYearEndByYear(year);
		}else if ("season".equals(statType)) {
			dates[0] = getSeasonBeginByYearAndSeason(year, typeToNum);
			dates[1] = getSeasonEndByYearAndSeason(year, typeToNum);
		}else if ("month".equals(statType)) {
            dates[0] = getMonthBeginByYearAndMonth(year, typeToNum);
            dates[1] = getMonthEndByYearAndMonth(year, typeToNum);
        }else if ("week".equals(statType)) {
			dates[0] = getWeekBeginByYearAndWeek(year, typeToNum);
			dates[1] = getWeekEndByYearAndWeek(year, typeToNum);
		}
		return dates;
	}
	
	/**
     * 偏移小时数
     * @param date
     * @param hours(秒)
     * @return
     */
    public static Date offsetHours(Date d, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(d);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
    /**
     * 偏移小时数 并且转换到0分
     * @param date
     * @param hours(秒)
     * @return
     */
    public static Date offsetHoursAndPatchToMinuteStart(Date d, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(d);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    /**
     * 偏移小时数 并且转换到59分59秒
     * @param date
     * @param hours(秒)
     * @return
     */
    public static Date offsetHoursAndPatchToMinuteEnd(Date d, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(d);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        //BUGFIX: sqlserver的datetime只能精确到1/300秒，999毫秒会四舍五入到第二天了 mysql 500毫秒会四舍五入到第二天了
        calendar.set(Calendar.MILLISECOND, 499);
        return calendar.getTime();
    }
    /**
     * 偏移分钟
     * @param date
     * @param minutes
     * @return
     */
    public static Date offsetSeconds(Date d, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(d);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }
    /**
     * 偏移分钟
     * @param date
     * @param minutes
     * @return
     */
    public static Date offsetMinutes(Date d, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(d);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }
    /**
     * 偏移小时数 并且转换到0秒
     * @param date
     * @param minutes
     * @return
     */
    public static Date offsetMinutesAndPatchToSecondStart(Date d, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(d);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    /**
     * 偏移小时数 并且转换到59秒
     * @param date
     * @param minutes
     * @return
     */
    public static Date offsetMinutesAndPatchToSecondEnd(Date d, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(d);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 59);
        //BUGFIX: sqlserver的datetime只能精确到1/300秒，999毫秒会四舍五入到第二天了 mysql 500毫秒会四舍五入到第二天了
        calendar.set(Calendar.MILLISECOND, 499);
        return calendar.getTime();
    }
	/**
	 * 将日期偏移多少天
	 * @param 起始日期
	 * @param days偏移天数
	 */
	public static Date offsetDays(Date d,int days) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(d);
        calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}
	/**
     * 将日期偏移多少天并且转换到凌晨
     * @param 起始日期
     * @param days偏移天数
     */
    public static Date offsetDaysAndPatchToDateStart(Date d,int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(d);
        calendar.add(Calendar.DAY_OF_MONTH,days);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    /**
     * 将日期偏移多少天 并且转换到午夜
     * @param 起始日期
     * @param days偏移天数
     */
    public static Date offsetDaysAndPatchToDateMidnight(Date d,int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(d);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        //BUGFIX: sqlserver的datetime只能精确到1/300秒，999毫秒会四舍五入到第二天了 mysql 500毫秒会四舍五入到第二天了
        calendar.set(Calendar.MILLISECOND, 499);
        return calendar.getTime();
    }
	/**
	 * 将日期偏移多少月
     * @param 起始日期
     * @param months偏移月份
	 */
	public static Date offsetMonths(Date d, int months) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
	}
    /**
     * 将日期偏移多少月 并且转换到凌晨
     * @param 起始日期
     * @param months偏移月份
     */
    public static Date offsetMonthsAndPatchToDateStart(Date d, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, months);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    /**
     * 将日期偏移多少月 并且转换到午夜
     * @param 起始日期
     * @param months偏移月份
     */
    public static Date offsetMonthsAndPatchToDateMidnight(Date d, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(d);
        calendar.add(Calendar.MONTH, months);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        //BUGFIX: sqlserver的datetime只能精确到1/300秒，999毫秒会四舍五入到第二天了 mysql 500毫秒会四舍五入到第二天了
        calendar.set(Calendar.MILLISECOND, 499);
        return calendar.getTime();
    }
    /**
     * 将日期偏移多少年
     * @param 起始日期 
     * @param years偏移年数
     */
    public static Date offsetYears(Date d, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(d);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }
    /**
     * 将日期偏移多少年 并且转换到凌晨
     * @param 起始日期 
     * @param years偏移年数
     */
    public static Date offsetYearsAndPatchToDateStart(Date d, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(d);
        calendar.add(Calendar.YEAR, years);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    /**
     * 将日期偏移多少年 并且转换到午夜
     * @param 起始日期 
     * @param years偏移年数
     */
    public static Date offsetYearsAndPatchToDateMidnight(Date d, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setMinimalDaysInFirstWeek(7);
        calendar.setTime(d);
        calendar.add(Calendar.YEAR, years);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        //BUGFIX: sqlserver的datetime只能精确到1/300秒，999毫秒会四舍五入到第二天了 mysql 500毫秒会四舍五入到第二天了
        calendar.set(Calendar.MILLISECOND, 499);
        return calendar.getTime();
    }
    
    /**
     * 获取日期间隔天数
     * @param d1
     * @param d2
     * @return
     */
	public static int getDatesIntervalDays(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		// 保证第二个时间一定大于第一个时间
		if (c1.after(c2)) {
			c1.setTime(d2);
			c2.setTime(d1);
		}
		int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		int betweenDays = c2.get(Calendar.DAY_OF_YEAR) - c1.get(Calendar.DAY_OF_YEAR);
		for (int i = 0; i < betweenYears; i++) {
			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
			betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
		}
		return betweenDays;
	}
	
	//获取日期间隔完整月数 如1.8到1.7间隔0个月1.8到1.8间隔1个月
    public static int getDatesIntervalFullMonths(Date date1, Date date2){
    	if(date1.after(date2)) {
    		Date d=date1;
    		date1=date2;
    		date2=d;
    	}
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH)+1;
        int month2 = c2.get(Calendar.MONTH)+1;
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        if(month2==month1&&day2<day1) {
        	month2--;
        }
        int yearInterval = year2-year1 ;
        int monthsDiff = Math.abs(yearInterval*12 + (month2-month1));
        return monthsDiff;
    }
	
    //获取日期间隔月数
    public static int getDatesIntervalMonths(Date date1, Date date2){
    	if(date1.after(date2)) {
    		Date d=date1;
    		date1=date2;
    		date2=d;
    	}
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        int month1 = c1.get(Calendar.MONTH)+1;
        int month2 = c2.get(Calendar.MONTH)+1;
        if(year1>year2) {//如果date1在date2年份以后  则调换位置  
        	int i=year1;
        	year1=year2;
        	year2=i;
        	i=month1;
        	month1=month2;
        	month2=i;
        }
        int yearInterval = year2-year1 ;
        int monthsDiff = Math.abs(yearInterval*12 + (month2-month1));
        return monthsDiff;
    }
    
    //获取日期间隔年数
	public static int getDatesIntervalYears(Date date1, Date date2) {
		 Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        return Math.abs(year2-year1);
	}

	/**
	 * 取得开始日期到结束日期之间的每月的第一天
	 * @param firstdate
	 * @param seconddate
	 * @return
	 */
	public static List<Date> getTwoDateList(Date firstdate, Date seconddate) {
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(firstdate.before(seconddate) ? firstdate: seconddate);
		Calendar endCalender = Calendar.getInstance();
		endCalender.setTime(firstdate.before(seconddate) ? seconddate: firstdate);
		
		List<Date> dateList = new ArrayList<Date>();
		int beginyear = beginCalendar.get(Calendar.YEAR);
		int beginmonth = beginCalendar.get(Calendar.MONTH);
		for (; (beginyear * 12 + beginmonth) <= (endCalender.get(Calendar.YEAR) * 12 + endCalender
				.get(Calendar.MONTH));) {
			Calendar temp = Calendar.getInstance();
			temp.set(beginyear, beginmonth, 1);
			dateList.add(temp.getTime());
			beginmonth++;
			if (beginmonth > 12) {
				beginyear++;
				beginmonth = 1;
			}
		}
		return dateList;
	}

    /**
     * 获取两个时间中的每一天
     * @param firstdate 开始时间
     * @param seconddate 结束时间
     * @return
     * @throws ParseException
     */
    public static List<Date> getTwoDateByDayList(Date firstdate, Date seconddate){
        LocalDate startDate = LocalDateTime.ofInstant(firstdate.toInstant(),ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = LocalDateTime.ofInstant(seconddate.toInstant(),ZoneId.systemDefault()).toLocalDate();

        long numOfDays = ChronoUnit.DAYS.between(startDate, endDate);
        List<LocalDate> listOfDates = Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(numOfDays)
                .collect(Collectors.toList());
        return listOfDates.stream()
                .map(item ->Date.from(item.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .collect(Collectors.toList());
    }

    /**
     * 根据日期获取中文年
     * @param src
     * @return
     */
    public static String getChinaYearByDate(Date src) {
        if (src == null) return null;
        return changNumToUpper(getYearByDate(src));
    }
	/**
	 * 取得中文月
	 * @param src
	 * @return
	 */
	public static String getChinaMonthByDate(Date src) {
		if (src == null) return null;
		return monthToUppder(getMonthByDate(src));
	}
	/**
	 * 取得中文日
	 * @param src
	 * @return
	 */
	public static String getChinaDay(Date src) {
		if (src == null) return null;
		return dayToUppder(getDayByDate(src));
	}
	/**
	 * 年月日的阿拉伯数字转成中文的数字
	 * @param changNum
	 * @param flag
	 * @return 其中：0代表0转成o，10代表0转成十
	 */
	public static String changNumToUpper(int chuangNum) {
		String u[] = { "○", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
		char[] changeStr = String.valueOf(chuangNum).toCharArray();
		String resultStr = "";
		for (int i = 0; i < changeStr.length; i++) {
			int numIndex = Integer.parseInt(changeStr[i] + "");
			resultStr = resultStr + u[numIndex];
		}
		return resultStr;
	}
	/**
	 * 月改为大写
	 * @param month
	 * @return
	 */
	public static String monthToUppder(int month) {
		if (month < 10) {
			return changNumToUpper(month);
		} else if (month == 10) {
			return "十";
		} else {
			return "十" + changNumToUpper(month - 10);
		}
	}
	/**
	 * 日改为大写
	 * @param day
	 * @return
	 */
	public static String dayToUppder(int day) {
		if (day < 20) {
			return monthToUppder(day);
		} else {
			char[] changeStr = String.valueOf(day).toCharArray();
			int numIndex = Integer.parseInt(changeStr[1] + "");
			if (changeStr[1] == '0') {
				return changNumToUpper(Integer.parseInt(changeStr[0] + ""))
						+ "十";
			} else {
				return changNumToUpper(Integer.parseInt(changeStr[0] + ""))
						+ "十" + changNumToUpper(numIndex);
			}
		}
	}
	
	/**
	 * 判断时间是否相等
	 * @param dateOne
	 * @param dateTwo
	 * @return
	 */
	public static boolean equals(Date dateOne, Date dateTwo) {
		if (dateOne == null && dateTwo == null) {
			return true;
		} else {
			if (dateOne == null || dateTwo == null) return false;
			return dateOne.getTime() == dateTwo.getTime();
		}
	}
	/**
	 * 判断日期是否是当天
	 */
	public static final boolean isCurrentDay(Date date){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar now = Calendar.getInstance();
		return df.format(date).equals(df.format(now.getTime()));
	}
	   /**
     * 根据时间判断是不是 日期所在年度 的第一天
     * @return
     */
    public static final boolean isYearFirstDayByDate(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (month==0 && day==1) {
            return true;
        }
        return false;
    }
    /**
     * 根据时间判断是不是 日期所在年度 的最后一天
     * @return
     */
    public static final boolean isYearLastDayByDate(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (month==12 && day==31) {
            return true;
        }
        return false;
    }
	/**
     * 根据时间判断是不是所 日期所在季度 的第一天
     * @return
     */
    public static final boolean isSeanonFirstDayByDate(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (month % 3 == 0 && day == 1) {
            return true;
        }
        return false;
    }
    /**
     * 根据时间判断是不是 日期所在季度 的最后一天
     * @return
     */
    public static final boolean isSeanonLastDayByDate(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int season=getSeasonByMonth(month);
        calendar.set(Calendar.MONTH,season*3-1);
        int lastDay=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (season*3==month && day == lastDay) {
            return true;
        }
        return false;
    }
    /**
     * 根据时间判断是不是 日期所在月份 的第一天
     * @return
     */
    public static final boolean isMonthFirstDayByDate(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day == 1) {
            return true;
        }
        return false;
    }
    /**
     * 根据时间判断是不是 日期所在月份 的最后一天
     * @return
     */
    public static final boolean isMonthLastDayByDate(Date d) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int lastDay=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        if (day == lastDay) {
            return true;
        }
        return false;
    }
    /**
     * 判断日期是否在开始和结束日期之间
     * @param d
     * @param begin
     * @param end
     * @return
     */
    public static final boolean isInBeginDateAndEndDateBetween(Date d,Date begin,Date end){
        if(d!=null){
            return (end==null||(!end.before(d)))&&(begin==null||!begin.after(d));
        }
        return false;
    }
	/**
	 * 获取显示的周几
	 * @param date
	 * @return
	 */
    private static final String[] weeknums = new String[]{"周日","周一","周二","周三","周四","周五","周六"};
    public static String getWeekNumDisplay(Date date) {
		if(date == null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setMinimalDaysInFirstWeek(7);
		calendar.setTime(date);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		int week = calendar.get(Calendar.DAY_OF_WEEK)-1;
		
		return weeknums[week];
	}
    /**
     * 比较两个日期字符串时间大小
     * @param date1Str
     * @param date2Str
     * @return
     */
    public static int compDate1StrDate2Str(String date1Str, String date2Str){
        Date d1 = CalendarUtils.parseDateStrToDate(date1Str, null);
        Date d2 = CalendarUtils.parseDateStrToDate(date2Str, null);
        if(d1==null&&d2!=null){
            return 1;
        }else if(d1!=null&&d2==null){
            return -1;
        }else {
            return d1.compareTo(d2);
        }
    }

    /**
     * 获取某一时间段特定星期几的日期
     *
     * @param dateFrom 开始时间
     * @param dateEnd  结束时间
     * @param weekDays 星期[1,2,3,4,5,6,7]
     * @return 返回时间数组
     */
    public static List<String> getDates(String dateFrom, String dateEnd, String weekDays) {
        long time = 1l;
        long perDayMilSec = 24 * 60 * 60 * 1000;
        List<String> dateList = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //需要查询的星期系数
        String strWeekNumber = weekForNum(weekDays);
        try {
            dateFrom = sdf.format(sdf.parse(dateFrom).getTime() - perDayMilSec);
            while (true) {
                time = sdf.parse(dateFrom).getTime();
                time = time + perDayMilSec;
                Date date = new Date(time);
                dateFrom = sdf.format(date);
                if (dateFrom.compareTo(dateEnd) <= 0) {
                    //查询的某一时间的星期系数
                    Integer weekDay = dayForWeek(date);
                    //判断当期日期的星期系数是否是需要查询的
                    if (strWeekNumber.indexOf(weekDay.toString()) != -1) {
                        dateList.add(dateFrom);
                    }
                } else {
                    break;
                }
            }
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return dateList;
    }
    //等到当期时间的周系数。星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7
    public static Integer dayForWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 得到对应星期的系数 星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7
     *
     * @param weekDays 星期格式
     */
    public static String weekForNum(String weekDays) {
        //返回结果为组合的星期系数
        String weekNumber = "";
        //解析传入的星期
        String[] split = weekDays.split(",");
        for (String weekDay : split) {
            weekNumber = weekNumber + "" + getWeekNum(weekDay).toString();
        }
        return weekNumber;
    }
    //将星期转换为对应的系数 星期日：1，星期一：2，星期二：3，星期三：4，星期四：5，星期五：6，星期六：7
    public static Integer getWeekNum(String strWeek){
        Integer number = 2;//默认为星期一
        if("1".equals(strWeek)){
            number = 2;//星期一
        }else if("2".equals(strWeek)){
            number = 3;//星期二
        }else if("3".equals(strWeek)){
            number = 4;//星期三
        }else if("4".equals(strWeek)){
            number = 5;//星期四
        }else if("5".equals(strWeek)){
            number = 6;//星期五
        }else if ("6".equals(strWeek)){
            number = 7;//星期六
        }else if ("7".equals(strWeek)){
            number = 1;//星期日
        }
        return number;
    }

    //获取指定月月份指定的日期
    public static List<String> getCycleDates(Date date,List<String> cycleDays){
        List<String> dateList = new ArrayList<>();
        if (cycleDays == null|| cycleDays.size() == 0) return dateList;
        // 下面是直接获取月份的开头日期。
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // DAY_OF_MONTH表示月份内的天数值，从1开始，0会迭代到上个自然月的最后一天。
        for (String cycleDay : cycleDays) {
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(cycleDay));
            dateList.add(toString(cal.getTime(),"yyyy-MM-dd"));
        }
        return dateList;
    }

    //获取指定月月份指定的日期
    public static List<String> getCycleDates(List<Date> dates,List<String> cycleDays){
        List<String> dateList = new ArrayList<>();
        if (dates == null || dates.size() == 0) return dateList;
        for (Date date : dates) {
            dateList.addAll(getCycleDates(date,cycleDays));
        }
        return dateList;
    }

}
