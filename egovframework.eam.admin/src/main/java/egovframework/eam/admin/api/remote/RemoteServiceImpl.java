package egovframework.eam.admin.api.remote;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.eam.admin.api.session.JpaSessionDAO;
import egovframework.eam.admin.api.session.SessionInfo;
import egovframework.eam.admin.api.system.JpaSystemDAO;
import egovframework.eam.admin.api.system.SystemInfo;
import egovframework.eam.api.internal.ObjectUtil;
import egovframework.eam.api.internal.RemoteService;
import egovframework.eam.api.system.SystemException;
import egovframework.eam.api.system.SystemService;
import egovframework.eam.api.system.Ticket;
import egovframework.eam.api.system.Token;
import egovframework.eam.api.system.UserTicket;
import egovframework.rte.fdl.cryptography.EgovGeneralCryptoService;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;

@Service("remoteService")
public class RemoteServiceImpl implements RemoteService {
	@Resource(name="systemService")
	private SystemService systemService;
	
	@Resource(name="passwordEncoder")
	private EgovPasswordEncoder passwordEncoder;
	
	@Resource(name="cryptoService")
	private EgovGeneralCryptoService cryptoService;
	
	@Resource(name="jpaSystemDao")
	private JpaSystemDAO systemDao;
	
	@Resource(name="jpaSessionDao")
	private JpaSessionDAO sessionDao;

	public byte[] getTicket(String systemId, byte[] data) {
		SystemInfo system = findAndCheckSystemId(systemId);
		
		byte[] decrypted = decrypt(data, system);

		Token token = (Token)ObjectUtil.getObjectFrom(decrypted);
		
		Ticket ticket = systemService.getTicket(systemId, token.getAuthKey(), system.getPassword());
		
		byte[] encrypted = encrypt(system, ticket);
		
		return encrypted;
	}
	
	public void login(String systemId, byte[] data) {
		SystemInfo system = findAndCheckSystemId(systemId);
		
		SessionInfo session = findAndCheckSessionKey(systemId);
		
		byte[] decrypted = decrypt(data, system);
		
		UserTicket userTicket = (UserTicket)ObjectUtil.getObjectFrom(decrypted);
		
		checkSessionKeyValiation(session, userTicket);
		
		systemService.login(userTicket, userTicket.getUserId());
	}

	public void logout(String systemId, byte[] data) {
		SystemInfo system = findAndCheckSystemId(systemId);
		
		SessionInfo session = findAndCheckSessionKey(systemId);
		
		byte[] decrypted = decrypt(data, system);
		
		UserTicket userTicket = (UserTicket)ObjectUtil.getObjectFrom(decrypted);
		
		checkSessionKeyValiation(session, userTicket);
		
		systemService.logout(userTicket, userTicket.getUserId());
	}
	
	private SystemInfo findAndCheckSystemId(String systemId) {
		SystemInfo system = systemDao.findById(systemId);
		
		if (system == null) {
			throw new SystemException("SystemId('" + systemId + "') not found...");
		}
		return system;
	}
	
	private SessionInfo findAndCheckSessionKey(String systemId) {
		SessionInfo session = sessionDao.findById(systemId);
		
		if (session == null) {
			throw new SystemException("Session for '" + systemId + "') not found...");
		}
		
		return session;
	}
	
	private void checkSessionKeyValiation(SessionInfo session, UserTicket userTicket) {
		if (! session.getSessionKey().equalsIgnoreCase(userTicket.getSessionKey())) {
			throw new SystemException("SessionKey invalidated...");
		}
	}

	private byte[] decrypt(byte[] data, SystemInfo system) {
		String hashedPassword = passwordEncoder.encryptPassword(system.getPassword());
		
		passwordEncoder.setHashedPassword(hashedPassword);
		
		cryptoService.setPasswordEncoder(passwordEncoder);
		
		return cryptoService.decrypt(data, system.getPassword());
	}
	
	private byte[] encrypt(SystemInfo system, Ticket ticket) {
		return cryptoService.encrypt(ObjectUtil.getByteArrayFrom(ticket), system.getPassword());
	}
}
