package dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import abstracts.Dao;
import entity.Currency;
import entity.CurrencyRate;

public class CurrencyDao implements Dao<Currency> {
	private EntityManager entityManager;
	
	public CurrencyDao(EntityManager em) {
		entityManager = em;
	}
	
	@Override
	public Optional get(Long id) {
		return Optional.ofNullable(entityManager.find(Currency.class, id));
	}

	@Override
	public List<Currency> getAll() {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Currency> criteriaQuery = builder.createQuery(Currency.class);
		criteriaQuery.from(Currency.class);
		return entityManager.createQuery(criteriaQuery).getResultList();
	}

	@Override
	public void save(Currency currency) {
		executeInsideTransaction(entityManager -> entityManager.persist(currency));
	}

	@Override
	public void update(Currency currency) {
		executeInsideTransaction(entityManager -> entityManager.merge(currency));
	}

	@Override
	public void delete(Currency currency) {
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
