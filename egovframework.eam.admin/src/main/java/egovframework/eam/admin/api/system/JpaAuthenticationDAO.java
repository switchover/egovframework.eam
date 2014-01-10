package egovframework.eam.admin.api.system;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import egovframework.eam.admin.api.orm.JpaDAO;

@Repository("jpaAuthenticationDao")
public class JpaAuthenticationDAO extends JpaDAO<String, AuthenticationInfo> {
    
    @Autowired
    EntityManagerFactory entityManagerFactory;
    
    @PostConstruct
    public void init() {
        super.setEntityManagerFactory(entityManagerFactory);
    }
    
    public AuthenticationInfo findByMultiId(AuthenticationPK ids) { 
        return getJpaTemplate().find(entityClass, ids); 
    }   
}