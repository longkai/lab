package cn.newgxu.lab.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 一个<b>线程安全</b>的日期时间处理工具类，包括格式化，解析时间，以及转换为相对时间的字符串。
 *
 * @User longkai
 * @Date 13-8-9
 * @Mail im.longkai@gmail.com
 */
public class DateTime {

	/**
	 * the ISO date pattern: yyyy-MM-dd
	 */
	public static final String ISO_DATE = "yyyy-MM-dd";
	/**
	 * the ISO time pattern: HH:mm:ss
	 */
	public static final String ISO_TIME = "HH:mm:ss";
	/**
	 * the ISO date-time pattern
	 */
	public static final String ISO_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

	private static ThreadLocal<SimpleDateFormat> sdf = new ThreadSafeDateFormat();

	/**
	 * format the given date to the ISO data-time pattern (yyyy-MM-dd HH:mm:ss)
	 *
	 * @param date
	 * @return formatted string
	 */
	public static String format(Date date) {
		return sdf.get().format(date);
	}

	/**
	 * format the given date to the given pattern
	 *
	 * @param date
	 * @param pattern
	 * @return formatted string
	 */
	public static String format(Date date, String pattern) {
		SimpleDateFormat sdf = DateTime.sdf.get();
		sdf.applyPattern(pattern);
		String s = sdf.format(date);
		sdf.applyPattern(ISO_DATE_TIME);
		return s;
	}

	/**
	 * parse the given date string as a Date object
	 *
	 * @param string
	 * @return date object
	 */
	public static Date parse(String string) {
		try {
			return sdf.get().parse(string);
		} catch (ParseException e) {
			throw new IllegalArgumentException("format string \"" + string + "\" to date error!", e);
		}
	}

	public static String getRelativeTimeString(long time) {
		long now = System.currentTimeMillis();
		long offset = now - time;
		String direction = offset > 0 ? "前" : "后";
		offset = Math.abs(offset);

//		秒杀- -
		long second = 1000;
		if (offset < second * 10) {
			return "刚刚~";
		}

//		xx秒前
		long minute = 60 * second;
		if (offset < minute) {
			return (offset / second) + "秒" + direction;
		}
//		xx分钟前
		long hour = minute * 60;
		if (offset < hour) {
			return (offset / minute) + "分钟" + direction;
		}
//		xx小时前
		long day = hour * 24;
		if (offset < day) {
			return (offset / hour) + "小时" + direction;
		}
//		xx天前，简单起见，每个月假设都为30天
		long month = day * 30;
		if (offset < month) {
			return (offset / day) + "天" + direction;
		}
//		xx月前
		long year = month * 12;
		if (offset < year) {
			return (offset / month) + "个月" + direction;
		}
//		xx年前
		return (offset / year) + "年" + direction;
	}

	/**
	 * thread safe date format class.
	 *
	 * @User longkai
	 * @Date 13-8-9
	 * @Mail im.longkai@gmail.com
	 */
	public static class ThreadSafeDateFormat extends ThreadLocal<SimpleDateFormat> {

		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(ISO_DATE_TIME);
		}

		@Override
		public SimpleDateFormat get() {
			return super.get();
		}

		@Override
		public void set(SimpleDateFormat value) {
			super.set(value);
		}

		@Override
		public void remove() {
			super.remove();
		}
	}
}
