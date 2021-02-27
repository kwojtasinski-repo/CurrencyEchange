package implement;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

import abstracts.CountryConverter;
import abstracts.DataConverter;
import abstracts.Manager;
import abstracts.Service;
import dao.CurrencyRepositoryImpl;
import entity.Country;
import entity.CurrencyRate;
import exception.CurrencyNotFound;
import exception.DateException;

public class ExchangeManager implements Manager {
	private Service service;
	
	public ExchangeManager(Service service)  {
		// TODO Auto-generated constructor stub
		this.service = service;
	}
	
	@Override
	public CurrencyRate exchangeCurrencyToPLN(String currencyCode, Date date) {
		// TODO Auto-generated method stub
		currencyCode = currencyCode.toUpperCase();
		checkDate(date);
		currencyCode = checkCurrency(currencyCode);
		CurrencyRate rate = getResponse(currencyCode, date);
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
	
	private CurrencyRate getResponse(String currencyCode, Date date) {
		CurrencyRate rate = null;		
		while(rate == null) { // or flag implemented in class shows that got response
			if(date.before(service.getLastCurrencyRateDate())) {
				throw new CurrencyNotFound("Check your file if date of currency rate exists or check currency code. If exists check method getCurrencyRate if return correct in class defined structure of file");
			}
			rate = service.getExchangeRate(currencyCode, date);
			if(rate == null) {
				date = setDateDay(date, -1);// method set Day -1
			}
		}
		return rate;
	}
	
	public Date setDateDay(Date date, int dayOfDate) { // set Date here not in other scope
		// TODO Auto-generated method stub
		//currencyDate - day
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, dayOfDate);
		return cal.getTime();
	}
}