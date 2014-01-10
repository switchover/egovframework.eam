package egovframework.eam.api.sso;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import egovframework.eam.api.config.SystemInfoConfig;
import egovframework.eam.api.user.DefaultUserHelper;

public class DefaultSSOHelper implements ApplicationContextAware {
	private static final Logger logger = Logger.getLogger(DefaultSSOHelper.class);
	
	private static ApplicationContext context;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}
	
	public static String getSystemId() {
		try {
			SystemInfoConfig systemInfo = context.getBean(SystemInfoConfig.class);
			
			return systemInfo.getSystemId();
			
		} catch (NoSuchBeanDefinitionException nsbde) {
			throw new RuntimeException(nsbde); 
		}
	}
	
	public static String getUserTicketString() {
		Map<String, String> user = DefaultUserHelper.getLoginedUser();
		
		String userId = user.get("userId");
		
		try {
			SSOService ssoService = context.getBean(SSOService.class);
			
			return ssoService.getUserTicketString(userId);
			
		} catch (NoSuchBeanDefinitionException nsbde) {
			throw new RuntimeException(nsbde); 
		} catch (RuntimeException re) {
			logger.error("No System Ticket.. But we will continue..", re);
			return "";
		}
	}
	
	public static String getUserTicketUrlEncodedString() {
		try {
			return URLEncoder.encode(getUserTicketString(), "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			// no-op
			throw new RuntimeException(uee);
		}
	}
	
}
