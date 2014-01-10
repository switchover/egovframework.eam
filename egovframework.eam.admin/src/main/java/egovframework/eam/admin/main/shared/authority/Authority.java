package egovframework.eam.admin.main.shared.authority;

import java.io.Serializable;

import egovframework.eam.admin.main.shared.role.Role;
import egovframework.eam.admin.main.shared.user.User;

public class Authority implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3521292817122993311L;

	private String userId;
	
	private String authority;
	
	private User user;
	
	private Role role;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
