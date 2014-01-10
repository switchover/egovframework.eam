package egovframework.eam.admin.api.role;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="roles")
public class RoleInfoList {
	private int count;
	private List<RoleInfo> roles;

	public RoleInfoList() {
	}
	
	public RoleInfoList(List<RoleInfo> roles) {
		this.roles = roles;
		this.count = roles.size();
	}

	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	@XmlElementWrapper(name="data")
	@XmlElement(name="role")
	public List<RoleInfo> getRoles() {
		return roles;
	}
	
	public void setRoles(List<RoleInfo> roles) {
		this.roles = roles;
	}
}
