package egovframework.eam.api.user;

import java.io.Serializable;

public class UserVO implements Serializable {
	/**
	 * SerialVer UID.
	 */
	private static final long serialVersionUID = 6946073094212387306L;
	
	private String userId;
	private String password;
	private boolean enabled;
	private String userName;
	
	public String getEncodedPassword() {
		return encode(password);
	}
	
	private String encode(String password) {
		return DefaultUserHelper.encodePassword(password);
	}
	
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
