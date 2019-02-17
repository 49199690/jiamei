package cn.nongph.jiamei.core.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	private static final String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
	
	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss" };

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	public static String formatDate(Date date){
		return formatDate(date, "yyyy-MM-dd");
	}
	
	public static String formateDateTime(Date date){
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, String pattern) {
		if( date==null )
			return "";
		else
			return DateFormatUtils.format(date, pattern);
	}
	
    /**
     * 得到系统日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatSystemDateTime() {
        return formatDateTime(new Date(System.currentTimeMillis()));
    }

    /**
     * 得到系统日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss SSS）
     */
    public static String formatSystemDateTimeMillis() {
        return formatDate(new Date(System.currentTimeMillis()),"yyyy-MM-dd HH:mm:ss SSS");
    }
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	public static String getDayOfWeek(Date d){
		Calendar c = Calendar.getInstance();
		c.setTime( d );
		return weekDays[c.get( Calendar.DAY_OF_WEEK )-1];
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}
    
	public static Date getDateStart(Date date) {
		if(date==null) {
			return null;
		}
		return new Date( getDateStart( date.getTime() ) );
	}
	
	public static Long getDateStart(Long millis){
		Long l = millis + 8 * DateUtils.MILLIS_PER_HOUR;
		l -= l%MILLIS_PER_DAY;
		l -= 8 * DateUtils.MILLIS_PER_HOUR;
		return l;
	}
	
	public static Date getDateEnd(Date date) {
		if(date==null) {
			return null;
		}
		long l = date.getTime() + 8*MILLIS_PER_HOUR;
		l = l - l%MILLIS_PER_DAY + MILLIS_PER_DAY - 1;
		l -= 8 * MILLIS_PER_HOUR;
		return new Date( l );
	}
	
	public static Date tomorrow(){
		return new Date( System.currentTimeMillis()+MILLIS_PER_DAY );
	}
	
}
