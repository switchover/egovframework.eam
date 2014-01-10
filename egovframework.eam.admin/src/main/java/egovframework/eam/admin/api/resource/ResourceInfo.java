package egovframework.eam.admin.api.resource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "secured_resources")
@XmlRootElement(name="resource")
public class ResourceInfo {
	@Id
    @Column(name="resource_id", length=10)
	private String resourceId;
	
	@Column(name="resource_name", length=50)
	private String resourceName;
	
	@Column(name="resource_pattern", nullable=false, length=100)
	private String resourcePattern;
	
	@Column(name="resource_type", length=10)
	private String resourceType;
	
	@Column(name="sort_order")
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
