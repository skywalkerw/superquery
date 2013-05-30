package wjm.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * @author WJM
 * @time 2012-11-26下午4:38:27
 */
public class StringUtil {
	private static final Logger log = Logger.getLogger(StringUtil.class);
	/**
	 * 是否为空，包括null和长度为零的空串
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(Object s) {
		if (s == null) {
			return true;
		}
		if (s.toString().trim().length() == 0) {
			return true;
		}

		return false;
	}

	/**
	 * 是否全空白字符,空格，\r \n \t 等
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isWhite(String s) {
		if (isEmpty(s)) {
			return true;
		}
		if (s.matches("(?s)\\s*?")) {
			return true;
		}

		return false;
	}
	
	public static String lower(String s){
		if(s==null){
			return null;
		}
		return s.toLowerCase();
	}
	public static String trim(Object s){
		if(s==null){
			return "";
		}
		return s.toString().trim();
	}
	public static String upper(String s){
		if(s==null){
			return null;
		}
		return s.toUpperCase();
	}
	public static void main(String[] args) {
		Date d1 = getDate("2012-01-01","yyyy-MM-dd");
		Date d2 = getDate("2012-01-02","yyyy-MM-dd");
		long long1 =  d1.getTime();
		long long2 =  d2.getTime();
		System.out.println((long2-long1)/(60*60*24*1000));
		
	}
	
	/**
	 * 计算两个日期之间的天数
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static long getDayBetween(Date d1,Date d2){
		long long1 =  d1.getTime();
		long long2 =  d2.getTime();
		long days = (long2-long1)/(60*60*24*1000);
		return days;
	}
	
	/**
	 * String转换为Date
	 * @param sDate
	 * @param format 指定日期格式 例如yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date getDate(String sDate,String format){
		Date myDate = null;
		try {
			myDate = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			myDate = formatter.parse(sDate);
		} catch (Exception e) {
			
		}
		return myDate;
	}
	
	public static int parseInt(String org,int defaultv){
		if(org==null){
			return defaultv;
		}
		try {
			return Integer.parseInt(org);
		} catch (NumberFormatException e) {
			log.warn("["+org+"]转换数字失败,返回默认值"+defaultv);
			return defaultv;
		}
	}
}
