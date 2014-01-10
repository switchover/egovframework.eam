package egovframework.eam.admin.main.shared.resource;

import java.io.Serializable;

public class Resource implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4095519092802023018L;

	private String resourceId;
	
	private String resourceName;
	
	private String resourcePattern;
	
	private String resourceType;
	
	private int sortOrder;

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourcePattern() {
		return resourcePattern;
	}

	public void setResourcePattern(String resourcePattern) {
		this.resourcePattern = resourcePattern;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
}
