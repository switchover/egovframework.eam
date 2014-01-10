package egovframework.eam.api.system;

import java.io.Serializable;
import java.util.Date;

public class Ticket implements Serializable {
	/**
	 * SerialVer UID.
	 */
	private static final long serialVersionUID = 439080659530624861L;
	
	private String systemId;
	private String sessionKey;
	private Date issueDate;
	private Date lastContactDate;
	
	public String getSystemId() {
		return systemId;
	}
	
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	public String getSessionKey() {
		return sessionKey;
	}
	
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}
	
	public Date getIssueDate() {
		return issueDate;
	}
	
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	
	public Date getLastContactDate() {
		return lastContactDate;
	}
	
	public void setLastContactDate(Date lastContactDate) {
		this.lastContactDate = lastContactDate;
	}
}
