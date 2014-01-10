package egovframework.eam.api.event;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.security.event.authentication.AuthenticationSuccessEvent;
import org.springframework.security.userdetails.UserDetails;

import egovframework.eam.api.system.SkippableSystemException;
import egovframework.eam.api.system.SystemService;
import egovframework.eam.api.system.Ticket;
import egovframework.eam.api.system.UserTicket;

public class AuthenticationSuccessEventHandler implements ApplicationListener<AuthenticationSuccessEvent> {
	private static final Logger logger = Logger.getLogger(AuthenticationSuccessEventHandler.class);
	
	@Resource(name="systemService")
	SystemService systemService;
	
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
		
		String userId = userDetails.getUsername();
		
		logger.debug("Successed login : " + userId);
		
		Ticket ticket = null;
		
		try {
			ticket = systemService.getTicket();
		} catch (SkippableSystemException sse) {
			return;
		}
		
		try {
			systemService.login(UserTicket.getUserTicket(ticket, userId), userId);
		} catch (SkippableSystemException sse) {
			return;
		}
	}
}
