package integration.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import dao.CountryDao;
import dao.CurrencyDao;
import dao.CurrencyRateDao;
import dao.CurrencyRepositoryImpl;
import entity.Country;
import entity.Currency;
import entity.CurrencyCountry;
import entity.CurrencyExchange;
import entity.CurrencyRate;
import service.CountryService;
import service.CurrencyRateService;
import service.CurrencyService;

public class CurrencyDatabaseServiceIntegrationTests {
    protected static SessionFactory sessionFactory;
    protected static CurrencyRepositoryImpl repo;
    protected static Map<String, Object> data;
    protected static CountryDao countryDao;
    protected static CurrencyDao currencyDao;
    protected static CurrencyRateDao currencyRateDao;
    protected static CountryService countryService;
    protected static CurrencyService currencyService;
    protected static CurrencyRateService currencyRateService;

    @BeforeClass
    public static void init() throws FileNotFoundException, SQLException {
    	sessionFactory = new Configuration().configure("hibernateTest.cfg.xml").buildSessionFactory();
    	repo = new CurrencyRepositoryImpl();
    	repo.setSession(sessionFactory);
    	String sqlString = loadFile();
    	EntityManager manager = sessionFactory.createEntityManager();
    	Query q = manager.createNativeQuery(sqlString);
    	manager.getTransaction().begin();
    	q.executeUpdate();
    	manager.getTransaction().commit();
    	manager.close();
    	data = sampleData();
    }
	
    public static Map<String, Object> sampleData() {
    	Map<String, Object> objects = new HashMap<String, Object>();
    	Currency expectedCurrency = new Currency();
    	String expectedCode = "USD";
    	String expectedCurrencyName = "dolar amerykaski";
    	Long expectedId = 3L;
    	expectedCurrency.setCurrencyCode(expectedCode);
    	expectedCurrency.setCurrencyName(expectedCurrencyName);
    	expectedCurrency.setId(expectedId);
    	objects.put("Currency", expectedCurrency);
    	return objects;
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
    public void should_retrun_countries() {
    	int listOfCountries = 5;
    	List<Country> countries = repo.getAllCountries();
    	
    	assertThat(countries.size()).isNotNull();
    	assertThat(countries.size()).isEqualTo(listOfCountries);
    }
    
    @Test
    public void should_retrun_currencies() {
    	int listOfCurrencies = 5;
    	List<Currency> currencies = repo.getAllCurrencies();
    	
    	assertThat(currencies.size()).isNotNull();
    	assertThat(currencies.size()).isEqualTo(listOfCurrencies);
    }
    
    @Test
    public void should_retrun_currencies_with_countries() {
    	int listOfCountries = 7;
    	List<CurrencyCountry> countries = repo.getAllCurrenciesCountries();
    	
    	assertThat(countries.size()).isEqualTo(listOfCountries);
    }
    
    @Test
    public void should_retrun_currency_rates() {
    	int listOfCountries = 14;
    	List<CurrencyRate> rates = repo.getAllRates();
    	
    	assertThat(rates.size()).isEqualTo(listOfCountries);
    }
    
    @Test
    public void should_retrun_currencies_exchanged() {
    	int listOfCountries = 14;
    	List<CurrencyExchange> currenciesExchanged = repo.getAllCurrenciesExchanged();
    	
    	assertThat(currenciesExchanged.size()).isEqualTo(listOfCountries);
    }
    
    @Test
    public void should_retrun_countries_with_at_least_two_currencies() {
    	int amount = 2;
    	int excpectedCurrencies = 2;
    	List<Country> countries = repo.findCountryWithCurrencies(amount);
    	Country country = countries.get(1);
    	String countryName = "UNITED STATES OF AMERICA";
    	
    	assertThat(country.getCountryName()).isEqualTo(countryName);
    	assertThat(country.getCurrencies().size()).isEqualTo(excpectedCurrencies);
    }
    
    @Test
    public void should_return_best_rates_for_currency() {
    	String currencyCode = "EUR";
    	int expectedCount = 3;
    	
    	List<CurrencyRate> currency =  repo.findFiveBestRatesForPlusAndMinus(currencyCode);
    	
    	assertThat(currency).isNotNull();
    	assertThat(currency.size()).isEqualTo(expectedCount);
    }
    
    @Test
	public void find_rates_with_with_highest_difference_in_period() throws ParseException {
		String stringDate = "2021-02-22";
		String stringDate2 = "2000-12-24";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date dateFrom = format.parse(stringDate2);
		Date dateTo = format.parse(stringDate);
		int position = 2;
		int difference = 1;
		int currency = 0;
		BigDecimal differenceValue = new BigDecimal("0.0574");
		Currency expectedCurrency = (Currency) data.get("Currency");

		List<Object[]> rates = repo.findRatesWithHigherDifferencePeriod(dateFrom, dateTo);
		Currency actualCurrency = (Currency)rates.get(position)[currency];
		
		assertThat(rates.size()).isNotEqualTo(0);
		assertThat(rates.get(position)[difference]).isEqualTo(differenceValue);
		assertThat(actualCurrency.getCurrencyCode()).isEqualTo(expectedCurrency.getCurrencyCode());
		assertThat(actualCurrency.getCurrencyName()).isEqualTo(expectedCurrency.getCurrencyName());
		assertThat(actualCurrency.getId()).isEqualTo(expectedCurrency.getId());
	}
    
    @Test
	public void should_find_max_and_min_rate() throws ParseException {
		String stringDate = "2021-02-22";
		String stringDate2 = "2000-12-24";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date dateFrom = format.parse(stringDate2);
		Date dateTo = format.parse(stringDate);
		int positionMin = 0;
		int positionMax = 1;
		BigDecimal expectedMinValue = new BigDecimal("3.6981");
		BigDecimal expectedMaxValue = new BigDecimal("5.0512");
		
		Object[] object = repo.findMaxAndMinRate(dateFrom, dateTo);
		
		assertThat(object[positionMin]).isEqualTo(expectedMinValue);
		assertThat(object[positionMax]).isEqualTo(expectedMaxValue);
	}
    
    @Test
    public void should_return_N_1_result(){
    	List<Country> countries = repo.getAllCountries();
    	int expectedCount = countries.size() + 1;
		int count=1;
		for(Country c : countries) {
			for(CurrencyCountry cu : c.getCurrencies()) {
				// 
				
			}
			count++;
		}
		
		assertThat(count).isEqualTo(expectedCount);
    }
        
    @AfterClass
    public static void tearDown(){
        sessionFactory.close();
    }
}
