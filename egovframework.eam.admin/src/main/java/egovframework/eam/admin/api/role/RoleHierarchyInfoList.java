package egovframework.eam.admin.api.role;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="roleHierarchies")
public class RoleHierarchyInfoList {
	private int count;
	private List<RoleHierarchyInfo> roleHierarchies;

	public RoleHierarchyInfoList() {
	}
	
	public RoleHierarchyInfoList(List<RoleHierarchyInfo> roleHierarchies) {
		this.roleHierarchies = roleHierarchies;
		this.count = roleHierarchies.size();
	}

	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	@XmlElementWrapper(name="data")
	@XmlElement(name="roleHierarchy")
	public List<RoleHierarchyInfo> getRoleHierarchies() {
		return roleHierarchies;
	}
	
	public void setRoleHierarchies(List<RoleHierarchyInfo> roleHierarchies) {
		this.roleHierarchies = roleHierarchies;
	}
}
