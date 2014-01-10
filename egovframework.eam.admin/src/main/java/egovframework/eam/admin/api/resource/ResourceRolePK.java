package egovframework.eam.admin.api.resource;

import java.io.Serializable;

import javax.persistence.Column;

public class ResourceRolePK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1409531036842517570L;

    @Column(name="resource_id", length=10)
	private String resourceId;
	
	@Column(name="authority", length=50)
	private String authority;

	public ResourceRolePK() {
		
	}
	
	public ResourceRolePK(String resourceId, String authority) {
		this.resourceId = resourceId;
		this.authority = authority;
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
}
