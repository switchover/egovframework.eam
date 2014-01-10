package egovframework.eam.admin.api.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "users")
@XmlRootElement(name="user")
public class UserInfo {
	@Id
    @Column(name="user_id", length=20)
	private String userId;
	
    @Column(name="password", nullable=false, length=255)
	private String password;
    
    @Column(name="enabled", nullable=false)
	private boolean enabled;
    
    @Column(name="user_name", nullable=false, length=50)
	private String userName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
