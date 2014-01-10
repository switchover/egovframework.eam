package egovframework.eam.admin.api.orm;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

public abstract class JpaDAO<K, E> extends JpaDaoSupport { 
    protected Class<E> entityClass; 

    @SuppressWarnings("unchecked") 
    public JpaDAO() { 
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass(); 
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1]; 
    } 

    public void persist(E entity) { 
        getJpaTemplate().persist(entity); 
    } 

    public void remove(E entity) { 
        getJpaTemplate().remove(entity); 
    } 
    
    public E merge(E entity) { 
        return getJpaTemplate().merge(entity); 
    } 
    
    public void refresh(E entity) { 
        getJpaTemplate().refresh(entity); 
    } 

    public E findById(K id) { 
        return getJpaTemplate().find(entityClass, id); 
    } 
    
    public E flush(E entity) { 
        getJpaTemplate().flush(); 
        return entity; 
    } 
    
    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    public List<E> findAll() { 
        Object res = getJpaTemplate().execute(new JpaCallback() { 

            public Object doInJpa(EntityManager em) throws PersistenceException { 
                Query q = em.createQuery("SELECT h FROM " + entityClass.getName() + " h"); 
                return q.getResultList(); 
            } 
            
        }); 
        
        return (List<E>) res; 
    } 
    
    @SuppressWarnings({ "unchecked" }) 
    public List<E> findWithLike(String fieldName, String value) { 
    	JpaCallbackWithLike callback = new JpaCallbackWithLike(entityClass, fieldName, value);
    	
        Object res = getJpaTemplate().execute(callback);
        
        return (List<E>) res; 
    }
    
    @SuppressWarnings("unchecked")
	public List<E> findWithEqual(String fieldName, String value) { 
    	JpaCallbackWithEqual callback = new JpaCallbackWithEqual(entityClass, fieldName, value);
    	
        Object res = getJpaTemplate().execute(callback);
        
        return (List<E>) res; 
    } 

    @SuppressWarnings({ "unchecked", "rawtypes" }) 
    public Integer removeAll() { 
        return (Integer) getJpaTemplate().execute(new JpaCallback() { 

            public Object doInJpa(EntityManager em) throws PersistenceException { 
                Query q = em.createQuery("DELETE FROM " + entityClass.getName() + " h"); 
                return q.executeUpdate(); 
            } 
            
        }); 
    }
    
}

@SuppressWarnings("rawtypes")
class JpaCallbackWithLike implements JpaCallback {
	private Class entityClass;
	private String fieldName;
	private String value;
	
	public JpaCallbackWithLike(Class entityClass, String fieldName, String value) {
		this.entityClass = entityClass;
		this.fieldName = fieldName;
		this.value = value;
	}
	
	public Object doInJpa(EntityManager em) throws PersistenceException {
		 Query q = em.createQuery("SELECT h FROM " + entityClass.getName() + " h where " + fieldName + " like :value");
         q.setParameter("value", "%" + value + "%");
         return q.getResultList(); 
	}
}

@SuppressWarnings("rawtypes")
class JpaCallbackWithEqual implements JpaCallback {
	private Class entityClass;
	private String fieldName;
	private String value;
	
	public JpaCallbackWithEqual(Class entityClass, String fieldName, String value) {
		this.entityClass = entityClass;
		this.fieldName = fieldName;
		this.value = value;
	}
	
	public Object doInJpa(EntityManager em) throws PersistenceException {
		 Query q = em.createQuery("SELECT h FROM " + entityClass.getName() + " h where " + fieldName + " = :value");
         q.setParameter("value", value);
         return q.getResultList(); 
	}
}