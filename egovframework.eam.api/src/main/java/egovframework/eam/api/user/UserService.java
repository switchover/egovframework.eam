package egovframework.eam.api.user;

public interface UserService {
	public boolean isExisted(String userId);
	public UserVO getUser(String userId);
	public void addUser(UserVO user);
	public void modifyUser(UserVO user);
	public void deleteUser(String userId);
	public void disableUser(String userId);
}
