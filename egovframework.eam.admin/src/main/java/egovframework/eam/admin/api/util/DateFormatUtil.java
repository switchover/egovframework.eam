package egovframework.eam.admin.api.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {
	public static final String DATE_PATTERN = "yyyyMMddHHmmss";
	public static final String MAX_DATE = "29991231235959";
	
	public static String getDateString(Date date) {
		DateFormat formatter = new SimpleDateFormat(DATE_PATTERN);
		
		return formatter.format(date);
	}
}
