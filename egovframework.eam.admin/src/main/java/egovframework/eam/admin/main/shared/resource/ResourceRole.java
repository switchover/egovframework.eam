package egovframework.eam.admin.main.shared.resource;

import java.io.Serializable;

import egovframework.eam.admin.main.shared.role.Role;

public class ResourceRole implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7789052124615554183L;

	private String resourceId;
	
	private String authority;

	private Resource resource;
	
	private Role role;

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
