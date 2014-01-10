package egovframework.eam.api.config;

public class SystemInfoConfig {
	private String systemId;
	private String authKey;
	private String password;
	
	private String adminServerIp;
	private String adminServerPort;
	
	private boolean skipOnRemoteError = true;
	
	public String getSystemId() {
		return systemId;
	}
	
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	public String getAuthKey() {
		return authKey;
	}
	
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getAdminServerIp() {
		return adminServerIp;
	}

	public void setAdminServerIp(String adminServerIp) {
		this.adminServerIp = adminServerIp;
	}

	public String getAdminServerPort() {
		return adminServerPort;
	}

	public void setAdminServerPort(String adminServerPort) {
		this.adminServerPort = adminServerPort;
	}

	public boolean isSkipOnRemoteError() {
		return skipOnRemoteError;
	}

	public void setSkipOnRemoteError(boolean skipOnRemoteError) {
		this.skipOnRemoteError = skipOnRemoteError;
	}
}
