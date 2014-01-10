package egovframework.eam.admin.api.authority;

import java.io.Serializable;

import javax.persistence.Column;

public class AuthorityPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4482903911930745853L;

	@Column(name="user_id", length=20)
	private String userId;
	
	@Column(name="authority", length=50)
	private String authority;
	
	public AuthorityPK() {
		
	}
	
	public AuthorityPK(String userId, String authority) {
		this.userId = userId;
		this.authority = authority;
	}

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
}
