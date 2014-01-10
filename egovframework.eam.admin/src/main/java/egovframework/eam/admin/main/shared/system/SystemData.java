package egovframework.eam.admin.main.shared.system;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SystemData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2570833882366775645L;

	private String systemId;
	
	private String systemName;
	
	private String password;
	
	private String createdDatetime;
	
	private List<String> ipList = new ArrayList<String>();

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreatedDatetime() {
		return createdDatetime;
	}

	public void setCreatedDatetime(String createdDatetime) {
		this.createdDatetime = createdDatetime;
	}
	
	public List<String> getIpList() {
		return ipList;
	}

	public void setIpList(List<String> ipList) {
		this.ipList = ipList;
	}
	
	public void addIpList(String ip) {
		ipList.add(ip);
	}
}
