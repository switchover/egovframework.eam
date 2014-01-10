package egovframework.eam.admin.api.authority;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import egovframework.eam.admin.api.orm.JpaDAO;

@Repository("jpaAuthorityDao")
public class JpaAuthorityDAO extends JpaDAO<String, AuthorityInfo> {
    
    @Autowired
    EntityManagerFactory entityManagerFactory;
    
    @PostConstruct
    public void init() {
        super.setEntityManagerFactory(entityManagerFactory);
    }
    
    public AuthorityInfo findByMultiId(AuthorityPK ids) { 
        return getJpaTemplate().find(entityClass, ids); 
    }   
}