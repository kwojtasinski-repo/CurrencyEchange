package dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.cfg.Configuration;

import abstracts.SpecialDao;

public class SpecialDaoImpl implements SpecialDao {
	private EntityManager entityManager;
	
	public SpecialDaoImpl (String hibernateConfigFile) {
		entityManager = new Configuration().configure(hibernateConfigFile).buildSessionFactory().createEntityManager();
	}
	
	public <T> T getByNamedQuery(String queryString, Map<String,Object> parameters) {
		Query query = entityManager.createNamedQuery(queryString);
		for(Map.Entry<String, Object> entry : parameters.entrySet()) {
    		query.setParameter(entry.getKey(), entry.getValue());
    	}
		T obj;
		try {
			obj = (T) query.getSingleResult();
		} catch(NoResultException e) {
			obj = null;
		}
		return obj;
	}
	
	public <T> T getByNativeQuery(String queryString, Map<String,Object> parameters) {
		Query query = entityManager.createNativeQuery(queryString);
		for(Map.Entry<String, Object> entry : parameters.entrySet()) {
    		query.setParameter(entry.getKey(), entry.getValue());
    	}
		T obj;
		try {
			obj = (T) query.getSingleResult();
		} catch(NoResultException e) {
			obj = null;
		}
		return obj;
	}
	
	public <T> List<T> getAllByNamedQuery(String queryString, Map<String,Object> parameters) {
		Query query = entityManager.createNamedQuery(queryString);
		for(Map.Entry<String, Object> entry : parameters.entrySet()) {
    		query.setParameter(entry.getKey(), entry.getValue());
    	}
		List<T> objects = query.getResultList();
		return objects;
	}
	
	public <T> List<T> getAllByNativeQuery(String queryString, Map<String,Object> parameters) {
		Query query = entityManager.createNativeQuery(queryString);
		for(Map.Entry<String, Object> entry : parameters.entrySet()) {
    		query.setParameter(entry.getKey(), entry.getValue());
    	}
		List<T> objects = query.getResultList();
		return objects;
	}

}
