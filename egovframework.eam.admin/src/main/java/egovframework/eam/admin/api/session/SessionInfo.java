package egovframework.eam.admin.api.session;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "egovam_session")
public class SessionInfo {
    @Id
    @Column(name="system_id", length=20)
	private String systemId;
    
    @Column(name="session_key", nullable=false, length=255)
	private String sessionKey;
	
    @Column(name="issue_datetime", nullable=false, length=14)
	private String issueDatetime;
	
    @Column(name="due_datetime", nullable=false, length=14)
	private String dueDatetime;

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
