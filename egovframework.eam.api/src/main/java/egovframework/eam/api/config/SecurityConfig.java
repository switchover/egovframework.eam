package egovframework.eam.api.config;

import java.lang.reflect.Field;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.config.BeanIds;
import org.springframework.security.ui.AccessDeniedHandlerImpl;
import org.springframework.security.ui.ExceptionTranslationFilter;
import org.springframework.security.ui.logout.LogoutFilter;
import org.springframework.security.ui.logout.LogoutHandler;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilterEntryPoint;
import org.springframework.security.util.UrlUtils;

public class SecurityConfig implements ApplicationContextAware {
	private static final Logger logger = Logger.getLogger(SecurityConfig.class);

	static final String CUSTOM_LOGOUT_FILTER_ID = "customLogoutFilter";
	
	private ApplicationContext context;
	
	private String loginUrl;
	private String logoutSuccessUrl;
	private String loginFailureUrl;
	private String accessDeniedUrl;
	
	private DataSource dataSource;
	
	private String jdbcUsersByUsernameQuery;
	private String jdbcAuthoritiesByUsernameQuery;
	
	private String jdbcMapClass;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
	
	@PostConstruct
	public void init() {
		logger.debug("init() started...");
		
		if (! "".equals(accessDeniedUrl)) {
			logger.debug("Replaced access denied url : " + accessDeniedUrl);
			registerAccessDeniedUrl();
		}
		
		if (! "".equals(loginUrl)) {
			logger.debug("Replaced login url : " + loginUrl);
			registerLoginUrl();
		}
		
		if (! "".equals(loginFailureUrl)) {
			logger.debug("Replaced login failure url : " + loginFailureUrl);
			registerLoginFailureUrl();
		}
		
		if (! "".equals(logoutSuccessUrl)) {
			logger.debug("Replaced logout success url : " + logoutSuccessUrl);
			registerLogoutSuccessUrl();
		}
		
		registerLogoutHandler();
		
		logger.debug("init() ended...");
	}
	
	private void registerLogoutHandler() {
		LogoutFilter filter = (LogoutFilter)context.getBean(BeanIds.LOGOUT_FILTER);
		
		try {
			Field field = filter.getClass().getDeclaredField("handlers");
			
			field.setAccessible(true);
			
			LogoutHandler[] handlers = (LogoutHandler[]) field.get(filter);
			LogoutHandler[] newHandlers = new LogoutHandler[handlers.length + 1];
			
			for (int i = 0; i < handlers.length; i++) {
				newHandlers[i] = handlers[i];
			}
			
			newHandlers[handlers.length] = (LogoutHandler) context.getBean("logoutHandler");
			
			field.set(filter, newHandlers);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	private void registerLogoutSuccessUrl() {
		LogoutFilter filter = (LogoutFilter)context.getBean(BeanIds.LOGOUT_FILTER);
		
		checkUrl(logoutSuccessUrl);
		
		try {
			Field field = filter.getClass().getDeclaredField("logoutSuccessUrl");
			
			field.setAccessible(true);
			field.set(filter, logoutSuccessUrl);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	private void registerLoginFailureUrl() {
		AuthenticationProcessingFilter filter = (AuthenticationProcessingFilter)context.getBean(BeanIds.FORM_LOGIN_FILTER);
		
		checkUrl(loginFailureUrl);
		
		filter.setAuthenticationFailureUrl(loginFailureUrl);
	}

	private void registerLoginUrl() {
		AuthenticationProcessingFilterEntryPoint entryPoint = (AuthenticationProcessingFilterEntryPoint)context.getBean(BeanIds.FORM_LOGIN_ENTRY_POINT);
		
		checkUrl(loginUrl);
		
		entryPoint.setLoginFormUrl(loginUrl);
	}

	protected void registerAccessDeniedUrl() {
		ExceptionTranslationFilter filter = (ExceptionTranslationFilter)context.getBean(BeanIds.EXCEPTION_TRANSLATION_FILTER);
		
		checkUrl(accessDeniedUrl);
		
		AccessDeniedHandlerImpl accessDeniedHandler = new AccessDeniedHandlerImpl();
		accessDeniedHandler.setErrorPage(accessDeniedUrl);
         
		filter.setAccessDeniedHandler(accessDeniedHandler);
	}

	private void checkUrl(String url) {
		if (UrlUtils.isValidRedirectUrl(url)) {
			// no-op
		} else {
			logger.warn("Url (" + url + " is not a valid redirect URL (must start with '/' or http(s))");
		}
	}
	
	//-------------------
	// getters & setters
	//-------------------

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLogoutSuccessUrl() {
		return logoutSuccessUrl;
	}

	public void setLogoutSuccessUrl(String logoutSuccessUrl) {
		this.logoutSuccessUrl = logoutSuccessUrl;
	}

	public String getLoginFailureUrl() {
		return loginFailureUrl;
	}

	public void setLoginFailureUrl(String loginFailureUrl) {
		this.loginFailureUrl = loginFailureUrl;
	}

	public String getAccessDeniedUrl() {
		return accessDeniedUrl;
	}

	public void setAccessDeniedUrl(String accessDeniedUrl) {
		this.accessDeniedUrl = accessDeniedUrl;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getJdbcUsersByUsernameQuery() {
		return jdbcUsersByUsernameQuery;
	}

	public void setJdbcUsersByUsernameQuery(String jdbcUsersByUsernameQuery) {
		this.jdbcUsersByUsernameQuery = jdbcUsersByUsernameQuery;
	}

	public String getJdbcAuthoritiesByUsernameQuery() {
		return jdbcAuthoritiesByUsernameQuery;
	}

	public void setJdbcAuthoritiesByUsernameQuery(String jdbcAuthoritiesByUsernameQuery) {
		this.jdbcAuthoritiesByUsernameQuery = jdbcAuthoritiesByUsernameQuery;
	}

	public String getJdbcMapClass() {
		return jdbcMapClass;
	}

	public void setJdbcMapClass(String jdbcMapClass) {
		this.jdbcMapClass = jdbcMapClass;
	}
}
