package egovframework.eam.api.system.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import egovframework.eam.api.config.SystemInfoConfig;
import egovframework.eam.api.internal.ObjectUtil;
import egovframework.eam.api.internal.RemoteService;
import egovframework.eam.api.internal.TicketStorage;
import egovframework.eam.api.system.SkippableSystemException;
import egovframework.eam.api.system.SystemException;
import egovframework.eam.api.system.SystemService;
import egovframework.eam.api.system.Ticket;
import egovframework.eam.api.system.Token;
import egovframework.eam.api.system.UserTicket;
import egovframework.rte.fdl.cryptography.EgovCryptoService;

public class SystemServiceImpl implements SystemService {
	private static final Logger logger = Logger.getLogger(SystemServiceImpl.class);
	
	@Resource(name="remoteService")
	private RemoteService remoteService;
	
	@Autowired
	private EgovCryptoService cryptoService;
	
	@Autowired
	private SystemInfoConfig systemInfo;
	
	@Resource(name="password")
	private String password;
	
	@Resource(name="ticketStorage")
	private TicketStorage ticketStorage;
	
	public Ticket getTicket(String systemId, String authKey, String pwd) {
		Ticket ticket = null;
		
		if (ticketStorage.get(systemId) == null) {
		
			Token token = new Token(systemId, authKey);
			
			byte[] encrypted = encrypt(token);
			
			byte[] data = null;
			
			try {
				data = remoteService.getTicket(systemId, encrypted);
			} catch (Throwable throwable) {
				logger.info("RemoteService", throwable);
				
				logger.debug("skipOnRemoteError = " + systemInfo.isSkipOnRemoteError());
				
				if (systemInfo.isSkipOnRemoteError()) {
					throw new SkippableSystemException(throwable);
				} else {
					throw new SystemException(throwable);
				}
			}
			
			byte[] decrypted = decrypt(data);
			
			ticket = (Ticket)ObjectUtil.getObjectFrom(decrypted);
			
			ticketStorage.put(systemId, ticket);
			
			return ticket;
		} else {
			return ticketStorage.get(systemId);
		}
	}
	
	public Ticket getTicket() {
		return getTicket(systemInfo.getSystemId(), systemInfo.getAuthKey(), systemInfo.getPassword());
	}

	public void login(Ticket ticket, String userId) {
		UserTicket userTicket = UserTicket.getUserTicket(ticket, userId);
		
		byte[] encrypted = encrypt(userTicket);
		
		try {
			remoteService.login(ticket.getSystemId(), encrypted);
		} catch (Throwable throwable) {
			logger.info("RemoteService", throwable);
			
			logger.debug("skipOnRemoteError = " + systemInfo.isSkipOnRemoteError());
			
			if (systemInfo.isSkipOnRemoteError()) {
				throw new SkippableSystemException(throwable);
			} else {
				throw new SystemException(throwable);
			}
		}
	}

	public void logout(Ticket ticket, String userId) {
		UserTicket userTicket = UserTicket.getUserTicket(ticket, userId);
		
		byte[] encrypted = encrypt(userTicket);
		
		try {
			remoteService.logout(ticket.getSystemId(), encrypted);
		} catch (Throwable throwable) {
			logger.info("RemoteService", throwable);
			
			logger.debug("skipOnRemoteError = " + systemInfo.isSkipOnRemoteError());
			
			if (systemInfo.isSkipOnRemoteError()) {
				throw new SkippableSystemException(throwable);
			} else {
				throw new SystemException(throwable);
			}
		}
	}
	
	private byte[] encrypt(Object obj) {
		return cryptoService.encrypt(ObjectUtil.getByteArrayFrom(obj), password);
	}
	
	private byte[] decrypt(byte[] ticket) {
		return cryptoService.decrypt(ticket, password);
	}
}
