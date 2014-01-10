package egovframework.eam.admin.api.resource;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import egovframework.eam.admin.api.orm.JpaDAO;

@Repository("jpaResourceRoleDao")
public class JpaResourceRoleDAO extends JpaDAO<String, ResourceRoleInfo> {
    
    @Autowired
    EntityManagerFactory entityManagerFactory;
    
    @PostConstruct
    public void init() {
        super.setEntityManagerFactory(entityManagerFactory);
    }
    
    public ResourceRoleInfo findByMultiId(ResourceRolePK ids) { 
        return getJpaTemplate().find(entityClass, ids); 
    }   
}