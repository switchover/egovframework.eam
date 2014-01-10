package egovframework.eam.api.system;

import java.io.Serializable;

/**
 * For system authentication.
 * 
 * @author Han
 */
public class Token implements Serializable {
	/**
	 * SerialVer UID.
	 */
	private static final long serialVersionUID = -3130441850684180898L;
	
	private String systemId;
	private String authKey;
	
	public Token() {
		
	}
	
	public Token(String systemId, String authKey) {
		this.systemId = systemId;
		this.authKey = authKey;
	}
	
	public String getSystemId() {
		return systemId;
	}
	
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	public String getAuthKey() {
		return authKey;
	}
	
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
}
