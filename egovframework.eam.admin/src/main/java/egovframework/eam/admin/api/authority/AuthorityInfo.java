package egovframework.eam.admin.api.authority;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import egovframework.eam.admin.main.shared.role.Role;
import egovframework.eam.admin.main.shared.user.User;

@Entity
@Table(name = "authorities")
@XmlRootElement(name="authority")
public class AuthorityInfo {
	@EmbeddedId
	private AuthorityPK authoritPK;
	
	@Transient
	@XmlTransient
	private String userId;
	
	@Transient
	@XmlTransient
	private String authority;
	
	@Transient
	private User user;
	
	@Transient
	private Role role;

	public AuthorityPK getAuthorityPK() {
		return authoritPK;
	}

	public void setAuthorityPK(AuthorityPK authoritPK) {
		this.authoritPK = authoritPK;
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
