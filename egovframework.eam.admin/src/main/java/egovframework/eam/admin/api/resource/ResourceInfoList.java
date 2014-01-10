package egovframework.eam.admin.api.resource;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="resources")
public class ResourceInfoList {
	private int count;
	private List<ResourceInfo> resources;

	public ResourceInfoList() {
	}
	
	public ResourceInfoList(List<ResourceInfo> resources) {
		this.resources = resources;
		this.count = resources.size();
	}

	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	@XmlElementWrapper(name="data")
	@XmlElement(name="resource")
	public List<ResourceInfo> getResources() {
		return resources;
	}
	
	public void setResources(List<ResourceInfo> resources) {
		this.resources = resources;
	}
}
