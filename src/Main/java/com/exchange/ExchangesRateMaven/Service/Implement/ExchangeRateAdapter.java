package com.exchange.ExchangesRateMaven.Service.Implement;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;

import org.json.JSONException;

import com.exchange.ExchangesRateMaven.Service.Abstract.Adaptee;
import com.exchange.ExchangesRateMaven.Service.Abstract.ExchangeRate;
import com.exchange.ExchangesRateMaven.Service.Common.JsonReader;
import com.exchange.ExchangesRateMaven.Service.Common.XmlReader;

public class ExchangeRateAdapter implements ExchangeRate {
	private Adaptee _adaptee;
	private Date date;
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
			this.currencyCode = currencyCode;
			String response = getResponse();
			rate= getRateFromResponse(response);
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
			checkCurrency(currencyCode);
			this.date = date;
			this.currencyCode = currencyCode;
			String response = getResponse();
			BigDecimal rate= getRateFromResponse(response);
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
		if(date.after(new Date())) {
			throw new Exception("Date cannot be after " + setDateFormat(currentDate));
		}
	}
	
	private String setDateFormat(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	private String getResponse() throws Exception {
		String response = "";
		while(response.length() == 0) { // or flag implemented in class shows that got response
			response = _adaptee.getDataAsString(currencyCode, date);
			date = setDateDay(-1);// method set Day -1
		}
		return response;
	}	
	
	private Date setDateDay(int dayOfDate) { // set Date here not in other scope
		// TODO Auto-generated method stub
		//currencyDate - day
		Calendar cal = Calendar.getInstance();
		cal.setTime(this.date);
		cal.add(Calendar.DATE, dayOfDate);
		return cal.getTime();
	}
	
	private Date getStartOfDay(Date day) {
	    return getStartOfDay(day, Calendar.getInstance());
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
	
	private BigDecimal getRateFromResponse(String txt) throws Exception
	{
		BigDecimal rate = null;
		if(txt.startsWith("<"))
		{
			//method to read xml
			rate = XmlReader.getCurrencyRateFromXml(txt);
			return rate;
		}
		else if(txt.startsWith("{") || txt.startsWith("["))
		{
			//method to read json
			rate = JsonReader.getCurrencyRateFromJson(txt);
			return rate;
		}
		else {
			throw new Exception("Unhanled format"); 
		}
	}
}
