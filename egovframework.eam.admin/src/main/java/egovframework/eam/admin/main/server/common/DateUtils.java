package egovframework.eam.admin.main.server.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static final String STRING_PATTERN = "yyyyMMddHHmmss";
	
	public static String getDateString(String dateString) {
		SimpleDateFormat parser = new SimpleDateFormat(STRING_PATTERN);
		SimpleDateFormat formatter = new SimpleDateFormat();
		
		Date date;
		try {
			date = parser.parse(dateString);
		} catch (ParseException pe) {
			throw new RuntimeException(pe);
		}
		
		return formatter.format(date);
	}
	
	public static String getCurrentString() {
		SimpleDateFormat parser = new SimpleDateFormat(STRING_PATTERN);
		
		Date date = new Date();
		
		return parser.format(date);
	}
}
