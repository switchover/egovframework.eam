package egovframework.eam.api.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SuppressWarnings("rawtypes")
public class MapClassFactoryBean implements FactoryBean, ApplicationContextAware {
	private ApplicationContext context;
	
	private String defaultMapClass;

	public Object getObject() throws Exception {
		try {
			SecurityConfig config = context.getBean(SecurityConfig.class);
			
			if (config.getJdbcMapClass() != null) {
				return config.getJdbcMapClass();
			}
		} catch (NoSuchBeanDefinitionException nsbde) {
			// no-op
		}
		
		return defaultMapClass;
	}

	public Class getObjectType() {
		return String.class;
	}

	public boolean isSingleton() {
		return true;
	}
	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	public String getDefaultMapClass() {
		return defaultMapClass;
	}

	@Required
	public void setDefaultMapClass(String defaultMapClass) {
		this.defaultMapClass = defaultMapClass;
	}
}
