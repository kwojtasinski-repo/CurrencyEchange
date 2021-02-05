package com.exchange.ExchangesRateMaven.Repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.exchange.ExchangesRateMaven.Domain.Entity.CurrencyTradeRate;
import com.exchange.ExchangesRateMaven.Domain.Interface.EntityRepository;

public class CurrencyTradeRateRepository implements EntityRepository<CurrencyTradeRate> {
    SessionFactory sessionFactory;
    
	public CurrencyTradeRateRepository() {
		// TODO Auto-generated constructor stub
//		sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();//configure("hibernate.cfg.xml").build();  
	}
	
	@Override
	public Long add(CurrencyTradeRate currencyTradeRate) {
		// TODO Auto-generated method stub
        Session session = sessionFactory.openSession();
        System.out.println("Creating currency trade");
        try {
        	session.beginTransaction();
			session.save(currencyTradeRate);
			session.getTransaction().commit();
        }
        catch(Exception ex) {
        	System.out.println(ex.getMessage());
        }
        finally {
        	session.close();
        }
		return currencyTradeRate.getId();
	}

	@Override
	public CurrencyTradeRate getById(Long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Getting object by id = " + id);
        CurrencyTradeRate currencyTradeRate = null;
        try {
            currencyTradeRate =  (CurrencyTradeRate) session.get(CurrencyTradeRate.class, id);
        } 
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        finally {
        	session.close();
        }   
		return currencyTradeRate;
	}

	@Override
	public List<CurrencyTradeRate> getAll() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Getting all of objects!");  
        List<CurrencyTradeRate> currencyTradeRates = new ArrayList<CurrencyTradeRate>();
        try {
        	currencyTradeRates = loadAllData(CurrencyTradeRate.class, session);
        } 
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        finally {
        	session.close();
        } 
		return currencyTradeRates;
	}

	@Override
	public void update(CurrencyTradeRate currencyTradeRate) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Updating object id = " + currencyTradeRate.getId());  
		try {
        	session.beginTransaction();
        	session.update(currencyTradeRate);
        	session.getTransaction().commit();
        } 
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        finally {  
        	session.close();
        }
	}

	@Override
	public void delete(CurrencyTradeRate currencyTradeRate) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Deleting object id = " + currencyTradeRate.getId());  
		try {
        	session.beginTransaction();
        	session.delete(currencyTradeRate);
            session.getTransaction().commit();
        } 
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        finally {
        	session.close();
        }
	}
	
	private static <T> List<T> loadAllData(Class<T> type, Session session) {
	    CriteriaBuilder builder = session.getCriteriaBuilder();
	    CriteriaQuery<T> criteria = builder.createQuery(type);
	    criteria.from(type);
	    List<T> data = session.createQuery(criteria).getResultList();
	    return data;
	  }
}
