package egovframework.eam.admin.api.role;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "roles")
@XmlRootElement(name="role")
public class RoleInfo {
	@Id
    @Column(name="authority", length=50)
	private String authority;
	
	@Column(name="role_name", nullable=false, length=50)
	private String roleName;

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
