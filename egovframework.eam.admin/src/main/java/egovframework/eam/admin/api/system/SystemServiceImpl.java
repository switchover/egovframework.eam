package egovframework.eam.admin.api.system;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.eaio.uuid.UUID;

import egovframework.eam.admin.api.session.JpaSessionDAO;
import egovframework.eam.admin.api.session.SessionInfo;
import egovframework.eam.admin.api.util.DateFormatUtil;
import egovframework.eam.api.system.SystemException;
import egovframework.eam.api.system.SystemService;
import egovframework.eam.api.system.SystemServiceUtil;
import egovframework.eam.api.system.Ticket;
import egovframework.eam.api.system.UserTicket;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;

@Service("systemService")
public class SystemServiceImpl implements SystemService {
	private static final Logger logger = Logger.getLogger(SystemServiceImpl.class);
	
	@Resource(name="jpaSessionDao")
	private JpaSessionDAO sessionDao;
	
	@Resource(name="jpaAuthenticationDao")
	private JpaAuthenticationDAO authenticationDao;

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public Ticket getTicket(String systemId, String authKey, String password) {

		if (isAuthKeyValid(password, authKey)) {
			Ticket ticket =  generateTicket(systemId);
			
			SessionInfo session = sessionDao.findById(systemId);
			
			if (session == null) {
				session = new SessionInfo();
				
				session.setSystemId(systemId);
				session.setSessionKey(ticket.getSessionKey());
				session.setIssueDatetime(DateFormatUtil.getDateString(ticket.getIssueDate()));
				session.setDueDatetime(DateFormatUtil.MAX_DATE);
				
				sessionDao.persist(session);
				
				logger.info("session key for " + systemId + " is created...");
			} else {
				session.setSessionKey(ticket.getSessionKey());
				session.setIssueDatetime(DateFormatUtil.getDateString(ticket.getIssueDate()));
				session.setDueDatetime(DateFormatUtil.MAX_DATE);
				
				sessionDao.merge(session);
				
				logger.info("session key for " + systemId + " is updated...");
			}
			
			return ticket;
		} else {
			throw new SystemException("AuthKey isn't valid!! Check your authKey.");
		}
	}
	
	public Ticket getTicket() {
		throw new UnsupportedOperationException("Use getTicket(String, String, String)");
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void login(Ticket ticket, String userId) {
		UserTicket userTicket = (UserTicket)ticket;
		
		AuthenticationInfo auth = authenticationDao.findByMultiId(
				new AuthenticationPK(userTicket.getSystemId(), userTicket.getUserId()));
		
		if (auth == null) {
			auth = new AuthenticationInfo();
			
			auth.setSystemId(userTicket.getSystemId());
			auth.setUserId(userTicket.getUserId());
			auth.setAuthenDatetime(DateFormatUtil.getDateString(new Date()));
			auth.setDueDatetime(DateFormatUtil.MAX_DATE);
			
			authenticationDao.persist(auth);
		} else {
			auth.setAuthenDatetime(DateFormatUtil.getDateString(new Date()));
			auth.setDueDatetime(DateFormatUtil.MAX_DATE);
			
			authenticationDao.merge(auth);
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void logout(Ticket ticket, String userId) {
		UserTicket userTicket = (UserTicket)ticket;
		
		AuthenticationInfo auth = authenticationDao.findByMultiId(
				new AuthenticationPK(userTicket.getSystemId(), userTicket.getUserId()));
		
		if (auth == null) {
			// no-op
		} else {
			authenticationDao.remove(auth);
		}
	}
	
	protected Ticket generateTicket(String systemId) {
		Ticket ticket = new Ticket();
		
		ticket.setSystemId(systemId);
		ticket.setIssueDate(new Date());

		UUID u = new UUID();

		ticket.setSessionKey(u.toString());
		
		return ticket;
	}

	protected boolean isAuthKeyValid(String password, String authKey) {
		EgovPasswordEncoder encoder = new EgovPasswordEncoder();
		
		encoder.setAlgorithm(SystemServiceUtil.DIGEST_ALGORITHM);
		
		return encoder.checkPassword(password, authKey);
	}

}
