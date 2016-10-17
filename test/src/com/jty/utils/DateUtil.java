package com.jty.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.jty.utils.exception.BusinessException;

public class DateUtil {
	public static Date getYesterday(){
		Date dNow = new Date();   //当前时间
		Date dBefore = new Date();

		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
		dBefore = calendar.getTime();   //得到前一天的时间
		return dBefore;
	}
	public static String getTodayStr(String formatStr){
		return DateUtil.getDateStr(new Date(), formatStr);
	}
	public static String getDateStr(Date date,String formatStr){
		DateFormat format = new SimpleDateFormat(formatStr); 
		return format.format(date);
	}
	
	
	
	/**
	 * 
	 * @auther xf
	 * @date 2015-7-8 上午11:58:13
	 * @param monthStr
	 * @return
	 */
	public static Date getFirstDate(String  monthStr){
		if(StringUtils.isBlank(monthStr)){
			return null;
		}else if(monthStr.indexOf("-")<0){
			throw new BusinessException("月份字符串格式不正确",false);
		}
		String [] yearMonth = monthStr.split("-");
		Integer year = Integer.valueOf(yearMonth[0]);
		Integer month = Integer.valueOf(yearMonth[1]);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH, month>0?month-1:month);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 获取一个月的最后一天
	 * @auther xf
	 * @date 2015-7-8 上午11:58:13
	 * @param monthStr
	 * @return
	 */
	public static Date getLastDate(String  monthStr){
		if(StringUtils.isBlank(monthStr)){
			return null;
		}else if(monthStr.indexOf("-")<0){
			throw new BusinessException("月份字符串格式不正确",false);
		}
		String [] yearMonth = monthStr.split("-");
		Integer year = Integer.valueOf(yearMonth[0]);
		Integer month = Integer.valueOf(yearMonth[1]);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR,year);
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DAY_OF_MONTH, 0);
		//cal.set(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}
	
	
	
	public static void main(String[] args){
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.YEAR,2015);
//		cal.set(Calendar.MONTH, 6);
//		  cal.set(Calendar.DAY_OF_MONTH, 1);
//		  cal.add(Calendar.DAY_OF_MONTH, -1);
//		System.out.println("------dd:"+getDateStr(cal.getTime(), "yyyy-MM-dd"));
		System.out.println(getDateStr(getFirstDate("2014-06"), "yyyy-MM-dd HH:mm:ss"));
	}
}
