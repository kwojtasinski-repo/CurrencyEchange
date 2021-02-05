package com.exchange.ExchangesRateMaven.Service.Implement;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.exchange.ExchangesRateMaven.Domain.Entity.Currency;
import com.exchange.ExchangesRateMaven.Domain.Entity.CurrencyTradeRate;
import com.exchange.ExchangesRateMaven.Service.Abstract.Adaptee;
import com.exchange.ExchangesRateMaven.Service.Abstract.ExchangeRate;
import com.exchange.ExchangesRateMaven.Service.Abstract.SalesDocument;
import com.exchange.ExchangesRateMaven.Service.Common.CurrencyRateFromApiJson;
import com.exchange.ExchangesRateMaven.Service.Common.CurrencyRateFromApiXml;

public class SalesDocumentService implements SalesDocument {

	@Override
	public BigDecimal insert(BigDecimal money, String currencyCode, Date date) {
		// TODO Auto-generated method stub
		Adaptee adaptee = new CurrencyRateFromApiJson(new Date(1009926000000L)); // set archival date
		//Adaptee adaptee = new CurrencyRateFromApiXml(new Date(1009926000000L)); // set archival date
		ExchangeRate exchangeRate = new ExchangeRateAdapter(adaptee);
		
		BigDecimal currency = exchangeRate.calculateCurrency(money, currencyCode, date);
		return currency;
	}
	
	public static void main(String[] args) {
		SalesDocumentService s = new SalesDocumentService();
		String string = "2020-12-27";
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMANY);
		Date date = new Date(1000L);
		try {
			date = format.parse(string);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(s.insert(new BigDecimal("500"), "chf", date));
	}
}
