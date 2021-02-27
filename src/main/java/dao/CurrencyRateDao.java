package dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import abstracts.Dao;
import entity.CurrencyRate;

public class CurrencyRateDao implements Dao<CurrencyRate>{
	private EntityManager entityManager;
	
	public CurrencyRateDao(EntityManager em) {
		entityManager = em;
	}
	
	@Override
	public Optional get(Long id) {
		return Optional.ofNullable(entityManager.find(CurrencyRate.class, id));
	}

	@Override
	public List<CurrencyRate> getAll() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CurrencyRate> criteriaQuery = builder.createQuery(CurrencyRate.class);
		criteriaQuery.from(CurrencyRate.class);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public void save(CurrencyRate currency) {
		executeInsideTransaction(entityManager -> entityManager.persist(currency));
	}

	@Override
	public void update(CurrencyRate currency) {
		executeInsideTransaction(entityManager -> entityManager.merge(currency));
	}

	@Override
	public void delete(CurrencyRate currency) {
		executeInsideTransaction(entityManager -> entityManager.remove(currency));
	}
	
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
