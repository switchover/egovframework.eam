package egovframework.eam.admin.main.shared.resource;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/resourceRoleService")
public interface ResourceRoleService extends RemoteService {
	public List<ResourceRole> findAll();
	public List<ResourceRole> findWithLike(String fieldName, String value);
	public ResourceRole findById(ResourceRole resourceRole);
	public void addResourceRole(ResourceRole resourceRole);
	public void updateResourceRole(ResourceRole resourceRole);
	public void deleteResourceRole(ResourceRole resourceRole);
}
