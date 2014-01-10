package egovframework.eam.api.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SuppressWarnings("rawtypes")
public class UsersQueryFactoryBean implements FactoryBean, ApplicationContextAware {
	private ApplicationContext context;
	
	private String defaultQuery;

	public Object getObject() throws Exception {
		try {
			SecurityConfig config = context.getBean(SecurityConfig.class);
			
			if (config.getJdbcUsersByUsernameQuery() != null) {
				return config.getJdbcUsersByUsernameQuery();
			}
		} catch (NoSuchBeanDefinitionException nsbde) {
			// no-op
		}
		
		return defaultQuery;
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

	public String getDefaultQuery() {
		return defaultQuery;
	}

	@Required
	public void setDefaultQuery(String defaultQuery) {
		this.defaultQuery = defaultQuery;
	}
}
