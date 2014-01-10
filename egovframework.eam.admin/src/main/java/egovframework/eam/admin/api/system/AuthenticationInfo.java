package egovframework.eam.admin.api.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "egovam_authentications")
@IdClass(AuthenticationPK.class)
public class AuthenticationInfo {
    @Id
    @Column(name="system_id", length=20)
	private String systemId;
    
    @Id
    @Column(name="user_id", length=20)
	private String userId;
    
    @Column(name="authen_datetime", nullable=false, length=14)
	private String authenDatetime;
    
    @Column(name="due_datetime", nullable=false, length=14)
	private String dueDatetime;

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

	public String getAuthenDatetime() {
		return authenDatetime;
	}

	public void setAuthenDatetime(String authenDatetime) {
		this.authenDatetime = authenDatetime;
	}

	public String getDueDatetime() {
		return dueDatetime;
	}

	public void setDueDatetime(String dueDatetime) {
		this.dueDatetime = dueDatetime;
	}
}
