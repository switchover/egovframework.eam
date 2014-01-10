package egovframework.eam.admin.api.resource;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import egovframework.eam.admin.main.shared.resource.Resource;
import egovframework.eam.admin.main.shared.role.Role;

@Entity
@Table(name = "secured_resources_role")
@XmlRootElement(name="resourceRole")
public class ResourceRoleInfo {
	@EmbeddedId
	private ResourceRolePK resourceRolePK;
	
	@Transient
	@XmlTransient
	private String resourceId;
	
	@Transient
	@XmlTransient
	private String authority;
	
	@Transient
	private Resource resource;
	
	@Transient
	private Role role;

	public ResourceRolePK getResourceRolePK() {
		return resourceRolePK;
	}

	public void setResourceRolePK(ResourceRolePK resourceRolePK) {
		this.resourceRolePK = resourceRolePK;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
