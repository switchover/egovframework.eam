package egovframework.eam.api.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SystemInfoStringFactoryBean implements FactoryBean<String>, ApplicationContextAware {
	//private static final Logger logger = Logger.getLogger(SystemInfoStringFactoryBean.class);
	
	private ApplicationContext context;

	private String propertyName;
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}
	
	public String getObject() throws Exception {
		SystemInfoConfig config = context.getBean(SystemInfoConfig.class);
		
		if (propertyName.equalsIgnoreCase("systemId")) {
			return config.getSystemId();
		} else if (propertyName.equalsIgnoreCase("authKey")) {
			return config.getAuthKey();
		} else if (propertyName.equalsIgnoreCase("password")) {
			return config.getPassword();
		} 
		
		throw new IllegalStateException("propertyName must be 'systemId', 'autkKey', or 'password'");
	}

	public Class<?> getObjectType() {
		return String.class;
	}

	public boolean isSingleton() {
		return true;
	}

	@Required
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
}
