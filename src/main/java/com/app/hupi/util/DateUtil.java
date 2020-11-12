package com.app.hupi.util;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Objects;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.boot.SpringApplication;

import com.gitee.sunchenbin.mybatis.actable.manager.system.SysOracleCreateTableManager;

public class DateUtil extends DateUtils {

	// 系统默认日期格式
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	// 系统默认日期时间格式
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	// 8位日期格式
	public static final String DATE_FORMAT_8 = "yyyyMMdd";

	// 10位日期格式
	public static final String DATE_FORMAT_MM_DD_HH_MM = "MM-dd HH:mm";

	// 14位日期时间格式
	public static final String DATE_TIME_FORMAT_14 = "yyyyMMddHHmmss";

	public static final String DATE_TIME_FORMAT_22 = "yyyy-MM-dd HH:mm:ss.SSS";

	public static final String DATE_24TIME_FORMAT = "yyyy-mm-dd hh24:mi:ss";

	// 01. java.util.Date --> java.time.LocalDateTime
	public static LocalDateTime dateToLocalDateTime(Date date) {
		if (Objects.isNull(date)) {
			return null;
		}
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		return LocalDateTime.ofInstant(instant, zone);
	}

	// 02. java.util.Date --> java.time.LocalDate
	public static LocalDate dateToLocalDate(Date date) {
		if (Objects.isNull(date)) {
			return null;
		}
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		return localDateTime.toLocalDate();
	}

	// 03. java.util.Date --> java.time.LocalTime
	public static LocalTime dateToLocalTime(Date date) {
		if (Objects.isNull(date)) {
			return null;
		}
		Instant instant = date.toInstant();
		ZoneId zone = ZoneId.systemDefault();
		LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
		return localDateTime.toLocalTime();
	}

	// 04. java.time.LocalDateTime --> java.util.Date
	public static Date localDateTimeToDate(LocalDateTime localDateTime) {
		if (Objects.isNull(localDateTime)) {
			return null;
		}
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		return Date.from(instant);
	}

	// 05. java.time.LocalDate --> java.util.Date
	public static Date localDateToDate(LocalDate localDate) {
		if (Objects.isNull(localDate)) {
			return null;
		}
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
		return Date.from(instant);
	}

	// 06. java.time.LocalTime --> java.util.Date
	public static Date localTimeToDate(LocalDate localDate, LocalTime localTime) {
		if (Objects.isNull(localDate) || Objects.isNull(localTime)) {
			return null;
		}
		LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);
		ZoneId zone = ZoneId.systemDefault();
		Instant instant = localDateTime.atZone(zone).toInstant();
		return Date.from(instant);
	}

	/***
	 * localDateTime 转换成 String
	 * 
	 * @param dateTime
	 * @return
	 */
	public static String localDateTimeToString(LocalDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_24TIME_FORMAT);
		String formattedDateTime = dateTime.format(formatter); // "1986-04-08 12:30"
		return formattedDateTime;
	}

	/**
	 * 得到应用服务器当前日期时间，以默认格式显示。
	 *
	 * @return
	 */
	public static String getFormatedDateTime() {
		Date date = getCurrentDate();
		SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_TIME_FORMAT);
		return dateFormator.format(date);

	}

	/**
	 * 比较两个时间的大小 date1大于date2 返回true
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean compareFormatedDateTime(String date1, String date2, String pattern) {
		Date d1 = parseStrToDate(date1, pattern);
		Date d2 = parseStrToDate(date2, pattern);
		return d1.getTime() - d2.getTime() >= 0;
	}

	/**
	 * <p>
	 * Description: 时间以及时间格式相关的处理功能
	 * </p>
	 * <p>
	 * private static Logger logger = Logger.getLogger(DateUtil.class); /**
	 * 得到应用服务器当前日期，以默认格式显示。
	 *
	 * @return
	 */
	public static String getFormatedDate() {
		Date date = getCurrentDate();
		SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_FORMAT);
		return dateFormator.format(date);

	}

	/**
	 * 得到应用服务器的当前时间
	 *
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * 得到应用服务器的当前时间，毫秒。
	 *
	 * @return
	 */
	public static long getCurrentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * 得到应用服务器当前日期 按照指定的格式返回。
	 *
	 * @param pattern
	 *            格式类型，通过系统常量中定义，如：CapConstants.DATE_FORMAT_8
	 * @return
	 */
	public static String formatDate(String pattern) {

		Date date = new Date();
		SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);
		String str = "";
		if (date != null) {
			str = dateFormator.format(date);
		}

		return str;
	}

	/**
	 * 转换输入日期 按照指定的格式返回。
	 *
	 * @param date
	 * @param pattern
	 *            格式类型，通过系统常量中定义，如：CapConstants.DATE_FORMAT_8
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {

		if (date == null) {
			return "";
		}

		SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);
		String str = dateFormator.format(date);

		return str;
	}

	/**
	 * 将时间串按照默认格式CapConstants.DATE_FORMAT，格式化成Date。
	 *
	 * @param dateStr
	 * @return
	 */
	public static Date parseStrToDate(String dateStr) {

		if (null == dateStr || "".equals(dateStr)) {
			return null;
		}

		SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_FORMAT);

		Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

		return tDate;
	}

	/**
	 * 将时间串按照默认格式CapConstants.DATE_FORMAT，格式化成Date。
	 *
	 * @param dateStr
	 * @return
	 */
	public static Calendar parseStrToCalendar(String dateStr) {

		if (null == dateStr || "".equals(dateStr)) {
			return null;
		}

		SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_FORMAT);

		Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

		Locale loc = Locale.getDefault();
		Calendar cal = new GregorianCalendar(loc);
		cal.setTime(tDate);

		return cal;
	}

	/**
	 * 将时间串按照指定格式，格式化成Date。
	 *
	 * @param dateStr
	 * @param pattern
	 *            格式类型，通过系统常量中定义，如：CapConstants.DATE_FORMAT_8
	 * @return
	 */

	public static Date parseStrToDate(String dateStr, String pattern) {
		if (null == dateStr || "".equals(dateStr)) {
			return null;
		}

		SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);

		Date tDate = dateFormator.parse(dateStr, new ParsePosition(0));

		return tDate;
	}

	/**
	 * 日期比较大小
	 *
	 * @param dateStr1
	 * @param dateStr2
	 * @param pattern
	 * @return
	 */
	public static int compareDate(String dateStr1, String dateStr2, String pattern) {

		Date date1 = parseStrToDate(dateStr1, pattern);
		Date date2 = parseStrToDate(dateStr2, pattern);

		return date1.compareTo(date2);

	}

	/**
	 * 得到应用服务器当前日期，以8位日期显示。
	 *
	 * @return
	 */
	public static String getDate() {

		Date date = getCurrentDate();
		SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_FORMAT_8);
		return dateFormator.format(date);

	}

	/**
	 * 得到应用服务器当前日期，以8位日期显示。
	 *
	 * @return
	 */
	public static String getHhMmDate() {
		Date date = getCurrentDate();
		SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_FORMAT_MM_DD_HH_MM);
		return dateFormator.format(date);

	}

	/**
	 * 得到应用服务器当前日期，以8位日期显示。
	 *
	 * @return
	 */
	public static String getDateTime() {

		Date date = getCurrentDate();
		SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_TIME_FORMAT_14);
		return dateFormator.format(date);

	}

	/**
	 * 通过统一的格式将文本转换成Date。输入为日期。
	 *
	 * @return
	 */
	public static java.sql.Date parseDate(String sdate) {
		if (null == sdate || "".equals(sdate)) {
			return null;
		}

		SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_FORMAT);

		Date tDate = dateFormator.parse(sdate, new ParsePosition(0));
		if (tDate == null) {
			return null;
		}

		return new java.sql.Date(tDate.getTime());
	}

	/**
	 * 返回负数 说明dateStr1 是dateStr2 之后 如-7 表示7天后是dateStr1
	 * 
	 * @param dateStr1
	 * @param dateStr2
	 * @param pattern
	 * @return
	 */
	public static long getBetweenDays(String dateStr1, String dateStr2, String pattern) {
		Date date1 = parseStrToDate(dateStr1, pattern);
		Date date2 = parseStrToDate(dateStr2, pattern);
		// 获取相差的天数
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date1);
		long timeInMillis1 = calendar.getTimeInMillis();
		calendar.setTime(date2);
		long timeInMillis2 = calendar.getTimeInMillis();

		long betweenDays = (timeInMillis2 - timeInMillis1) / (1000L * 3600L * 24L);
		return betweenDays;
	}

	
	
	/**
	 * 获取两个时间的时间差
	 * @param dateStr1
	 * @param dateStr2
	 * @return
	 */
	public static String getSubTiemStr(String minDateStr, String maxDateStr) {
		Date minDate = parseStrToDate(minDateStr, DATE_TIME_FORMAT);
		Date maxDate = parseStrToDate(maxDateStr, DATE_TIME_FORMAT);
		long subTime=maxDate.getTime()-minDate.getTime();
		// 相差多少分钟
		String str="";
		long hour=subTime/3600000;
		long minute=(subTime%3600000)/60000;
		str=hour+"小时";
		str=str+minute+"分钟";
		
		if(maxDate.getSeconds()>=minDate.getSeconds()) {
			str=str+(maxDate.getSeconds()-minDate.getSeconds()+"秒");
		}
		else {
			str=str+(60+maxDate.getSeconds()-minDate.getSeconds()+"秒");
		}
		return str;
	}
	
	
	public static String getNextDay(int subDay) {
		long currentDay=getCurrentTimeMillis();
		Date date=new Date(currentDay+(subDay*24*3600*1000));
		SimpleDateFormat dateFormator = new SimpleDateFormat(DATE_FORMAT_8);
		return dateFormator.format(date);
	}
	
	
	public static String getNextDay(int subDay,String formatStr) {
		long currentDay=getCurrentTimeMillis();
		Date date=new Date((long) (currentDay+(DoubleUtil.mul(new Double(subDay), new Double(24*3600*1000)))));
		SimpleDateFormat dateFormator = new SimpleDateFormat(formatStr);
		return dateFormator.format(date);
	}
	
	public static void main(String[] args) {
	
		Date date = getCurrentDate();
		
		System.out.println(date.getHours());
	}

	public static String getFirstDayCurrentMouth() {
		Calendar cale = null;
		cale = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		String firstday;
		// 获取本月的第一天
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = format.format(cale.getTime());
		return firstday;
	}

	public static String getLastDayCurrentMouth() {
		// 获取本月的最后一天
		Calendar cale = null;
		cale = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		String lastDay;
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		lastDay = format.format(cale.getTime());
		return lastDay;
	}

}
