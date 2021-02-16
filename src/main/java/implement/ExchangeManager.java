package implement;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

import abstracts.CountryConverter;
import abstracts.CurrencyExchangeMapper;
import abstracts.CurrencyExchangeRateRepository;
import abstracts.DataConverter;
import abstracts.Manager;
import abstracts.Service;
import common.CountryDto;
import common.CurrencyCodesWithCountries;
import common.ExchangeRateDto;
import common.ExchangedCurrencyDto;
import entity.Country;
import entity.CurrencyExchange;
import entity.CurrencyExchangeKey;
import entity.CurrencyRate;
import exception.CurrencyNotFound;
import exception.DateException;

public class ExchangeManager implements Manager {
	private Service service;
	private DataConverter converter;
	private Date date;
	private Date dateFirst;
	private String currencyCode;
	private CurrencyExchangeRateRepository repo;
	private CountryConverter countryService;

	public ExchangeManager(Service service, DataConverter converter, CurrencyExchangeRateRepository repo)  {
		// TODO Auto-generated constructor stub
		this.service = service;
		this.converter = converter;
		service.setFormat(converter.getFormat());
		this.repo = repo;
	}
	
	public ExchangeManager(Service service, DataConverter converter, CurrencyExchangeRateRepository repo, CountryConverter countryService)  {
		// TODO Auto-generated constructor stub
		this.service = service;
		this.converter = converter;
		service.setFormat(converter.getFormat());
		this.repo = repo;
		this.countryService = countryService;
	}

	@Override
	public ExchangedCurrencyDto exchangeCurrencyToPLN(String currencyCode, Date date, BigDecimal value) {
		// TODO Auto-generated method stub
		checkDate(date);
		this.currencyCode = checkCurrency(currencyCode);
		this.date = date;
		this.dateFirst = date;
		ExchangeRateDto rate = getResponse();
		ExchangedCurrencyDto currency = new ExchangedCurrencyDto(rate.getCurrencyCode(), rate.getCurrencyDate(), value, rate.getCurrencyRate().multiply(value), rate.getCurrencyRate(), "PLN");
		return currency;
	}
	
	@Override
	public ExchangeRateDto getCurrencyRate(String currencyCode, Date date, BigDecimal value) {
		// TODO Auto-generated method stub
		checkDate(date);
		this.currencyCode = checkCurrency(currencyCode);
		this.date = date;
		this.dateFirst = date;
		ExchangeRateDto rate = getResponse();
		return rate;
	}
	
	public String checkCurrency(String currencyCode) {
		if(currencyCode != null && currencyCode.length() != 3) {
			throw new IllegalArgumentException("Invalid currency code");
		}
		String code;
		try {
			code = Currency.getInstance(currencyCode.toUpperCase()).toString();
		}
		catch (Exception e){
			throw new CurrencyNotFound("Enter currency code properly");
		}
		return code;
	}
	
	public void checkDate(Date date) {
		if(date == null) {
			throw new IllegalArgumentException("Invalid date");
		}
		Date currentDate = new Date();
		Date dateBefore = service.getLastCurrencyRateDate();
		if(date.before(dateBefore)) {
			throw new DateException("Date cannot be before " + dateBefore); 
		}
		if(date.after(currentDate)) {
			throw new DateException("Date cannot be after " + setDateFormat(currentDate));
		}
	}
	
	private String setDateFormat(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	private ExchangeRateDto getResponse() {
		ExchangeRateDto rate = null;
		while(rate == null) { // or flag implemented in class shows that got response
			if(date.before(service.getLastCurrencyRateDate())) {
				throw new CurrencyNotFound("Check your file if currency code " + currencyCode + " for date "+ dateFirst + " exists or check if currency code exists. If exists check method getCurrencyRate if return correct in class defined structure of file");
			}
			CurrencyRate curRate = repo.getRateByDateAndCode(convertToSqlDate(date), currencyCode);
			if(curRate != null) {
				rate = CurrencyExchangeMapper.INSTANCE.mapToExchangeRateDto(curRate);
			}
			else {
				rate = converter.getCurrencyRate(service.getExchangeRate(currencyCode, date));
				if(rate!=null) {
					curRate = CurrencyExchangeMapper.INSTANCE.mapToCurrencyRate(rate);
					repo.addRate(curRate);
				}
				else {
					date = setDateDay(-1);// method set Day -1
				}
			}
		}
		return rate;
	}
	
	private java.sql.Date convertToSqlDate(Date date) {
	    java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}
	
	private Date convertToDate(java.sql.Date date) {
		Date utilDate = new Date(date.getTime());
		return utilDate;
	}
	
	private Date setDateDay(int dayOfDate) { // set Date here not in other scope
		// TODO Auto-generated method stub
		//currencyDate - day
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.date);
		cal.add(Calendar.DATE, dayOfDate);
		return cal.getTime();
	}

	@Override
	public BigDecimal exchangeCurrencyToPLNByCountryName(String countryName, Date date, BigDecimal value) {
		// TODO Auto-generated method stub
		checkDate(date);
		CountryDto countryDto = countryService.getCodeByCurrencyName(countryName);
		this.currencyCode = checkCurrency(countryDto.getCurrencyCode());
		this.date = date;
		this.dateFirst = date;
		countryDto.setCurrencyDate(convertToSqlDate(date));
		countryDto.setCurrencyToExchange(value);
		ExchangeRateDto rate = getRateForCountry(countryDto);
		return countryDto.getCurrencyExchanged();
	}
	
	private ExchangeRateDto getRateForCountry(CountryDto countryDto) {
		ExchangeRateDto rate = null;
		CurrencyRate curRate = null;
		while(rate == null) { // or flag implemented in class shows that got response
			if(date.before(service.getLastCurrencyRateDate())) {
				throw new CurrencyNotFound("Check your file if currency code " + currencyCode + " for date "+ dateFirst + " exists or check if currency code exists. If exists check method getCurrencyRate if return correct in class defined structure of file");
			}
			curRate = repo.getRateByDateAndCode(countryDto.getCurrencyDate(), countryDto.getCurrencyCode());
			if(curRate != null) {
				rate = CurrencyExchangeMapper.INSTANCE.mapToExchangeRateDto(curRate);
			}
			else { 
				rate = converter.getCurrencyRate(service.getExchangeRate(currencyCode, date));
				if(rate!=null) {
					curRate = CurrencyExchangeMapper.INSTANCE.mapToCurrencyRate(rate);
					curRate.setCurrencyId(repo.addRate(curRate));
				}
				else {
					date = setDateDay(-1);// method set Day -1
					countryDto.setCurrencyDate(convertToSqlDate(date));
				}
			}
		}
		countryDto.setCurrencyExchanged(countryDto.getCurrencyToExchange().multiply(rate.getCurrencyRate()).setScale(2, RoundingMode.HALF_EVEN));
		addExchangeRateToDb(countryDto, curRate);
		return rate;
	}
	
	private void addExchangeRateToDb(CountryDto countryDto, CurrencyRate rate) {
		Country country = CurrencyExchangeMapper.INSTANCE.mapToCountry(countryDto);
		Long idCountry = repo.addCountry(country);
		CurrencyExchangeKey currencyExchangeKey = new CurrencyExchangeKey(idCountry, rate.getCurrencyId());
		CurrencyExchange currencyExchange = new CurrencyExchange(currencyExchangeKey, country, rate);
		CurrencyExchangeKey key = repo.addCurrencyExchange(currencyExchange);
	}
	
	@Override
	public BigDecimal exchangeCurrencyToPLNByCountryNameAndCurrencyCode(String countryName, Date date, BigDecimal money, String currencyCode) {
		checkDate(date);
		this.currencyCode = checkCurrency(currencyCode);
		this.date = date;
		this.dateFirst = date;
		CountryDto countryDto = new CountryDto();
		countryDto.setCurrencyCode(this.currencyCode);
		countryDto.setCurrencyDate(convertToSqlDate(date));
		countryDto.setCurrencyToExchange(money);
		countryDto.setCountryName(countryName);
		ExchangeRateDto rate = getRateForCountry(countryDto);
		ExchangedCurrencyDto currency = new ExchangedCurrencyDto(rate.getCurrencyCode(), rate.getCurrencyDate(), money, countryDto.getCurrencyExchanged(), rate.getCurrencyRate(), "PLN");
		return countryDto.getCurrencyExchanged();
	}
}