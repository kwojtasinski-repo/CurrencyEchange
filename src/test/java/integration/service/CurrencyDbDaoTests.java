package integration.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.CountryDao;
import dao.CurrencyDao;
import dao.CurrencyRateDao;
import entity.Country;
import entity.Currency;
import entity.CurrencyRate;
import service.CountryService;
import service.CurrencyRateService;
import service.CurrencyService;

public class CurrencyDbDaoTests {
    protected static SessionFactory sessionFactory;
    protected static CountryDao countryDao;
    protected static CurrencyDao currencyDao;
    protected static CurrencyRateDao currencyRateDao;
    protected static CountryService countryService;
    protected static CurrencyService currencyService;
    protected static CurrencyRateService currencyRateService;

    @BeforeClass
    public static void init() throws FileNotFoundException, SQLException {
    	sessionFactory = new Configuration().configure("hibernateTest.cfg.xml").buildSessionFactory();    	
    	String sqlString = loadFile();
    	EntityManager manager = sessionFactory.createEntityManager();
    	Query q = manager.createNativeQuery(sqlString);
    	manager.getTransaction().begin();
    	q.executeUpdate();
    	manager.getTransaction().commit();
    	countryDao = new CountryDao(manager);
    	currencyDao = new CurrencyDao(manager);
    	currencyRateDao = new CurrencyRateDao(manager);
    	countryService = new CountryService(countryDao);
    	currencyService = new CurrencyService(currencyDao);
    	currencyRateService = new CurrencyRateService(currencyRateDao);
    }
    
    public static String loadFile() throws FileNotFoundException {
    	File myObj = new File("C:\\Projects\\ExchangesRate\\src\\test\\resources\\META-INF\\test.sql");
        Scanner myReader = new Scanner(myObj);
        StringBuilder sb = new StringBuilder();
        while (myReader.hasNextLine()) {
        	sb.append(myReader.nextLine());
        }
        myReader.close();
        return sb.toString();
    }
	
    @Test
    public void should_get_all_countries() {
    	List<Country> countries = countryService.getAll();
    	
    	assertThat(countries).isNotNull();
    	assertThat(countries.size()).isNotEqualTo(0);
    }
    
    @Test
    public void should_get_all_currencies() {
    	List<Currency> currencies = currencyService.getAll();
    	
    	assertThat(currencies).isNotNull();
    	assertThat(currencies.size()).isNotEqualTo(0);
    }
    
    @Test
    public void should_get_all_rates() {
    	List<CurrencyRate> rates = currencyRateService.getAll();
    	
    	assertThat(rates).isNotNull();
    	assertThat(rates.size()).isNotEqualTo(0);
    }
    
    @Test
    public void shoud_add_country() {
    	Country country = new Country("Kanada");
    	country.setCountryId(null);
    	Long expectedId = 6L;
    	
    	countryService.save(country);
    	
    	assertThat(country.getCountryId()).isNotNull();
    	assertThat(country.getCountryId()).isEqualTo(expectedId);
    }
    
    @Test
    public void shoud_add_currency() {
    	Currency currency = new Currency();
    	currency.setCurrencyCode("CAD");
    	currency.setCurrencyName("dolar kanadyjski");
    	Long expectedId = 6L;
    	
    	currencyService.save(currency);
    	
    	assertThat(currency.getId()).isNotNull();
    	assertThat(currency.getId()).isEqualTo(expectedId);
    }
    
    @Test
    public void shoud_add_currency_rate() { // probably errors in db
    	BigDecimal rate = new BigDecimal("4.2121");
    	CurrencyRate currencyRate = new CurrencyRate();
    	currencyRate.setCurrencyDate(new Date());
    	currencyRate.setCurrencyRate(rate);
    	Long expectedId = 15L;
    	Currency currency = currencyService.get(13L);
    	currencyRate.setCurrency(currency);
    	
    	currencyRateService.save(currencyRate);
    	
    	assertThat(currencyRate.getCurrencyId()).isNotNull();
    	assertThat(currencyRate.getCurrencyId()).isEqualTo(expectedId);
    }
    
    @Test
    public void shoud_get_country() {
    	Long expectedId = 1L;
    	String expectedCountry = "AUSTRIA";
    	
    	Country country = countryService.get(expectedId);
    	
    	assertThat(country).isNotNull();
    	assertThat(country.getCountryId()).isEqualTo(expectedId);
    	assertThat(country.getCountryName()).isEqualTo(expectedCountry);
    }
    
    @Test
    public void shoud_get_currency() {
    	Long expectedId = 1L;
    	String expectedCurrencyCode = "EUR";
    	String expectedCurrencyName = "euro";
    	
    	Currency currency = currencyService.get(expectedId);
    	
    	assertThat(currency).isNotNull();
    	assertThat(currency.getId()).isEqualTo(expectedId);
    	assertThat(currency.getCurrencyCode()).isEqualTo(expectedCurrencyCode);
    	assertThat(currency.getCurrencyName()).isEqualTo(expectedCurrencyName);
    }
    
    @Test
    public void shoud_get_currency_rate() {
    	Long expectedId = 14L;
    	BigDecimal rate = new BigDecimal("4.2212");
    	
    	CurrencyRate currencyRate = currencyRateService.get(expectedId);
    	
    	assertThat(currencyRate).isNotNull();
    	assertThat(currencyRate.getCurrencyId()).isEqualTo(expectedId);
    	assertThat(currencyRate.getCurrencyRate()).isEqualTo(rate);
    }
    
    @Test
    public void shoud_update_country() {
    	Long id = 2L;
    	String expectedName = "Canada";
    	
    	Country country = countryService.get(id);
    	country.setCountryName(expectedName);
    	countryService.update(country);
    	Country countryModified = countryService.get(id);
    	
    	assertThat(countryModified.getCountryName()).isEqualTo(expectedName);
    }
    
    @Test
    public void shoud_update_currency() {
    	Long id = 4L;
    	String expectedName = "CDA";
    	
    	Currency currency = currencyService.get(id);
    	currency.setCurrencyCode(expectedName);
    	currencyService.update(currency);
    	Currency currencyModified = currencyService.get(id);
    	
    	assertThat(currencyModified.getCurrencyCode()).isEqualTo(expectedName);
    }
    
    @Test
    public void shoud_update_currency_rate() {
    	Long id = 13L;
    	BigDecimal rate = new BigDecimal("4.6121");
    	
    	CurrencyRate currencyRate = currencyRateService.get(id);
    	currencyRate.setCurrencyRate(rate);
    	currencyRateService.update(currencyRate);
    	CurrencyRate currencyRateModified = currencyRateService.get(id);
    	
    	assertThat(currencyRateModified.getCurrencyRate()).isEqualTo(rate);
    }
    
    @Test
    public void shoud_delete_country() {
    	Long id = 3L;
    	
    	Country country = countryService.get(id);
    	countryService.delete(country);
    	Country countryModifed = countryService.get(id);
    	
    	assertThat(countryModifed).isNull();
    }
    
    @Test
    public void shoud_delete_currency() { // probably errors in db (mapping) but everything except 3 functions works fine so abstract dao is implemented correctly
    	Long id = 5L;
    	
    	Currency currency = currencyService.get(id);
    	currencyService.delete(currency);
    	Currency currencyModifed = currencyService.get(id);
    	
    	assertThat(currencyModifed).isNull();
    }
    
    @Test
    public void shoud_delete_currency_rate() { // probably errors in db (mapping)
    	Long id = 14L;
    	
    	CurrencyRate currencyRate = currencyRateService.get(id);
    	currencyRateService.delete(currencyRate);
    	CurrencyRate currencyRateModifed = currencyRateService.get(id);
    	
    	assertThat(currencyRateModifed).isNull();
    }
    
    @AfterClass
    public static void tearDown(){
        sessionFactory.close();
    }
}
