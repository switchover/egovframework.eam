package egovframework.eam.admin.api.system;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "egovam_systems")
@XmlRootElement(name="system")
public class SystemDataInfo {
    @Id
    @Column(name="system_id", length=20)
	private String systemId;
    
    @Column(name="system_name", nullable=false, length=50)
	private String systemName;
	
    @Column(name="password", nullable=false, length=20)
	private String password;
	
    @Column(name="created_datetime", nullable=false, length=14)
	private String createdDatetime;
    
    @Transient
    private List<String> ipList;

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
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

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

    @XmlElementWrapper(name="ipList")
    @XmlElement(name="ip")
	public List<String> getIpList() {
		return ipList;
	}

	public void setIpList(List<String> ipList) {
		this.ipList = ipList;
	}
}
