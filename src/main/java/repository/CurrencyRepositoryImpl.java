package repository;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.JDBCException;
import org.hibernate.MappingException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.TransactionException;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.tool.schema.spi.SchemaManagementException;

import abstracts.CurrencyRepository;
import entity.Country;
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
	public CurrencyRate getRateById(Long id) {
		// TODO Auto-generated method stub
		CurrencyRate currencyRate = null;
		currencyRate = get(CurrencyRate.class, id);
		return currencyRate;
	}
	
	@Override
	public CurrencyRate getRateByDateAndCode(java.util.Date date, String code) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Getting object by date and currency code = " + date + " " + code);
        Date sqlDate = new Date(date.getTime());
    	Query<?> query= session.
    	        createQuery("SELECT c FROM CurrencyRate c where currency_date='" + sqlDate + "' AND currency_code='" + code + "'");
    	CurrencyRate currencyRate = getUniqueResult(query);
		return currencyRate;
	}

	@Override
	public CurrencyRate getRateForCountryByDateAndCode(String countryName, java.util.Date date, String currencyCode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Getting object for " + countryName + " by date " + date + " and currency code " + currencyCode);
        Date sqlDate = new java.sql.Date(date.getTime());
	    Query<?> query = session
    			.createQuery("SELECT cur FROM CurrencyExchange ce\r\n"
    					+ "JOIN CurrencyRate cur ON(cur.currencyId = ce.currencyRate.currencyId)\r\n"
    					+ "JOIN Country c ON(c.countryId = ce.country.countryId)\r\n"
    					+ "WHERE cur.currencyDate=:sqlDate \r\n"
    					+ "AND cur.currencyCode=:currencyCode \r\n"
    					+ "AND c.countryName=:countryName");
    	query.setParameter("sqlDate", sqlDate);
    	query.setParameter("currencyCode", currencyCode);
    	query.setParameter("countryName", countryName);
    	CurrencyRate currencyRate = getUniqueResult(query);
		return currencyRate;
	}
	
	@Override
	public CurrencyExchange getCurrencyById(Long id) {
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
	public void deleteCurrencyExchange(CurrencyExchange currencyExchanged) {
		// TODO Auto-generated method stub
		delete(currencyExchanged);
	}

	@Override
	public void deleteCurrencyRate(CurrencyRate rate) {
		// TODO Auto-generated method stub
		delete(rate);
	}

	public List<CurrencyRate> findRatesWithHigherDifferencePeriod(java.util.Date dateFrom, java.util.Date dateTo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        Date sqlDateFrom = new java.sql.Date(dateFrom.getTime());
        Date sqlDateTo = new java.sql.Date(dateTo.getTime());
	    Query<?> query = session
    			.createQuery("SELECT cr FROM currency_rate cr "
    					+ "WHERE cr.currency_rate = "
    					+ "(SELECT min(cr2.currency_rate) FROM currency_rate cr2"
    					+ "WHERE (cr2.currency_date BETWEEN :dateFrom AND :dateTo)) "
    					+ "GROUP BY cr.id ORDER BY (max(cr2.rate) - min(cr2.rate)) DESC");
    	query.setParameter("dateFrom", sqlDateFrom);
    	query.setParameter("dateTo", sqlDateTo);
    	List<CurrencyRate> currencyRates = getListResult(query);
		return currencyRates;
	}
	
	public List<CurrencyRate> findMaxAndMinRate(java.util.Date dateFrom, java.util.Date dateTo) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        Date sqlDateFrom = new java.sql.Date(dateFrom.getTime());
        Date sqlDateTo = new java.sql.Date(dateTo.getTime());
	    Query<?> query = session
    			.createQuery("SELECT min(cr.rate), max((cr.rate) FROM currency cr "
    					+ "WHERE (cr.date BETWEEN :dateFrom AND :dateTo)");
    	query.setParameter("dateFrom", sqlDateFrom);
    	query.setParameter("dateTo", sqlDateTo);
    	List<CurrencyRate> currencyRates = getListResult(query);
		return currencyRates;
	}
	
	public List<CurrencyRate> findFiveBestRatesForPlusAndMinus(String currencyCode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
	    Query<?> query = session
    			.createQuery("SELECT cr FROM currency_rate cr WHERE cr.currency_code = :currencyCode ORDER BY cr.currency_rate desc LIMIT 5\r\n"
    					+ "UNION SELECT cr2 FROM currency_rate cr2 WHERE cr2.currency_code = :currencyCode ORDER BY cr2.currency_rate asc LIMIT 5");
    	query.setParameter("currencyCode", currencyCode);
    	List<CurrencyRate> currencyRates = getListResult(query);
		return currencyRates;
	}
	
	public List<Country> findCountryWithAtLeast2Currencies(Integer amount) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
	    Query<?> query = session
    			.createQuery("SELECT c FROM CountryEntity c WHERE size(c.currencies) >= :amount");
    	query.setParameter("amount", amount);
    	List<Country> countries = getListResult(query);
		return countries;
	}
	
	@Override
	public List<CurrencyExchange> getAllCurrencies() {
		// TODO Auto-generated method stub
        System.out.println("Getting all of objects!");  
        List<CurrencyExchange> currenciesExchanged = loadAllData(CurrencyExchange.class);
		return currenciesExchanged;
	}

	@Override
	public List<CurrencyRate> getAllRates() {
		// TODO Auto-generated method stub
        System.out.println("Getting all of objects!");  
        List<CurrencyRate> currenciesRate = loadAllData(CurrencyRate.class);
		return currenciesRate;
	}
	
	@Override
	public Long addCountry(Country country) {
		// TODO Auto-generated method stub
		add(country);
		return country.getCountryId();
	}

	@Override
	public Country getCountryById(Long id) {
		// TODO Auto-generated method stub
        Country country = get(Country.class, id);
		return country;
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
		Session session = sessionFactory.openSession();
	    Query<?> query = session
    			.createQuery("SELECT c FROM Country c WHERE c.countryName=:countryName");
    	query.setParameter("countryName", countryName);
    	Country country = getUniqueResult(query);
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
	
	private <T> T getUniqueResult(Query<?> query) {
		Session session = sessionFactory.openSession();
        T object = null;
        try {
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
	
	private <T> List<T> getListResult(Query<?> query) {
		Session session = sessionFactory.openSession();
        List<T> object = new ArrayList<T>();
        try {
	    	object = (List<T>) getListResult(query);
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
}
