package com.exchange.ExchangesRateMaven.Service.Implement;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

import com.exchange.ExchangesRateMaven.Service.Abstract.Adaptee;
import com.exchange.ExchangesRateMaven.Service.Abstract.ExchangeRate;

public class ExchangeRateAdapter implements ExchangeRate {
	private Adaptee _adaptee;
	private Date date;
	private Date dateFirst;
	private String currencyCode;
	// tutaj trzeba sprawdzic daty
	// tak mysle ze powinno byc pole do ostatniej daty archiwizacji
	// zaimplementowac algorytm cofania
	// i tak oprocz tego wstrzykujesz wartosci co nie?
	// odczyt z pliku/ web (co bedzie wynikiem ofc string)
	// trzeba rozroznic czy to jest xml czy json
	// w glownej metodzie trzeba rzucic throw new Runtime
	public ExchangeRateAdapter(Adaptee adaptee) {
		// TODO Auto-generated constructor stub
		_adaptee = adaptee;
	}

	@Override
	public BigDecimal getCurrencyRate(String currencyCode, Date date) {
		// TODO Auto-generated method stub
		BigDecimal rate = null;
		try {
			checkDate(date);
			checkCurrency(currencyCode);
			this.date = date;
			this.dateFirst = date;
			this.currencyCode = currencyCode;
			rate = getResponse();
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return rate;
	}

	@Override
	public BigDecimal calculateCurrency(BigDecimal currencyValue, String currencyCode, Date date) {
		// TODO Auto-generated method stub
		BigDecimal currencyExchanged = null;
		try	{
			checkDate(date);
			currencyCode = checkCurrency(currencyCode);
			this.date = date;
			this.dateFirst = date;
			this.currencyCode = currencyCode;
			BigDecimal rate = getResponse();
			if(rate !=null) {
				currencyExchanged = currencyValue.multiply(rate);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return currencyExchanged;
	}
	
	private String checkCurrency(String currencyCode) throws Exception {
		String code;
		try {
			code = Currency.getInstance(currencyCode.toUpperCase()).toString();
		}
		catch (Exception e){
			throw new Exception("Enter currency code properly");
		}
		return code;
	}
	
	private void checkDate(Date date) throws Exception {
		Date currentDate = new Date();
		Date dateBefore = _adaptee.getLastArchiveCurrencyRate();
		if(date.before(dateBefore)) {
			throw new Exception("Date cannot be before " + dateBefore); 
		}
		if(date.after(currentDate)) {
			throw new Exception("Date cannot be after " + setDateFormat(currentDate));
		}
	}
	
	private String setDateFormat(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	private BigDecimal getResponse() throws Exception {
		BigDecimal rate = null;
		while(rate == null) { // or flag implemented in class shows that got response
			if(date.before(_adaptee.getLastArchiveCurrencyRate())) {
				throw new Exception("Check your file if currency code " + currencyCode + " for date "+ dateFirst + " exists or check if currency code exists. If exists check method getCurrencyRate if return correct in class defined structure of file");
			}
			rate = _adaptee.getCurrencyRate(currencyCode, date);
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
		
	public static Date getStartOfDay(Date day, Calendar cal) {
	    if (day == null)
	      day = new Date();
	    cal.setTime(day);
	    cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
	    cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
	    cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
	    cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
	    return cal.getTime();
	  }
}
