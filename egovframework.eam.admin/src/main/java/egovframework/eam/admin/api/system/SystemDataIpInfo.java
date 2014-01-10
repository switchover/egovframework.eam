package egovframework.eam.admin.api.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "egovam_systems_ip")
@IdClass(SystemDataIpPK.class)
@XmlRootElement(name="ip")
public class SystemDataIpInfo {
    @Id
    @Column(name="system_id", length=20)
	private String systemId;
    
    @Id
    @Column(name="ip", length=16)
	private String ip;

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
}
