package Repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;  
import org.hibernate.SessionFactory;  
import org.hibernate.Transaction;  
import org.hibernate.boot.Metadata;  
import org.hibernate.boot.MetadataSources;  
import org.hibernate.boot.registry.StandardServiceRegistry;  
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import Entity.CurrencyTradeRate;  

public class CurrencyTradeRateRepository implements EntityRepository<CurrencyTradeRate>
{
	StandardServiceRegistry ssr;  
    Metadata meta;
	
	public CurrencyTradeRateRepository() {
		// TODO Auto-generated constructor stub
		ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();  
	    meta = new MetadataSources(ssr).getMetadataBuilder().build();  
	}
	
	@Override
	public Long add(CurrencyTradeRate currencyTradeRate) {
		// TODO Auto-generated method stub
		SessionFactory factory = meta.getSessionFactoryBuilder().build();  
        Session session = factory.openSession();  
        Transaction t = session.beginTransaction();  
        session.save(currencyTradeRate);  
        t.commit();      
        factory.close();  
        session.close();
		return currencyTradeRate.getId();
	}

	@Override
	public CurrencyTradeRate getById(Long id) {
		// TODO Auto-generated method stub
		SessionFactory factory = meta.getSessionFactoryBuilder().build();  
        CurrencyTradeRate currencyTradeRate = null;
        Session session = null;
        try {
        	session = factory.openSession(); 
            currencyTradeRate =  (CurrencyTradeRate) session.get(CurrencyTradeRate.class, id);
        } 
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        finally
        {
        	factory.close();  
        	session.close();
        }   
		return currencyTradeRate;
	}

	@Override
	public List<CurrencyTradeRate> getAll() {
		// TODO Auto-generated method stub
		SessionFactory factory = meta.getSessionFactoryBuilder().build();  
        Session session = null;
        List<CurrencyTradeRate> currencyTradeRates = new ArrayList<CurrencyTradeRate>();
        try {
        	session = factory.openSession(); 
        	currencyTradeRates = loadAllData(CurrencyTradeRate.class, session);
        } 
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        finally
        {
        	factory.close();  
        	session.close();
        } 
		
		return currencyTradeRates;
	}

	@Override
	public void update(CurrencyTradeRate currencyTradeRate) {
		// TODO Auto-generated method stub
		SessionFactory factory = meta.getSessionFactoryBuilder().build();
		Session session = null;
		try {
        	session = factory.openSession(); 
        	session.beginTransaction();
        	session.update(currencyTradeRate);
        	session.getTransaction().commit();
        } 
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        finally
        {
        	factory.close();  
        	session.close();
        }
	}

	@Override
	public void delete(CurrencyTradeRate currencyTradeRate) {
		// TODO Auto-generated method stub
		SessionFactory factory = meta.getSessionFactoryBuilder().build();
		Session session = null;
		try {
        	session = factory.openSession(); 
        	session.beginTransaction();
        	session.delete(currencyTradeRate);
        } 
        catch (Exception e) {
        	System.out.println(e.getMessage());
        }
        finally
        {
        	factory.close();  
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
