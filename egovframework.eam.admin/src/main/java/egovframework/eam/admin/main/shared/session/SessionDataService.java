package egovframework.eam.admin.main.shared.session;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/sessionDataService")
public interface SessionDataService extends RemoteService {
	public List<SessionData> findAll();
	public List<SessionData> findWithLike(String fieldName, String value);
	public SessionData findById(String systemId);
}