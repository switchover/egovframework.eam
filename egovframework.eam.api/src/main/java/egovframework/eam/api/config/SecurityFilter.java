package egovframework.eam.api.config;

import org.springframework.security.config.BeanIds;
import org.springframework.web.filter.DelegatingFilterProxy;

public class SecurityFilter extends DelegatingFilterProxy {
	public static final String TARGET_BEAN_NAME = BeanIds.SPRING_SECURITY_FILTER_CHAIN;

	public void setTargetBeanName(String targetBeanName) {
		super.setTargetBeanName(TARGET_BEAN_NAME);
	}
	
	protected String getTargetBeanName() {
		return TARGET_BEAN_NAME;
	}
}
