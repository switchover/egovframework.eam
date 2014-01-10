package egovframework.eam.api.sso.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface SSOLoginCheck extends Remote {
	public static final String USER_ID = "userId";
	public static final String USER_NAME = "userName";
	
	/**
	 * Check login from UserTicket from main system.
	 * 
	 * @param systemId main system's id
	 * @param data encrypted UserTicket (base64)
	 * @return logged in userId
	 */
	public Map<String, String> loginCheck(String systemId, String base64Data) throws RemoteException;
}
