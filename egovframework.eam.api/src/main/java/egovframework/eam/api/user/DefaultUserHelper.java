package egovframework.eam.api.user;

import java.util.List;
import java.util.Map;

import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.providers.encoding.ShaPasswordEncoder;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

public class DefaultUserHelper {
	public static Map<String, String> getLoginedUser() {
		if (EgovUserDetailsHelper.isAuthenticated()) {
			@SuppressWarnings("unchecked")
			Map<String, String> user = (Map<String, String>)EgovUserDetailsHelper.getAuthenticatedUser(); 
			return user;
		}
		
		return null;
	}
	
	public static List<String> getUserAuthorities() {
		List<String> autorities = EgovUserDetailsHelper.getAuthorities();
		
		return autorities;
	}
	
	public static String encodePassword(String password) {
		PasswordEncoder encoder = new ShaPasswordEncoder(256);
		return encoder.encodePassword(password, null);
	}
	
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Password is needed...");
			System.out.println("  ex)");
			System.out.println("  " + DefaultUserHelper.class.getName() + " password");
			return;
		}
		
		String password = args[0];
		try {
			String hashed = encodePassword(password);
			
			System.out.println("Password : " + password + " => " + hashed);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
