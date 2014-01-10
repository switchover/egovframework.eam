package egovframework.eam.api.sso.impl;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;

import egovframework.eam.api.config.SystemInfoConfig;
import egovframework.eam.api.internal.ObjectUtil;
import egovframework.eam.api.internal.TicketStorage;
import egovframework.eam.api.sso.SSOService;
import egovframework.eam.api.system.Ticket;
import egovframework.eam.api.system.UserTicket;
import egovframework.rte.fdl.cryptography.EgovCryptoService;

public class SSOServiceImpl implements SSOService {
	@Autowired
	private SystemInfoConfig systemInfo;
	
	@Resource(name="ticketStorage")
	private TicketStorage ticketStorage;
	
	@Autowired
	private EgovCryptoService cryptoService;

	public String getUserTicketString(String userId) {
		Ticket ticket = ticketStorage.get(systemInfo.getSystemId());
		
		if (ticket == null) {
			throw new RuntimeException("No system ticket..");
		}
		
		UserTicket userTicket = UserTicket.getUserTicket(ticket, userId);
		
		byte[] data = encrypt(userTicket, systemInfo.getPassword());
		
		return new String(Base64.encodeBase64(data));
	}

	private byte[] encrypt(Object obj, String password) {
		return cryptoService.encrypt(ObjectUtil.getByteArrayFrom(obj), password);
	}
	
}
