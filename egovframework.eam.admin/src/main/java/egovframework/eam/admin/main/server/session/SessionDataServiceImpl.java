package egovframework.eam.admin.main.server.session;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.eam.admin.api.session.JpaSessionDataDAO;
import egovframework.eam.admin.api.session.SessionDataInfo;
import egovframework.eam.admin.main.server.common.DateUtils;
import egovframework.eam.admin.main.server.util.BeanOperator;
import egovframework.eam.admin.main.shared.session.SessionData;
import egovframework.eam.admin.main.shared.session.SessionDataService;

@Service("sessionDataService")
public class SessionDataServiceImpl implements SessionDataService {
	@Resource(name = "jpaSessionDataDao")
	private JpaSessionDataDAO sessionDataDao;
	
	private BeanOperator<SessionDataInfo, SessionData> bean = new BeanOperator<SessionDataInfo, SessionData>(SessionData.class);
	
	public List<SessionData> findAll() {
		List<SessionDataInfo> sessions = sessionDataDao.findAll();
		
		for (SessionDataInfo session : sessions) {
			session.setSystemName(session.getSystem().getSystemName());
			
			session.setIssueDatetime(DateUtils.getDateString(session.getIssueDatetime()));
			session.setDueDatetime(DateUtils.getDateString(session.getDueDatetime()));
		}
		
		return bean.cloneList(sessions);
	}

	public List<SessionData> findWithLike(String fieldName, String value) {
		List<SessionDataInfo> sessions = sessionDataDao.findWithLike(fieldName, value);
		
		for (SessionDataInfo session : sessions) {
			session.setSystemName(session.getSystem().getSystemName());
			
			session.setIssueDatetime(DateUtils.getDateString(session.getIssueDatetime()));
			session.setDueDatetime(DateUtils.getDateString(session.getDueDatetime()));
		}
		
		return bean.cloneList(sessions);
	}
	
	public SessionData findById(String sessionId) {
		SessionDataInfo session = sessionDataDao.findById(sessionId);
		
		session.setSystemName(session.getSystem().getSystemName());
		
		session.setIssueDatetime(DateUtils.getDateString(session.getIssueDatetime()));
		session.setDueDatetime(DateUtils.getDateString(session.getDueDatetime()));
		
		return bean.cloneBean(session);
	}
}
