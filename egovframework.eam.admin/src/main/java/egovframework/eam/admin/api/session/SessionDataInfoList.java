package egovframework.eam.admin.api.session;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sessions")
public class SessionDataInfoList {
	private int count;
	private List<SessionDataInfo> sessions;

	public SessionDataInfoList() {
	}
	
	public SessionDataInfoList(List<SessionDataInfo> systems) {
		this.sessions = systems;
		this.count = systems.size();
	}

	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	@XmlElementWrapper(name="data")
	@XmlElement(name="session")
	public List<SessionDataInfo> getSessions() {
		return sessions;
	}
	
	public void setSessions(List<SessionDataInfo> sessions) {
		this.sessions = sessions;
	}
}
