package egovframework.eam.api.config;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@SuppressWarnings("rawtypes")
public class AdminServerFactoryBean implements FactoryBean, ApplicationContextAware {
	private static final Logger logger = Logger.getLogger(AdminServerFactoryBean.class);
	
	private ApplicationContext context;
	
	private final static String DEFAULT_ADMIN_SERVER_IP = "127.0.0.1";	// default
	private final static String DEFAULT_ADMIN_SERVER_PORT = "1099";		// default

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	public Object getObject() throws Exception {
		StringBuffer buffer = new StringBuffer();
		
		try {
			SystemInfoConfig config = context.getBean(SystemInfoConfig.class);
			
			buffer.append("rmi://");
			
			if (config.getAdminServerIp() == null || "".equals(config.getAdminServerIp())) {
				buffer.append(DEFAULT_ADMIN_SERVER_IP);
			} else {
				buffer.append(config.getAdminServerIp());
			}
			
			buffer.append(":");
			
			if (config.getAdminServerPort() == null || "".equals(config.getAdminServerPort())) {
				buffer.append(DEFAULT_ADMIN_SERVER_PORT);
			} else {
				buffer.append(config.getAdminServerPort());
			}
			
			buffer.append("/RemoteService");
		} catch (NoSuchBeanDefinitionException nsbde) {
			logger.error(nsbde);
			logger.info("Return default value : rmi://127.0.0.1:1099/RemoteService");
			
			return "rmi://127.0.0.1:1099/RemoteService";
		}
		
		return buffer.toString();
	}

	public Class getObjectType() {
		return String.class;
	}

	public boolean isSingleton() {
		return true;
	}

}
