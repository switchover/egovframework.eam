package egovframework.eam.api.user;

public class CamelCaseUtil {
	public static String convert2CamelCase(String underScore) {

		if (isSkipCase(underScore)) {
			return underScore;
		}
		
		StringBuilder result = new StringBuilder();
		boolean nextUpper = false;
		int len = underScore.length();

		for (int i = 0; i < len; i++) {
			char currentChar = underScore.charAt(i);
			if (currentChar == '_') {
				nextUpper = true;
			} else {
				if (nextUpper) {
					result.append(Character.toUpperCase(currentChar));
					nextUpper = false;
				} else {
					result.append(Character.toLowerCase(currentChar));
				}
			}
		}
		return result.toString();
	}

	protected static boolean isSkipCase(String underScore) {
		return underScore.indexOf('_') < 0 && Character.isLowerCase(underScore.charAt(0));
	}
}
