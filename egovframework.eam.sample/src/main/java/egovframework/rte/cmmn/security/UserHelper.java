package egovframework.rte.cmmn.security;

import java.util.List;

import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.providers.encoding.ShaPasswordEncoder;

import egovframework.rte.fdl.security.userdetails.util.EgovUserDetailsHelper;

public class UserHelper {
	public static void getLoginedUserInfo() {
		if (EgovUserDetailsHelper.isAuthenticated()) {
			EgovUserDetailsVO user = (EgovUserDetailsVO)EgovUserDetailsHelper.getAuthenticatedUser(); 

			System.out.println(user.getUserId());
			System.out.println(user.getUserName());
			
			List<String> autorities = EgovUserDetailsHelper.getAuthorities();
		}
	}
	
	public static void main(String[] args) {
		String password = "admin";
		try {
			PasswordEncoder encoder = new ShaPasswordEncoder(256);
			String hashed = encoder.encodePassword(password, null);
			
			System.out.println("Password : " + password + " => " + hashed);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
