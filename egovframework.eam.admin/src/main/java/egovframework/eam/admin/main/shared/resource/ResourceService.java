package egovframework.eam.admin.main.shared.resource;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/resourceService")
public interface ResourceService extends RemoteService {
	public List<Resource> findAll();
	public List<Resource> findWithLike(String fieldName, String value);
	public Resource findById(String resourceId);
	public void addResource(Resource role);
	public void updateResource(Resource role);
	public void deleteResource(String resourceId);
}
