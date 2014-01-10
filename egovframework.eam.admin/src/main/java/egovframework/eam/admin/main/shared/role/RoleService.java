package egovframework.eam.admin.main.shared.role;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/roleService")
public interface RoleService extends RemoteService {
	public List<Role> findAll();
	public List<Role> findWithLike(String fieldName, String value);
	public Role findById(String authority);
	public void addRole(Role role);
	public void updateRole(Role role);
	public void deleteRole(String authority);
}
