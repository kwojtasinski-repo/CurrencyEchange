package implement;

import java.io.File;
import java.util.Date;
import java.util.List;

import abstracts.CountryConverter;
import abstracts.CurrencyRepository;
import abstracts.Service;
import entity.Country;
import entity.CurrencyExchange;
import entity.CurrencyExchangeKey;
import entity.CurrencyRate;

public class CurrencyDatabaseService implements Service {
	private CurrencyRepository repo;
	private Date archivalDate; 
	private Service service;
	
	public CurrencyDatabaseService(Service service, CurrencyRepository repo, Date archivalDate) {
		// TODO Auto-generated constructor stub
		this.repo = repo;
		this.archivalDate = archivalDate;
		this.service = service;
	}
	
	@Override
	public Date getLastCurrencyRateDate() {
		// TODO Auto-generated method stub		
		return archivalDate;
	}

	@Override
	public CurrencyRate getExchangeRate(String currencyCode, Date date) {
		// TODO Auto-generated method stub
		CountryConverter country = new CsvService(new File("C:\\Projects\\ExchangesRate\\src\\main\\java\\implement\\codes-all_csv.csv"));
		String countryName = country.getCountryByCurrencyName(currencyCode);
		CurrencyRate currencyRate = repo.getRateForCountryByDateAndCode(countryName, date, currencyCode);
		if(currencyRate != null) {
			return currencyRate;
		}
		
		currencyRate = service.getExchangeRate(currencyCode, date);
		if(currencyRate != null) {
			addCountryWithRate(new Country(countryName), currencyRate);
		}
		return currencyRate;
	}
	
	public void addCountryWithRate(Country country, CurrencyRate rate) {
		Country countryFromDb = getCountry(country.getCountryName());
		if(countryFromDb != null) {
			country = countryFromDb;
		}
		addCountryAndRate(country, rate);
	}

	private Country getCountry(String countryName) {
		Country country = repo.getCountryByCountryName(countryName);
		return country;
	}
	
	private void addCountryAndRate(Country country, CurrencyRate rate) {
		CurrencyExchangeKey currencyExchangeKey = new CurrencyExchangeKey(country.getCountryId(), rate.getCurrencyId());
		CurrencyExchange currencyExchange = new CurrencyExchange(currencyExchangeKey, country, rate);
		CurrencyExchangeKey key = repo.addCurrencyExchange(currencyExchange);
	}
	
	@Override
	public void setFormat(String format) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFormat() {
		// TODO Auto-generated method stub
		return "db";
	}
}
