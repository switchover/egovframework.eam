package egovframework.eam.admin.api.role;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import egovframework.eam.admin.api.orm.JpaDAO;

@Repository("jpaRoleHierarchyDao")
public class JpaRoleHierarchyDAO extends JpaDAO<String, RoleHierarchyInfo> {
    
    @Autowired
    EntityManagerFactory entityManagerFactory;
    
    @PostConstruct
    public void init() {
        super.setEntityManagerFactory(entityManagerFactory);
    }
    
    public RoleHierarchyInfo findByMultiId(RoleHierarchyPK ids) { 
        return getJpaTemplate().find(entityClass, ids); 
    }   
}