package ms.luna.biz.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

	public static final String FORMAT_yyyy_MM_dd_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	
	public static final String FORMAT_yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";

	public static final String FORMAT_yyyyMMdd = "yyyyMMdd";
	/**
	 * "yyyyMMddHHmmssSSS"
	 */
	public static final String FORMAT_yyyyMMddHHmmss = "yyyyMMddHHmmss";

	public static String formatyyyyMMddHHmmss(java.util.Date date) {
		return format(date, FORMAT_yyyyMMddHHmmss);
	}

	public static String format(java.util.Date date, String fmtPattern) {
		return new SimpleDateFormat(fmtPattern).format(date);
	}

	public static Date parse(String dateStr, String fmtPattern) {
		SimpleDateFormat format = new SimpleDateFormat(fmtPattern);
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			throw new RuntimeException("日期格式不对：" + fmtPattern + "");
		}
	}
	
	public static Date getDateAdd(java.util.Date date, int year, int month, int day, int hour, int minute,
			int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(Calendar.YEAR, year);
		calendar.add(Calendar.MONTH, month);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		calendar.add(Calendar.HOUR, hour);
		calendar.add(Calendar.MINUTE, minute);
		calendar.add(Calendar.SECOND, second);

		return calendar.getTime();
	}
	
	public static boolean isTimeOut(long org, long cur, long min) {
		return (cur - org) > (min * 60 * 1000);
	}

	public static void main(String[] args) {
		System.out.println(format(new Date(), FORMAT_yyyy_MM_dd_HH_MM_SS));
		try {
			System.out.println(URLEncoder.encode("http://luna-test.visualbusiness.cn/luna/test1session.html", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 */
	private DateUtil() {
	}
}
