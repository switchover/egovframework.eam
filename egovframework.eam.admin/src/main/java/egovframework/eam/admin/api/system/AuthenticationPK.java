package egovframework.eam.admin.api.system;

import java.io.Serializable;

import javax.persistence.Column;

public class AuthenticationPK implements Serializable {
	/**
	 * SerialVer UID.
	 */
	private static final long serialVersionUID = -4759983847121673943L;

	@Column(name="system_id", length=20)
	private String systemId;
	
	@Column(name="user_id", length=20)
	private String userId;
	
	public AuthenticationPK() {
	}
	
	public AuthenticationPK(String systemId, String userId) {
		this.systemId = systemId;
		this.userId = userId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (obj instanceof AuthenticationPK) {
			AuthenticationPK auth = (AuthenticationPK)obj;
			
			if (auth.getSystemId() == null || auth.getUserId() == null) {
				return false;
			}
			
			if (auth.getSystemId().equals(systemId) && auth.getUserId().equals(userId)) {
				return true;
			}
 		} 

		return false;
	}

	@Override
	public int hashCode() {
		return super.hashCode();	// TODO
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
