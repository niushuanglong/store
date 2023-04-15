package com.niu.web.business.utils;

/**
 * 周信息
 * @author Administrator
 */
public class WeekValObj {
    private int year;
    private int month;
    private int week;
    public WeekValObj(int year, int week) {
        super();
        this.year = year;
        this.week = week;
    }
    public WeekValObj(int year, int month, int week) {
        super();
        this.year = year;
        this.month = month;
        this.week = week;
    }
    public int getYear() {
        return year;
    }
    public int getMonth() {
		return month;
	}
	public int getWeek() {
        return week;
    }
    

}
