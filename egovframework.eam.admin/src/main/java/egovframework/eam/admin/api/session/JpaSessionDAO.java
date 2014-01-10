package egovframework.eam.admin.api.session;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import egovframework.eam.admin.api.orm.JpaDAO;

@Repository("jpaSessionDao")
public class JpaSessionDAO extends JpaDAO<String, SessionInfo> {
    
    @Autowired
    EntityManagerFactory entityManagerFactory;
    
    @PostConstruct
    public void init() {
        super.setEntityManagerFactory(entityManagerFactory);
    }
    
}