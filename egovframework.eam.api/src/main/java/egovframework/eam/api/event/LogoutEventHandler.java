package egovframework.eam.api.event;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.Authentication;
import org.springframework.security.ui.logout.LogoutHandler;

import egovframework.eam.api.system.SkippableSystemException;
import egovframework.eam.api.system.SystemService;
import egovframework.eam.api.system.Ticket;
import egovframework.eam.api.system.UserTicket;

public class LogoutEventHandler implements LogoutHandler {
	private static final Logger logger = Logger.getLogger(LogoutEventHandler.class);

	@Resource(name="systemService")
	SystemService systemService;
	
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		logger.debug("Successed logout : " + authentication.getName());	
		
		String userId = authentication.getName();
		
		Ticket ticket = null;
		
		try {
			ticket = systemService.getTicket();
		} catch (SkippableSystemException sse) {
			return;
		}
		
		try {
			systemService.logout(UserTicket.getUserTicket(ticket, userId), userId);
		} catch (SkippableSystemException sse) {
			return;
		}
	}

}
