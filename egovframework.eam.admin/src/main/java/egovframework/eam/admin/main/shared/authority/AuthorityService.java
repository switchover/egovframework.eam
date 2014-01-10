package egovframework.eam.admin.main.shared.authority;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("springGwtServices/authorityService")
public interface AuthorityService extends RemoteService {
	public List<Authority> findAll();
	public List<Authority> findWithLike(String fieldName, String value);
	public Authority findById(Authority authority);
	public void addAuthority(Authority authority);
	public void updateAuthority(Authority authority);
	public void deleteAuthority(Authority authority);
}
