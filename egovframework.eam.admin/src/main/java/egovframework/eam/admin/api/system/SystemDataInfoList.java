package egovframework.eam.admin.api.system;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="systems")
public class SystemDataInfoList {
	private int count;
	private List<SystemDataInfo> systems;

	public SystemDataInfoList() {
	}
	
	public SystemDataInfoList(List<SystemDataInfo> systems) {
		this.systems = systems;
		this.count = systems.size();
	}

	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	@XmlElementWrapper(name="data")
	@XmlElement(name="system")
	public List<SystemDataInfo> getSystems() {
		return systems;
	}
	
	public void setSystems(List<SystemDataInfo> systems) {
		this.systems = systems;
	}
}
