package repository;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.JDBCException;
import org.hibernate.LazyInitializationException;
import org.hibernate.MappingException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.QueryTimeoutException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StaleStateException;
import org.hibernate.TransactionException;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.JDBCConnectionException;
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
		Session session = sessionFactory.openSession();
        System.out.println("Creating currency trade");
        try {
        	session.beginTransaction();
        	session.save(currencyExchanged);
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
		return currencyExchanged.getId();
	}

	@Override
	public Long addRate(CurrencyRate rate) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Creating currency trade");
        try {
	    	session.beginTransaction();
			session.save(rate);
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
		return rate.getCurrencyId();
	}

	@Override
	public CurrencyRate getRateById(Long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Getting object by id = " + id);
        CurrencyRate currencyRate = null;
        try {
	    	currencyRate =  (CurrencyRate) session.get(CurrencyRate.class, id);
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
		return currencyRate;
	}
	
	@Override
	public CurrencyRate getRateByDateAndCode(java.util.Date date, String code) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Getting object by date and currency code = " + date + " " + code);
        CurrencyRate currencyRate = null;
        try {
	        Date sqlDate = new Date(date.getTime());
	    	Query<?> query= session.
	    	        createQuery("SELECT c FROM CurrencyRate c where currency_date='" + sqlDate + "' AND currency_code='" + code + "'");
	    	currencyRate = (CurrencyRate) query.uniqueResult();
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
		return currencyRate;
	}

	@Override
	public CurrencyRate getRateForCountryByDateAndCode(String countryName, java.util.Date date, String currencyCode) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Getting object for " + countryName + " by date " + date + " and currency code " + currencyCode);
        CurrencyRate currencyRate = null;
        try {
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
	    	currencyRate = (CurrencyRate) query.uniqueResult();
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
		return currencyRate;
	}
	
	@Override
	public CurrencyExchange getCurrencyById(Long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Getting object by id = " + id);
        CurrencyExchange currencyExchanged = null;
        try {
	    	currencyExchanged =  (CurrencyExchange) session.get(CurrencyExchange.class, id);
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
		return currencyExchanged;
	}

	@Override
	public void updateCurrencyExchange(CurrencyExchange currencyExchanged) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Updating object id = " + currencyExchanged.getId());  
    	try {
	        session.beginTransaction();
	    	session.update(currencyExchanged);
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

	@Override
	public void updateCurrencyRate(CurrencyRate currencyRate) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Updating object id = " + currencyRate.getCurrencyId());  
    	try {
	        session.beginTransaction();
	    	session.update(currencyRate);
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

	@Override
	public void deleteCurrencyExchange(CurrencyExchange currencyExchanged) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Deleting object id = " + currencyExchanged.getId());  
        try {
	        session.beginTransaction();
	    	session.delete(currencyExchanged);
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

	@Override
	public void deleteCurrencyRate(CurrencyRate rate) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Deleting object id = " + rate.getCurrencyId());  
        try {
	    	session.beginTransaction();
	    	session.delete(rate);
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

	@Override
	public List<CurrencyExchange> getAllCurrencies() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Getting all of objects!");  
        List<CurrencyExchange> currenciesExchanged = null;
        try {
	        currenciesExchanged = new ArrayList<CurrencyExchange>();
	    	currenciesExchanged = loadAllData(CurrencyExchange.class, session);
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
		return currenciesExchanged;
	}

	@Override
	public List<CurrencyRate> getAllRates() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Getting all of objects!");  
        List<CurrencyRate> currenciesRate = null;
        try {
	        currenciesRate = new ArrayList<CurrencyRate>();
	    	currenciesRate = loadAllData(CurrencyRate.class, session);
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
		return currenciesRate;
	}
	
	@Override
	public Long addCountry(Country country) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Creating currency trade");
        try {
	    	session.beginTransaction();
			session.save(country);
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
		return country.getCountryId();
	}

	@Override
	public Country getCountryById(Long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Getting object by id = " + id);
        Country country = null;
        try {
	    	country =  (Country) session.get(Country.class, id);
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
		return country;
	}

	@Override
	public void updateCountry(Country country) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Updating object id = " + country.getCountryId());  
    	try {
	        session.beginTransaction();
	    	session.update(country);
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

	@Override
	public void deleteCountry(Country country) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Deleting object id = " + country.getCountryId());  
    	try {
	        session.beginTransaction();
	    	session.delete(country);
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

	@Override
	public List<Country> getAllCountries() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
        System.out.println("Getting all of objects!");  
        List<Country> countries = null;
        try {
	        countries = new ArrayList<Country>();
	    	countries = loadAllData(Country.class, session);
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
    	return countries;
	}

	private static <T> List<T> loadAllData(Class<T> type, Session session) {
	    CriteriaBuilder builder = session.getCriteriaBuilder();
	    CriteriaQuery<T> criteria = builder.createQuery(type);
	    criteria.from(type);
	    List<T> data = session.createQuery(criteria).getResultList();
	    return data;
	}
}
