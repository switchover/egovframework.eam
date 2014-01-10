package egovframework.eam.admin.api.user;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="users")
public class UserInfoList {
	private int count;
	private List<UserInfo> users;
	
	public UserInfoList() {
	}
	
	public UserInfoList(List<UserInfo> users) {
		this.users = users;
		this.count = users.size();
	}

	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	@XmlElementWrapper(name="data")
	@XmlElement(name="user")
	public List<UserInfo> getUsers() {
		return users;
	}
	
	public void setUsers(List<UserInfo> users) {
		this.users = users;
	}
}
