package repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.JDBCException;
import org.hibernate.MappingException;
import org.hibernate.QueryException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.TransactionException;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.SQLGrammarException;
import org.hibernate.query.Query;
import org.hibernate.tool.schema.spi.SchemaManagementException;

import abstracts.CurrencyRepository;
import entity.Country;
import entity.Currency;
import entity.CurrencyCountry;
import entity.CurrencyExchange;
import entity.CurrencyExchangeKey;
import entity.CurrencyRate;
import exception.DatabaseException;
import exception.UncheckedIOException;

public class CurrencyRepositoryImpl implements CurrencyRepository {
	SessionFactory sessionFactory;
    
	public CurrencyRepositoryImpl() {
		// TODO Auto-generated constructor stub
		sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();//configure("hibernate.cfg.xml").build();  
	}
	
	public void setSession(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
	}
	
	@Override
	public CurrencyExchangeKey addCurrencyExchange(CurrencyExchange currencyExchanged) {
		// TODO Auto-generated method stub
        System.out.println("Creating currency trade");
        add(currencyExchanged);
		return currencyExchanged.getId();
	}

	@Override
	public Long addRate(CurrencyRate rate) {
		// TODO Auto-generated method stub
        System.out.println("Creating currency trade");
        add(rate);
		return rate.getCurrencyId();
	}

	@Override
	public Long addCurrency(Currency currency) {
		System.out.println("Creating currency");
		add(currency);
		return currency.getId();
	}
	
	@Override
	public Long addCountry(Country country) {
		// TODO Auto-generated method stub
		add(country);
		return country.getCountryId();
	}

	@Override
	public CurrencyCountry addCurrencyCountry(CurrencyCountry currencyCountry) {
		// TODO Auto-generated method stub
		add(currencyCountry);
		return currencyCountry;
	}
	
	@Override
	public CurrencyRate getRateById(Long id) {
		// TODO Auto-generated method stub
		CurrencyRate currencyRate = null;
		currencyRate = get(CurrencyRate.class, id);
		return currencyRate;
	}
	
	@Override
	public Currency getCurrencyById(Long id) {
		// TODO Auto-generated method stub
		Currency currency = null;
		currency = get(Currency.class, id);
		return currency;
	}
	
	@Override
	public CurrencyCountry getCurrencyCountryById(Long id) {
		CurrencyCountry currencyCountry = null;
		currencyCountry = get(CurrencyCountry.class, id);
		return currencyCountry;
	}
	
	@Override
	public CurrencyRate getRateByDateAndCode(java.util.Date date, String code) {
		// TODO Auto-generated method stub
        System.out.println("Getting object by date and currency code = " + date + " " + code);
        Date sqlDate = new Date(date.getTime());
    	String query= "SELECT c FROM CurrencyRate c where c.currencyDate= :date AND c.currency.currencyCode :code";
    	Map<String, Object> queryParameters = new HashMap<String, Object>();
    	queryParameters.put("date", sqlDate);
    	queryParameters.put("code", code);
    	CurrencyRate currencyRate = getUniqueResult(query, queryParameters);
		return currencyRate;
	}

	@Override
	public CurrencyRate getRateForCountryByDateAndCode(String countryName, java.util.Date date, String currencyCode) {
		// TODO Auto-generated method stub
        System.out.println("Getting object for " + countryName + " by date " + date + " and currency code " + currencyCode);
        Date sqlDate = new java.sql.Date(date.getTime());
	    String query = "SELECT cur FROM CurrencyExchange ce\r\n"
    					+ "JOIN CurrencyRate cur ON(cur.currencyId = ce.currencyRate.currencyId)\r\n"
    					+ "JOIN Country c ON(c.countryId = ce.country.countryId)\r\n"
    					+ "WHERE cur.currencyDate=:sqlDate \r\n"
    					+ "AND cur.currency.currencyCode=:currencyCode \r\n"
    					+ "AND c.countryName=:countryName";
	    Map<String, Object> queryParameters = new HashMap<String, Object>();
	    queryParameters.put("sqlDate", sqlDate);
	    queryParameters.put("currencyCode", currencyCode);
	    queryParameters.put("countryName", countryName);
    	CurrencyRate currencyRate = getUniqueResult(query, queryParameters);
		return currencyRate;
	}
	
	@Override
	public CurrencyExchange getCurrencyExchangeById(Long id) {
		// TODO Auto-generated method stub
        CurrencyExchange currencyExchanged = null;
        currencyExchanged = get(CurrencyExchange.class, id);
		return currencyExchanged;
	}

	@Override
	public void updateCurrencyExchange(CurrencyExchange currencyExchanged) {
		// TODO Auto-generated method stub
		update(currencyExchanged);
	}

	@Override
	public void updateCurrencyRate(CurrencyRate currencyRate) {
		// TODO Auto-generated method stub
		update(currencyRate);
	}
	
	@Override
	public void updateCurrency(Currency currency) {
		// TODO Auto-generated method stub
		update(currency);
	}
	
	@Override
	public void updateCurrencyCountry(CurrencyCountry currencyCountry) {
		update(currencyCountry);
	}

	@Override
	public void deleteCurrencyExchange(CurrencyExchange currencyExchanged) {
		// TODO Auto-generated method stub
		delete(currencyExchanged);
	}

	@Override
	public void deleteCurrencyRate(CurrencyRate rate) {
		// TODO Auto-generated method stub
		delete(rate);
	}
	
	@Override
	public void deleteCurrency(Currency currency) {
		// TODO Auto-generated method stub
		delete(currency);
	}
	
	@Override
	public void deleteCurrencyCountry(CurrencyCountry currencyCountry) {
		delete(currencyCountry);
	}

	public List<Object[]> findRatesWithHigherDifferencePeriod(java.util.Date dateFrom, java.util.Date dateTo) {
		// TODO Auto-generated method stub
        Date sqlDateFrom = new java.sql.Date(dateFrom.getTime());
        Date sqlDateTo = new java.sql.Date(dateTo.getTime());
	    String query = "SELECT cr.currency, MAX(cr.currencyRate-cr2.currencyRate) FROM CurrencyRate cr "
    					+ " LEFT JOIN CurrencyRate cr2  "
    					+ "ON cr.currency=cr2.currency AND cr.currencyRate>=cr2.currencyRate "
    					+ " JOIN cr.currency c ON cr.currency.currencyCodeId = c.currencyCodeId"
    					+ " WHERE cr2.currencyRate IS NOT NULL "
    					+ "AND cr.currencyDate BETWEEN :dateFrom AND :dateTo" 
    					+ " GROUP BY cr.currency, c.currencyCodeId ";	  // sprawdz nazwy czy sa dobrze (tabel, kolumn)
    	Map<String, Object> queryParameters = new HashMap<String, Object>();
    	queryParameters.put("dateFrom", sqlDateFrom);
    	queryParameters.put("dateTo", sqlDateTo);
    	List<Object[]> currencyRates = getListResult(query, queryParameters);
		return currencyRates;
		/*
		 * SELECT cr.id_currency, MAX(cr.currency_rate-cr2.currency_rate) as highestDifference 
FROM currency_rate cr 
LEFT JOIN currency_rate cr2 
ON cr.id_currency=cr2.id_currency AND cr.currency_rate>=cr2.currency_rate
WHERE cr2.currency_rate IS NOT NULL 
AND cr.currency_date BETWEEN '2000-02-10' AND '2021-02-20'
GROUP BY cr.id_currency
		 */
	}
	
	public Object[] findMaxAndMinRate(java.util.Date dateFrom, java.util.Date dateTo) {
		// TODO Auto-generated method stub
        Date sqlDateFrom = new java.sql.Date(dateFrom.getTime());
        Date sqlDateTo = new java.sql.Date(dateTo.getTime());
	    String query = "SELECT min(cr.currencyRate), max(cr.currencyRate) FROM CurrencyRate cr "
    					+ "WHERE (cr.currencyDate >= :dateFrom AND cr.currencyDate <= :dateTo)";
    	Map<String, Object> queryParameters = new HashMap<String, Object>();
    	queryParameters.put("dateFrom", sqlDateFrom);
    	queryParameters.put("dateTo", sqlDateTo);
    	Object[] values = getSingleResult(query, queryParameters);
		return values;
	}
	
	public List<CurrencyRate> findFiveBestRatesForPlusAndMinus(String currencyCode) {
		// TODO Auto-generated method stub
		String query = "SELECT cr FROM CurrencyRate cr JOIN FETCH cr.currency WHERE cr.currency.currencyCode = :currencyCode ORDER BY cr.currencyRate desc\r\n";
    	Map<String, Object> queryParametrs = new HashMap<String, Object>();
    	queryParametrs.put("currencyCode", currencyCode);
    	List<CurrencyRate> currencyRates = getListResult(query, queryParametrs, 5);
    	int count = currencyRates.size(); 
    	if(count<5) {
    		System.out.println("There is only " + currencyRates.size() + " records");
    	} else if(count>=5 && count<10) {
    		System.out.println("There is only " + currencyRates.size() + " records");
    		query = "SELECT cr2 FROM CurrencyRate cr2 JOIN FETCH cr2.currency  WHERE cr2.currency.currencyCode = :currencyCode ORDER BY cr2.currencyRate asc\r\n";
	    	List<CurrencyRate> currencyRates2 = getListResult(query, queryParametrs, count-5);
	    	currencyRates.addAll(currencyRates2);
    	} else if(count>9) {
	    	query = "SELECT cr2 FROM CurrencyRate cr2 JOIN FETCH cr2.currency  WHERE cr2.currency.currencyCode = :currencyCode ORDER BY cr2.currencyRate asc\r\n";
	    	List<CurrencyRate> currencyRates2 = getListResult(query, queryParametrs, 5);
	    	currencyRates.addAll(currencyRates2);
    	}
		return currencyRates;
	}
	
	public List<Country> findCountryWithCurrencies(int amount) {
		// TODO Auto-generated method stub
	    //String query = "SELECT c FROM Country c WHERE size(c.currencies) >= :amount";
		String query = "COUNTRY_WITH_AMOUNT_CURRENCIES";
    	Map<String, Object> queryParameters = new HashMap<String, Object>();
    	queryParameters.put("amount", amount);
    	List<Country> countries = getListResultNamedQuery(query, queryParameters);
		return countries;
	}
	
	@Override
	public List<CurrencyExchange> getAllCurrenciesExchanged() {
		// TODO Auto-generated method stub
        System.out.println("Getting all of objects!");  
        List<CurrencyExchange> currenciesExchanged = loadAllData(CurrencyExchange.class);
		return currenciesExchanged;
	}
	
	@Override
	public List<Currency> getAllCurrencies() {
		System.out.println("Getting all of objects!");  
        List<Currency> currencies = loadAllData(Currency.class);
		return currencies;
	}
	
	@Override
	public List<CurrencyCountry> getAllCurrenciesCountries() {
		System.out.println("Getting all of objects!");  
        List<CurrencyCountry> currencies = loadAllData(CurrencyCountry.class);
		return currencies;
	}

	@Override
	public List<CurrencyRate> getAllRates() {
		// TODO Auto-generated method stub
        System.out.println("Getting all of objects!");  
        List<CurrencyRate> currenciesRate = loadAllData(CurrencyRate.class);
		return currenciesRate;
	}

	@Override
	public Country getCountryById(Long id) {
		// TODO Auto-generated method stub
        Country country = get(Country.class, id);
		return country;
	}
	
	@Override
	public Currency getCurrencyByCode(String currencyCode) {
		String query = "SELECT c FROM Currency c WHERE c.currencyCode = :currencyCode";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("currencyCode", currencyCode);
		Currency currency = getUniqueResult(query, parameters);
		return currency;
	}

	@Override
	public void updateCountry(Country country) {
		// TODO Auto-generated method stub
		update(country);
	}

	@Override
	public void deleteCountry(Country country) {
		// TODO Auto-generated method stub
		delete(country);
	}

	@Override
	public List<Country> getAllCountries() {
		// TODO Auto-generated method stub
        System.out.println("Getting all of objects!");  
        List<Country> countries = loadAllData(Country.class);
    	return countries;
	}
	
	@Override
	public Country getCountryByCountryName(String countryName) {
		System.out.println("Getting Country by Country Name");
	    String query = "SELECT c FROM Country c WHERE c.countryName=:countryName";
    	Map <String, Object> queryParameters = new HashMap<String, Object>();
	    queryParameters.put("countryName", countryName);
    	Country country = getUniqueResult(query, queryParameters);
		return country;
	}
	
	private <T> List<T> loadAllData(Class<T> type) {
		Session session = sessionFactory.openSession();
		List<T> data = new ArrayList<>();
		try {
		    CriteriaBuilder builder = session.getCriteriaBuilder();
		    CriteriaQuery<T> criteria = builder.createQuery(type);
		    criteria.from(type);
		    data = session.createQuery(criteria).getResultList();
		} catch(MappingException e) {
        	throw new DatabaseException("Unknown entity, check mapping, annotations, getters and setters");
        } catch(SchemaManagementException e) {
        	throw new DatabaseException("Schema-validation: missing table");
        } catch(JDBCException e) {
        	throw new DatabaseException("DatabaseException: could not prepare statement");
        } catch(TransactionException e) {
	    	throw new DatabaseException("Transaction was marked for rollback only; cannot commit");
	    } catch(Exception e) {
	    	throw new UncheckedIOException("An attempt to load uninitialized data outside an active session");
	    }
        finally {
        	session.close();
        }
	    return data;
	}
	
	private <T> void add(T object) {
		Session session = sessionFactory.openSession();
		try {
        	session.beginTransaction();
        	session.save(object);
    		session.getTransaction().commit();
        } catch(MappingException e) {
        	throw new DatabaseException("Unknown entity, check mapping, annotations, getters and setters");
        } catch(SchemaManagementException e) {
        	throw new DatabaseException("Schema-validation: missing table");
        } catch(JDBCException e) {
        	throw new DatabaseException("DatabaseException: could not prepare statement");
        } catch(TransactionException e) {
	    	throw new DatabaseException("Transaction was marked for rollback only; cannot commit");
	    } catch(Exception e) {
	    	throw new UncheckedIOException("An attempt to load uninitialized data outside an active session");
	    }
        finally {
        	session.close();
        }
	}
	
	private <T> T get(Class<T> type, Long id) {
		Session session = sessionFactory.openSession();
        System.out.println("Getting object by id = " + id);
        T object = null;
        try {
        	object = (T) session.get(type, id);
        } catch(MappingException e) {
        	throw new DatabaseException("Unknown entity, check mapping, annotations, getters and setters");
        } catch(SchemaManagementException e) {
        	throw new DatabaseException("Schema-validation: missing table");
        } catch(JDBCException e) {
        	throw new DatabaseException("DatabaseException: could not prepare statement");
        } catch(TransactionException e) {
	    	throw new DatabaseException("Transaction was marked for rollback only; cannot commit");
	    } catch(Exception e) {
	    	throw new UncheckedIOException("An attempt to load uninitialized data outside an active session");
	    }
        finally {
        	session.close();
        }
		return (T) object;
	}
	
	private <T> T getSingleResult(String queryString, Map<String,Object> parameters) {
		Session session = sessionFactory.openSession();
        T object = null;
        try {
    		Query<?> query = session.createQuery(queryString);
        	for(Map.Entry<String, Object> entry : parameters.entrySet()) {
        		query.setParameter(entry.getKey(), entry.getValue());
        	}
        	object = (T) query.getSingleResult();
        } catch(MappingException e) {
        	throw new DatabaseException("Unknown entity, check mapping, annotations, getters and setters");
        } catch(SchemaManagementException e) {
        	throw new DatabaseException("Schema-validation: missing table");
        } catch(JDBCException e) {
        	throw new DatabaseException("DatabaseException: could not prepare statement");
        } catch(TransactionException e) {
	    	throw new DatabaseException("Transaction was marked for rollback only; cannot commit");
	    } catch(Exception e) {
	    	throw new UncheckedIOException("An attempt to load uninitialized data outside an active session");
	    }
        finally {
        	session.close();
        }
		return (T) object;
	}
	
	private <T> T getSingleResultNamedQuery(String queryString, Map<String,Object> parameters) {
		Session session = sessionFactory.openSession();
        T object = null;
        try {
    		Query<?> query = session.createNamedQuery(queryString);
        	for(Map.Entry<String, Object> entry : parameters.entrySet()) {
        		query.setParameter(entry.getKey(), entry.getValue());
        	}
        	object = (T) query.getSingleResult();
        } catch(MappingException e) {
        	throw new DatabaseException("Unknown entity, check mapping, annotations, getters and setters");
        } catch(SchemaManagementException e) {
        	throw new DatabaseException("Schema-validation: missing table");
        } catch(JDBCException e) {
        	throw new DatabaseException("DatabaseException: could not prepare statement");
        } catch(TransactionException e) {
	    	throw new DatabaseException("Transaction was marked for rollback only; cannot commit");
	    } catch(Exception e) {
	    	throw new UncheckedIOException("An attempt to load uninitialized data outside an active session");
	    }
        finally {
        	session.close();
        }
		return (T) object;
	}
	
	private <T> void update(T object) {
		Session session = sessionFactory.openSession();
        System.out.println("Updating object " + object.getClass().getName());  
    	try {
	        session.beginTransaction();
	    	session.update(object);
	    	session.getTransaction().commit();
    	} catch(MappingException e) {
        	throw new DatabaseException("Unknown entity, check mapping, annotations, getters and setters");
        } catch(SchemaManagementException e) {
        	throw new DatabaseException("Schema-validation: missing table");
        } catch(JDBCException e) {
        	throw new DatabaseException("DatabaseException: could not prepare statement");
        } catch(TransactionException e) {
	    	throw new DatabaseException("Transaction was marked for rollback only; cannot commit");
	    } catch(Exception e) {
	    	throw new UncheckedIOException("An attempt to load uninitialized data outside an active session");
	    }
        finally {
        	session.close();
        }
	}
	
	private <T> void delete(T object) {
		Session session = sessionFactory.openSession();
        System.out.println("Deleting object " + object.getClass().getName());  
    	try {
	        session.beginTransaction();
	    	session.delete(object);
	        session.getTransaction().commit();
    	} catch(MappingException e) {
        	throw new DatabaseException("Unknown entity, check mapping, annotations, getters and setters");
        } catch(SchemaManagementException e) {
        	throw new DatabaseException("Schema-validation: missing table");
        } catch(JDBCException e) {
        	throw new DatabaseException("DatabaseException: could not prepare statement");
        } catch(TransactionException e) {
	    	throw new DatabaseException("Transaction was marked for rollback only; cannot commit");
	    } catch(Exception e) {
	    	throw new UncheckedIOException("An attempt to load uninitialized data outside an active session");
	    }
        finally {
        	session.close();
        }
	}

	private <T> T getUniqueResult(String queryString, Map<String,Object> parameters) {
		Session session = sessionFactory.openSession();
        T object = null;
        try {
        	Query<?> query = session.createQuery(queryString);
        	for(Map.Entry<String, Object> entry : parameters.entrySet()) {
        		query.setParameter(entry.getKey(), entry.getValue());
        	}
	    	object = (T) query.uniqueResult();
        } catch(MappingException e) {
        	throw new DatabaseException("Unknown entity, check mapping, annotations, getters and setters");
        } catch(SchemaManagementException e) {
        	throw new DatabaseException("Schema-validation: missing table");
        } catch(JDBCException e) {
        	throw new DatabaseException("DatabaseException: could not prepare statement");
        } catch(TransactionException e) {
	    	throw new DatabaseException("Transaction was marked for rollback only; cannot commit");
	    } catch(Exception e) {
	    	throw new UncheckedIOException("An attempt to load uninitialized data outside an active session");
	    }
        finally {
        	session.close();
        }
		return object;
	}
	
	private <T> T getUniqueResultNamedQuery(String queryString, Map<String,Object> parameters) {
		Session session = sessionFactory.openSession();
        T object = null;
        try {
        	Query<?> query = session.createNamedQuery(queryString);
        	for(Map.Entry<String, Object> entry : parameters.entrySet()) {
        		query.setParameter(entry.getKey(), entry.getValue());
        	}
	    	object = (T) query.uniqueResult();
        } catch(MappingException e) {
        	throw new DatabaseException("Unknown entity, check mapping, annotations, getters and setters");
        } catch(SchemaManagementException e) {
        	throw new DatabaseException("Schema-validation: missing table");
        } catch(JDBCException e) {
        	throw new DatabaseException("DatabaseException: could not prepare statement");
        } catch(TransactionException e) {
	    	throw new DatabaseException("Transaction was marked for rollback only; cannot commit");
	    } catch(Exception e) {
	    	throw new UncheckedIOException("An attempt to load uninitialized data outside an active session");
	    }
        finally {
        	session.close();
        }
		return object;
	}
	
	private <T> List<T> getListResult(String queryString, Map<String,Object> parameters) {
		Session session = sessionFactory.openSession();
        List<T> object = new ArrayList<T>();
        try {
        	Query<?> query = session.createQuery(queryString);
        	if(parameters != null) {
	        	for(Map.Entry<String, Object> entry : parameters.entrySet()) {
	        		query.setParameter(entry.getKey(), entry.getValue());
	        	}
        	}
        	object = (List<T>) query.getResultList();
        } catch(MappingException e) {
        	throw new DatabaseException("Unknown entity, check mapping, annotations, getters and setters");
        } catch(SchemaManagementException e) {
        	throw new DatabaseException("Schema-validation: missing table");
        } catch(SQLGrammarException e) {
        	throw new DatabaseException(e.getMessage());
        } catch(JDBCException e) {
        	throw new DatabaseException("DatabaseException: could not prepare statement");
        } catch(TransactionException e) {
	    	throw new DatabaseException("Transaction was marked for rollback only; cannot commit");
	    } catch(QueryException e) {
	    	throw new DatabaseException(e.getMessage());
	    } catch(Exception e) {
	    	throw new UncheckedIOException("An attempt to load uninitialized data outside an active session. Please check your sql query if correct");
	    }
        finally {
        	session.close();
        }
		return object;
	}
	
	private <T> List<T> getListResult(String queryString, Map<String,Object> parameters, int maxResult) {
		Session session = sessionFactory.openSession();
        List<T> object = new ArrayList<T>();
        try {
        	Query<?> query = session.createQuery(queryString);
        	for(Map.Entry<String, Object> entry : parameters.entrySet()) {
        		query.setParameter(entry.getKey(), entry.getValue());
        	}
        	query.setMaxResults(maxResult);
        	object = (List<T>) query.getResultList();
        } catch(MappingException e) {
        	throw new DatabaseException("Unknown entity, check mapping, annotations, getters and setters");
        } catch(SchemaManagementException e) {
        	throw new DatabaseException("Schema-validation: missing table");
        } catch(JDBCException e) {
        	throw new DatabaseException("DatabaseException: could not prepare statement");
        } catch(TransactionException e) {
	    	throw new DatabaseException("Transaction was marked for rollback only; cannot commit");
	    } catch(QueryException e) {
	    	throw new DatabaseException(e.getMessage());
	    } catch(Exception e) {
	    	throw new UncheckedIOException("An attempt to load uninitialized data outside an active session");
	    }
        finally {
        	session.close();
        }
		return object;
	}

	private <T> List<T> getListResultNamedQuery(String queryString, Map<String,Object> parameters) {
		Session session = sessionFactory.openSession();
        List<T> object = new ArrayList<T>();
        try {
        	Query<?> query = session.createNamedQuery(queryString);
        	for(Map.Entry<String, Object> entry : parameters.entrySet()) {
        		query.setParameter(entry.getKey(), entry.getValue());
        	}
        	object = (List<T>) query.getResultList();
        } catch(MappingException e) {
        	throw new DatabaseException("Unknown entity, check mapping, annotations, getters and setters");
        } catch(SchemaManagementException e) {
        	throw new DatabaseException("Schema-validation: missing table");
        } catch(JDBCException e) {
        	throw new DatabaseException("DatabaseException: could not prepare statement");
        } catch(TransactionException e) {
	    	throw new DatabaseException("Transaction was marked for rollback only; cannot commit");
	    } catch(QueryException e) {
	    	throw new DatabaseException(e.getMessage());
	    } catch(Exception e) {
	    	throw new UncheckedIOException("An attempt to load uninitialized data outside an active session");
	    }
        finally {
        	session.close();
        }
		return object;
	}
	
	private <T> List<T> getListResultNameQuery(String queryString, Map<String,Object> parameters, int maxResult) {
		Session session = sessionFactory.openSession();
        List<T> object = new ArrayList<T>();
        try {
        	Query<?> query = session.createNamedQuery(queryString);
        	for(Map.Entry<String, Object> entry : parameters.entrySet()) {
        		query.setParameter(entry.getKey(), entry.getValue());
        	}
        	query.setMaxResults(maxResult);
        	object = (List<T>) query.getResultList();
        } catch(MappingException e) {
        	throw new DatabaseException("Unknown entity, check mapping, annotations, getters and setters");
        } catch(SchemaManagementException e) {
        	throw new DatabaseException("Schema-validation: missing table");
        } catch(JDBCException e) {
        	throw new DatabaseException("DatabaseException: could not prepare statement");
        } catch(TransactionException e) {
	    	throw new DatabaseException("Transaction was marked for rollback only; cannot commit");
	    } catch(QueryException e) {
	    	throw new DatabaseException(e.getMessage());
	    } catch(Exception e) {
	    	throw new UncheckedIOException("An attempt to load uninitialized data outside an active session");
	    }
        finally {
        	session.close();
        }
		return object;
	}
}
/// TODO implement for entities abstract DAO