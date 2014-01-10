package egovframework.eam.api.sso.client;

import java.rmi.Naming;
import java.util.Map;

public class SSOClient {
	public static final String USER_ID = SSOLoginCheck.USER_ID;
	public static final String USER_NAME = SSOLoginCheck.USER_NAME;

	private String serverIp;
	private String serverPort;
		
	public SSOClient(String serverIp, String serverPort) {
		this.serverIp = serverIp;
		this.serverPort = serverPort;
	}
	
	public Map<String, String> getSSOLoginedUser(String systemId, String base64Data) {
		
		try {
			SSOLoginCheck check = (SSOLoginCheck)Naming.lookup("rmi://" + serverIp + ":" + serverPort + "/SSOLoginCheck");
			
			return check.loginCheck(systemId, base64Data);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
