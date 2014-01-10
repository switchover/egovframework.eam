package egovframework.eam.admin.api.system;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import egovframework.eam.admin.api.orm.JpaDAO;

@Repository("jpaSystemDataIpDao")
public class JpaSystemDataIpDAO extends JpaDAO<String, SystemDataIpInfo> {
    
    @Autowired
    EntityManagerFactory entityManagerFactory;
    
    @PostConstruct
    public void init() {
        super.setEntityManagerFactory(entityManagerFactory);
    }
    
    public SystemDataIpInfo findByMultiId(SystemDataIpPK ids) { 
        return getJpaTemplate().find(entityClass, ids); 
    }   
    
}