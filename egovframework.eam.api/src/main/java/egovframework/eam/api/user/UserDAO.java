package egovframework.eam.api.user;

public interface UserDAO {
	UserVO select(String userId);
	void insert(UserVO user);
	void update(UserVO user);
	void delete(String userId);
}
