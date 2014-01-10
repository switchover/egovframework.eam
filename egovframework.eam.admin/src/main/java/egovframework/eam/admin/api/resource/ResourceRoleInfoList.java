package egovframework.eam.admin.api.resource;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="resourceRoles")
public class ResourceRoleInfoList {
	private int count;
	private List<ResourceRoleInfo> resourceRoles;

	public ResourceRoleInfoList() {
	}
	
	public ResourceRoleInfoList(List<ResourceRoleInfo> resourceRoles) {
		this.resourceRoles = resourceRoles;
		this.count = resourceRoles.size();
	}

	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	@XmlElementWrapper(name="data")
	@XmlElement(name="resourceRole")
	public List<ResourceRoleInfo> getResourceRoles() {
		return resourceRoles;
	}
	
	public void setResourceRoles(List<ResourceRoleInfo> resourceRoles) {
		this.resourceRoles = resourceRoles;
	}
}
