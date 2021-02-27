package dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import abstracts.Dao;
import entity.Country;

public class CountryDao implements Dao<Country> {
	private EntityManager entityManager;
	
	public CountryDao(EntityManager em) {
		entityManager = em;
	}
	
	@Override
	public Optional get(Long id) {
		return Optional.ofNullable(entityManager.find(Country.class, id));
	}

	@Override
	public List<Country> getAll() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Country> criteriaQuery = builder.createQuery(Country.class);
		criteriaQuery.from(Country.class);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public void save(Country country) {
		executeInsideTransaction(entityManager -> entityManager.persist(country));
	}

	@Override
	public void update(Country country) {
		executeInsideTransaction(entityManager -> entityManager.merge(country));
	}

	@Override
	public void delete(Country country) {
		executeInsideTransaction(entityManager -> entityManager.remove(country));
	}

	@Transactional
	private void executeInsideTransaction(Consumer<EntityManager> action) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit(); 
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }
	
	// for generic types
	//private Class getType() {
	//	return type;
    //}
}
