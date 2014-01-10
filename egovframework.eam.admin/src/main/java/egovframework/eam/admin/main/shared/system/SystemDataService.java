package egovframework.eam.admin.main.shared.system;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/systemDataService")
public interface SystemDataService extends RemoteService {
	public List<SystemData> findAll();
	public List<SystemData> findWithLike(String fieldName, String value);
	public SystemData findById(String systemId);
	public void addSystem(SystemData system);
	public void updateSystem(SystemData system);
	public void deleteSystem(String systemId);
	public String getAuthKey(String systemId);
}
