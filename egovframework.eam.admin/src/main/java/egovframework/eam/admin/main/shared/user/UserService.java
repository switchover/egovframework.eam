package egovframework.eam.admin.main.shared.user;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/userService")
public interface UserService extends RemoteService {
	public List<User> findAll();
	public List<User> findWithLike(String fieldName, String value);
	public User findById(String userId);
	public void addUser(User user);
	public void updateUser(User user);
	public void deleteUser(String userId);
}
