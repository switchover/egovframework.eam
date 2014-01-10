package egovframework.eam.api.config;

import java.util.Locale;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;

@SuppressWarnings("rawtypes")
public class DataSourceFactoryBean implements FactoryBean, ApplicationContextAware {
	static final String DEF_DATASOURCE_NAME = "dataSource";
	
	private ApplicationContext context;

	@Resource(name = "messageSource")
	private MessageSource messageSource;

	public Object getObject() throws Exception {
		
		try {
			SecurityConfig config = context.getBean(SecurityConfig.class);
			
			if (config.getDataSource() != null) {
				return config.getDataSource();
			}
		} catch (NoSuchBeanDefinitionException nsbde) {
			// no-op
		}
		
		if (context.containsBean(DEF_DATASOURCE_NAME)) {
			
			return (DataSource)context.getBean(DEF_DATASOURCE_NAME);
		}
		
		throw new Exception(messageSource.getMessage("error.egovam.no.datasource", null, 
				"There isn't a DataSource. Define a DataSource as name 'dataSource'.", 
				Locale.getDefault()));
	}

	public Class getObjectType() {
		return DataSource.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

}
