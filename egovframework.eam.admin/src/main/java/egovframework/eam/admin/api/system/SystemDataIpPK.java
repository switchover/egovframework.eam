package egovframework.eam.admin.api.system;

import java.io.Serializable;

import javax.persistence.Column;

public class SystemDataIpPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5571922164421394668L;

	@Column(name="system_id", length=20)
	private String systemId;
    
    @Column(name="ip", length=16)
	private String ip;
    
    public SystemDataIpPK() {
    	
    }
    
    public SystemDataIpPK(String systemId, String ip) {
    	this.systemId = systemId;
    	this.ip = ip;
    }

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
