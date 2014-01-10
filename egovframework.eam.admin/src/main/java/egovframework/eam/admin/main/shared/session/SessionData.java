package egovframework.eam.admin.main.shared.session;

import java.io.Serializable;

public class SessionData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8097156573897707119L;

	private String systemId;
    
    private String systemName;

	private String sessionKey;
	
	private String issueDatetime;
	
	private String dueDatetime;

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getIssueDatetime() {
		return issueDatetime;
	}

	public void setIssueDatetime(String issueDatetime) {
		this.issueDatetime = issueDatetime;
	}

	public String getDueDatetime() {
		return dueDatetime;
	}

	public void setDueDatetime(String dueDatetime) {
		this.dueDatetime = dueDatetime;
	}
}
