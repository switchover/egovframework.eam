package egovframework.eam.admin.main.server.system;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import egovframework.eam.admin.api.system.JpaSystemDataDAO;
import egovframework.eam.admin.api.system.JpaSystemDataIpDAO;
import egovframework.eam.admin.api.system.SystemDataInfo;
import egovframework.eam.admin.api.system.SystemDataIpInfo;
import egovframework.eam.admin.main.server.common.DateUtils;
import egovframework.eam.admin.main.server.util.BeanOperator;
import egovframework.eam.admin.main.shared.system.SystemData;
import egovframework.eam.admin.main.shared.system.SystemDataService;
import egovframework.eam.api.system.SystemServiceUtil;

@Service("systemDataService")
public class SystemDataServiceImpl implements SystemDataService {
	@Resource(name = "jpaSystemDataDao")
	private JpaSystemDataDAO systemDataDao;
	
	@Resource(name = "jpaSystemDataIpDao")
	private JpaSystemDataIpDAO systemDataIpDao;
	
	private BeanOperator<SystemDataInfo, SystemData> bean = new BeanOperator<SystemDataInfo, SystemData>(SystemData.class);
	
	public List<SystemData> findAll() {
		List<SystemDataInfo> systems = systemDataDao.findAll();
		
		for (SystemDataInfo system : systems) {
			addIpList(system);
			
			system.setCreatedDatetime(DateUtils.getDateString(system.getCreatedDatetime()));
		}
		
		return bean.cloneList(systems);
	}

	public List<SystemData> findWithLike(String fieldName, String value) {
		List<SystemDataInfo> systems = systemDataDao.findWithLike(fieldName, value);
		
		for (SystemDataInfo system : systems) {
			addIpList(system);
			
			system.setCreatedDatetime(DateUtils.getDateString(system.getCreatedDatetime()));
		}
		
		return bean.cloneList(systems);
	}
	
	public SystemData findById(String systemId) {
		SystemDataInfo system = systemDataDao.findById(systemId);
		
		addIpList(system);
		
		system.setCreatedDatetime(DateUtils.getDateString(system.getCreatedDatetime()));
		
		return bean.cloneBean(system);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void addSystem(SystemData system) {
		SystemDataInfo entity = systemDataDao.findById(system.getSystemId());
		
		if (entity == null) {
			entity = new SystemDataInfo();
			bean.copyBean(system, entity);
			
			entity.setCreatedDatetime(DateUtils.getCurrentString());
			
			systemDataDao.persist(entity);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void updateSystem(SystemData system) {
		SystemDataInfo entity = systemDataDao.findById(system.getSystemId());
		
		if (entity != null) {
			system.setCreatedDatetime(entity.getCreatedDatetime());
			
			bean.copyBean(system, entity);
			
			systemDataDao.merge(entity);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deleteSystem(String systemId) {
		SystemDataInfo entity = systemDataDao.findById(systemId);
		
		if (entity != null) {
			systemDataDao.remove(entity);
		}	
	}
	
	public String getAuthKey(String systemId) {
		SystemDataInfo entity = systemDataDao.findById(systemId);
		
		if (entity != null) {
			return SystemServiceUtil.getAuthKey(SystemServiceUtil.DIGEST_ALGORITHM, entity.getPassword());
		} else {
			return "";
		}
	}

	private void addIpList(SystemDataInfo system) {
		List<String> ipList = new ArrayList<String>();
		
		List<SystemDataIpInfo> ip = systemDataIpDao.findWithEqual("system_id", system.getSystemId());
		for (SystemDataIpInfo info : ip) {
			ipList.add(info.getIp());
		}
		
		system.setIpList(ipList);
	}
}
