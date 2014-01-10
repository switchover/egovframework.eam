package egovframework.eam.admin.main.shared.role;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/roleHierarchyService")
public interface RoleHierarchyService extends RemoteService {
	public List<RoleHierarchy> findAll();
	public RoleHierarchy findById(RoleHierarchy roleHierarchy);
	public void addRoleHierarchy(RoleHierarchy roleHierarchy);
	public void deleteRoleHierarchy(RoleHierarchy roleHierarchy);
}
