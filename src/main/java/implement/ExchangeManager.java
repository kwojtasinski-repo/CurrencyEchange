package implement;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

import abstracts.DataConverter;
import abstracts.Manager;
import abstracts.Service;
import common.ExchangeRate;
import common.ExchangedCurrency;
import exception.CurrencyNotFound;
import exception.DateException;

public class ExchangeManager implements Manager {
	private Service service;
	private DataConverter converter;
	private Date date;
	private Date dateFirst;
	private String currencyCode;

	public ExchangeManager(Service service, DataConverter converter)  {
		// TODO Auto-generated constructor stub
		// TODO Auto-generated constructor stub
		this.service = service;
		this.converter = converter;
		service.setFormat(converter.getFormat());
	}

	@Override
	public ExchangedCurrency exchangeCurrencyToPLN(String currencyCode, Date date, BigDecimal value) {
		// TODO Auto-generated method stub
		checkDate(date);
		checkCurrency(currencyCode);
		this.date = date;
		this.dateFirst = date;
		this.currencyCode = currencyCode;
		ExchangeRate rate = getResponse();
		ExchangedCurrency currency = new ExchangedCurrency(rate.getCurrencyCode(), rate.getCurrencyDate(), value, rate.getCurrencyRate().multiply(value));
		return currency;
	}
	
	private String checkCurrency(String currencyCode) {
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
	
	private void checkDate(Date date) {
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
	
	private ExchangeRate getResponse() {
		ExchangeRate rate = null;
		while(rate == null) { // or flag implemented in class shows that got response
			if(date.before(service.getLastCurrencyRateDate())) {
				throw new CurrencyNotFound("Check your file if currency code " + currencyCode + " for date "+ dateFirst + " exists or check if currency code exists. If exists check method getCurrencyRate if return correct in class defined structure of file");
			}
			rate = converter.getCurrencyRate(service.getExchangeRate(currencyCode, date));
			date = setDateDay(-1);// method set Day -1
		}
		return rate;
	}
	
	private Date setDateDay(int dayOfDate) { // set Date here not in other scope
		// TODO Auto-generated method stub
		//currencyDate - day
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.date);
		cal.add(Calendar.DATE, dayOfDate);
		return cal.getTime();
	}
}
