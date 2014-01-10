package egovframework.eam.admin.api.authority;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="authorities")
public class AuthorityInfoList {
	private int count;
	private List<AuthorityInfo> authorities;

	public AuthorityInfoList() {
	}
	
	public AuthorityInfoList(List<AuthorityInfo> authorities) {
		this.authorities = authorities;
		this.count = authorities.size();
	}

	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	@XmlElementWrapper(name="data")
	@XmlElement(name="authority")
	public List<AuthorityInfo> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(List<AuthorityInfo> authorities) {
		this.authorities = authorities;
	}
}
