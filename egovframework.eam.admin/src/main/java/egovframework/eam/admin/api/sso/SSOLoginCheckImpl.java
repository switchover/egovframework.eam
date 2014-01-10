package egovframework.eam.admin.api.sso;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import egovframework.eam.admin.api.system.AuthenticationInfo;
import egovframework.eam.admin.api.system.AuthenticationPK;
import egovframework.eam.admin.api.system.JpaAuthenticationDAO;
import egovframework.eam.admin.api.system.JpaSystemDAO;
import egovframework.eam.admin.api.system.SystemInfo;
import egovframework.eam.admin.api.user.JpaUserDAO;
import egovframework.eam.admin.api.user.UserInfo;
import egovframework.eam.api.internal.ObjectUtil;
import egovframework.eam.api.sso.client.SSOLoginCheck;
import egovframework.eam.api.system.SystemException;
import egovframework.eam.api.system.UserTicket;
import egovframework.rte.fdl.cryptography.EgovGeneralCryptoService;
import egovframework.rte.fdl.cryptography.EgovPasswordEncoder;

@Service("ssoLogicCheck")
public class SSOLoginCheckImpl implements SSOLoginCheck {
	private static final Logger logger = Logger.getLogger(SSOLoginCheckImpl.class);
	
	@Resource(name="passwordEncoder")
	private EgovPasswordEncoder passwordEncoder;
	
	@Resource(name="cryptoService")
	private EgovGeneralCryptoService cryptoService;
	
	@Resource(name="jpaSystemDao")
	private JpaSystemDAO systemDao;
	
	@Resource(name="jpaAuthenticationDao")
	private JpaAuthenticationDAO authenticationDao;
	
	@Resource(name="jpaUserDao")
	private JpaUserDAO userDao;

	public Map<String, String> loginCheck(String systemId, String base64Data) {
		
		byte[] data = Base64.decodeBase64(base64Data.getBytes());
		
		SystemInfo system = findAndCheckSystemId(systemId);
		
		byte[] decrypted = decrypt(data, system);
		
		UserTicket userTicket = (UserTicket)ObjectUtil.getObjectFrom(decrypted);
		
		AuthenticationInfo auth = authenticationDao.findByMultiId(
				new AuthenticationPK(userTicket.getSystemId(), userTicket.getUserId()));
		
		if (auth == null) {
			logger.warn("There is no authentication from " + userTicket.getSystemId() + " (id:" + userTicket.getUserId() + ") => return null...");
			return null;
		} else {
			
			UserInfo userInfo = userDao.findById(userTicket.getUserId());
			
			Map<String, String> user = new HashMap<String, String>();
			
			user.put(USER_ID, userInfo.getUserId());
			user.put(USER_NAME, userInfo.getUserName());
			
			return user;
		}
		
	}
	
	private SystemInfo findAndCheckSystemId(String systemId) {
		SystemInfo system = systemDao.findById(systemId);
		
		if (system == null) {
			throw new SystemException("SystemId('" + systemId + "') not found...");
		}
		return system;
	}
	
	private byte[] decrypt(byte[] data, SystemInfo system) {
		String hashedPassword = passwordEncoder.encryptPassword(system.getPassword());
		
		passwordEncoder.setHashedPassword(hashedPassword);
		
		cryptoService.setPasswordEncoder(passwordEncoder);
		
		return cryptoService.decrypt(data, system.getPassword());
	}

}
